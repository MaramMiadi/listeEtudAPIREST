package maram.isett.listeetudapirest.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EtudiantDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private int age ;
    private String cin;
    private String nom;
    private LocalDate dateNaissance;
    private String email;
    private int anneePremiereInscription;
    private Long departementId;
    private String departementNom;
}
