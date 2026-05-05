package maram.isett.listeetudapirest;

import maram.isett.listeetudapirest.entity.Etudiant;
import maram.isett.listeetudapirest.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EtudiantIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private EtudiantRepository repository;

    @Test
    void shouldPersistAndRetrieveEtudiant() {
        Etudiant e = new Etudiant();
        e.setNom("Dupont");
        e.setCin("99999999");
        e.setEmail("dupont@test.com");
        
        repository.save(e);
        
        assertThat(repository.findAll()).isNotEmpty();
        assertThat(repository.findAll().get(0).getNom()).isEqualTo("Dupont");
    }
}
