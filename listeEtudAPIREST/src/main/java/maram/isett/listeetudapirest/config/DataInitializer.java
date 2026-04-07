package maram.isett.listeetudapirest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EtudiantRepository repository;

    public DataInitializer(EtudiantRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Etudiant(null, "12345678", "Maram Miadi", LocalDate.of(2002, 5, 15)));
            repository.save(new Etudiant(null, "87654321", "Mariem Miadi", LocalDate.of(2001, 8, 22)));
            repository.save(new Etudiant(null, "11223344", "Fatma dhouib", LocalDate.of(2003, 1, 10)));
            repository.save(new Etudiant(null, "55667788", "Marwa Degachi", LocalDate.of(2000, 11, 5)));
            repository.save(new Etudiant(null, "99887766", "Maram Mia", LocalDate.of(2002, 3, 30)));
            System.out.println("✅ 5 étudiants initiaux ajoutés !");
        }
    }
}
