package maram.isett.listeetudapirest.service;

import lombok.RequiredArgsConstructor;
import maram.isett.listeetudapirest.dto.DepartementDTO;
import maram.isett.listeetudapirest.entity.Departement;
import maram.isett.listeetudapirest.exceptions.BusinessException;
import maram.isett.listeetudapirest.exceptions.ResourceNotFoundException;
import maram.isett.listeetudapirest.mapper.DepartementMapper;
import maram.isett.listeetudapirest.repository.DepartementRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    @Cacheable(value = "departements")
    @Transactional(readOnly = true)
    public List<DepartementDTO> findAll() {
        return departementRepository.findAll().stream()
                .map(departementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "departements", key = "#id")
    @Transactional(readOnly = true)
    public DepartementDTO findById(Long id) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'id: " + id));
        return departementMapper.toDTO(departement);
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO create(DepartementDTO departementDTO) {
        if (departementRepository.existsByNom(departementDTO.getNom())) {
            throw new BusinessException("Un département avec ce nom existe déjà");
        }

        Departement departement = departementMapper.toEntity(departementDTO);
        Departement saved = departementRepository.save(departement);
        return departementMapper.toDTO(saved);
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO update(Long id, DepartementDTO departementDTO) {
        Departement existing = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'id: " + id));

        existing.setNom(departementDTO.getNom());

        Departement updated = departementRepository.save(existing);
        return departementMapper.toDTO(updated);
    }

    @CacheEvict(value = "departements", allEntries = true)
    public void delete(Long id) {
        if (!departementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Département non trouvé avec l'id: " + id);
        }
        departementRepository.deleteById(id);
    }
}
