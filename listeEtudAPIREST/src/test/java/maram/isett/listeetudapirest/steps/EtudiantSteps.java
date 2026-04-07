package maram.isett.listeetudapirest.steps;

import maram.isett.listeetudapirest.entity.Etudiant;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class EtudiantSteps {

    private Etudiant etudiant;
    private int age;

    @Given("un étudiant avec la date de naissance {string}")
    public void un_etudiant_avec_la_date_de_naissance(String date) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(date));
    }

    @When("on calcule son âge")
    public void on_calcule_son_age() {
        age = etudiant.age();
    }

    @Then("l'âge retourné doit être {int}")
    public void l_age_retourne_doit_etre(Integer expected) {
        assertEquals(expected, age);
    }
}
