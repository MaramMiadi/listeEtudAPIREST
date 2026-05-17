package maram.isett.gradingservice.dto;


import lombok.Data;

@Data
public class EtudiantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}