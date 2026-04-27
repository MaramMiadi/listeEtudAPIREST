package maram.isett.listeetudapirest.service;

import maram.isett.listeetudapirest.dto.EtudiantDTO;

import lombok.RequiredArgsConstructor;
import maram.isett.listeetudapirest.entity.Departement;
import maram.isett.listeetudapirest.entity.Etudiant;
import maram.isett.listeetudapirest.exceptions.BusinessException;
import maram.isett.listeetudapirest.exceptions.ResourceNotFoundException;
import maram.isett.listeetudapirest.mapper.EtudiantMapper;
import maram.isett.listeetudapirest.repository.DepartementRepository;
import maram.isett.listeetudapirest.repository.EtudiantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;
    private final EtudiantMapper etudiantMapper;

    @Cacheable(value = "etudiants")
    @Transactional(readOnly = true)
    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll().stream()
                .map(etudiantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "etudiants", key = "#id")
    @Transactional(readOnly = true)
    public EtudiantDTO findById(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'id: " + id));
        return etudiantMapper.toDTO(etudiant);
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO create(EtudiantDTO etudiantDTO) {
        // Validation
        if (etudiantRepository.existsByCin(etudiantDTO.getCin())) {
            throw new BusinessException("Un étudiant avec ce CIN existe déjà");
        }
        if (etudiantRepository.existsByEmail(etudiantDTO.getEmail())) {
            throw new BusinessException("Un étudiant avec cet email existe déjà");
        }

        Departement departement = departementRepository.findById(etudiantDTO.getDepartementId())
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'id: " + etudiantDTO.getDepartementId()));

        Etudiant etudiant = etudiantMapper.toEntity(etudiantDTO, departement);
        Etudiant saved = etudiantRepository.save(etudiant);
        return etudiantMapper.toDTO(saved);
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO update(Long id, EtudiantDTO etudiantDTO) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'id: " + id));

        Departement departement = departementRepository.findById(etudiantDTO.getDepartementId())
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'id: " + etudiantDTO.getDepartementId()));

        existing.setCin(etudiantDTO.getCin());
        existing.setNom(etudiantDTO.getNom());
        existing.setDateNaissance(etudiantDTO.getDateNaissance());
        existing.setEmail(etudiantDTO.getEmail());
        existing.setAnneePremiereInscription(etudiantDTO.getAnneePremiereInscription());
        existing.setDepartement(departement);

        Etudiant updated = etudiantRepository.save(existing);
        return etudiantMapper.toDTO(updated);
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public void delete(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Étudiant non trouvé avec l'id: " + id);
        }
        etudiantRepository.deleteById(id);
    }

    @Cacheable(value = "etudiants", key = "#annee")
    @Transactional(readOnly = true)
    public List<EtudiantDTO> findByAnneePremiereInscription(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee).stream()
                .map(etudiantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "etudiants", key = "#departementId")
    @Transactional(readOnly = true)
    public List<EtudiantDTO> findByDepartementId(Long departementId) {
        return etudiantRepository.findByDepartementId(departementId).stream()
                .map(etudiantMapper::toDTO)
                .collect(Collectors.toList());
    }
}
