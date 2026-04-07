package maram.isett.listeetudapirest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cin;

    private String nom;
    private LocalDate dateNaissance;
    private String email;
    private int anneePremiereInscription;
    public int age() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
}