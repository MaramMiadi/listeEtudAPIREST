package maram.isett.listeetudapirest.mapper;

import maram.isett.listeetudapirest.dto.EtudiantDTO;
import maram.isett.listeetudapirest.entity.Departement;
import maram.isett.listeetudapirest.entity.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDTO(Etudiant etudiant) {
        if (etudiant == null) return null;

        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setCin(etudiant.getCin());
        dto.setNom(etudiant.getNom());
        dto.setDateNaissance(etudiant.getDateNaissance());
        dto.setEmail(etudiant.getEmail());
        dto.setAnneePremiereInscription(etudiant.getAnneePremiereInscription());

        if (etudiant.getDepartement() != null) {
            dto.setDepartementId(etudiant.getDepartement().getId());
            dto.setDepartementNom(etudiant.getDepartement().getNom());
        }

        return dto;
    }
    public Etudiant toEntity(EtudiantDTO dto, Departement departement) {
        if (dto == null) return null;

        Etudiant etudiant = new Etudiant();
        etudiant.setId(dto.getId());
        etudiant.setCin(dto.getCin());
        etudiant.setNom(dto.getNom());
        etudiant.setDateNaissance(dto.getDateNaissance());
        etudiant.setEmail(dto.getEmail());
        etudiant.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        etudiant.setDepartement(departement);

        return etudiant;
    }
}