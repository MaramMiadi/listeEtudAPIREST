package maram.isett.gradingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "liste-etud-api")
public interface StudentClient {

    @GetMapping("/api/etudiants/{id}")
    Object getEtudiantById(@PathVariable("id") Long id);
}