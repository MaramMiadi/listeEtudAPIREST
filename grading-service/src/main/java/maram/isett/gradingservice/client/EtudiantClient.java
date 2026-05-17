package maram.isett.gradingservice.client;

import maram.isett.gradingservice.dto.EtudiantDTO;

import maram.isett.gradingservice.dto.EtudiantDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EtudiantClient {

    @Value("${clients.etudiant-service.url}")
    private String etudiantServiceUrl;

    private RestClient restClient = RestClient.create();

    public EtudiantDTO findById(Long id) {
        return restClient.get()
                .uri(etudiantServiceUrl + "/api/etudiants/{id}", id)
                .retrieve()
                .body(EtudiantDTO.class);
    }
}