package maram.isett.listeetudapirest.mapper;


import maram.isett.listeetudapirest.dto.DepartementDTO;
import maram.isett.listeetudapirest.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDTO(Departement departement) {
        if (departement == null) return null;

        DepartementDTO dto = new DepartementDTO();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        return dto;
    }

    public Departement toEntity(DepartementDTO dto) {
        if (dto == null) return null;

        Departement departement = new Departement();
        departement.setId(dto.getId());
        departement.setNom(dto.getNom());
        return departement;
    }
}