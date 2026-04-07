package maram.isett.listeetudapirest.repository;

import maram.isett.listeetudapirest.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    boolean existsByNom(String nom);
}
