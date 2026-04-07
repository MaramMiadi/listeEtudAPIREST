package maram.isett.listeetudapirest.repository;

import maram.isett.listeetudapirest.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByAnneePremiereInscription(int annee);
    boolean existsByCin(String cin);
    boolean existsByEmail(String email);
}

