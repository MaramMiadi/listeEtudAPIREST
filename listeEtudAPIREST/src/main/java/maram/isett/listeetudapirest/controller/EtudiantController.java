package maram.isett.listeetudapirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maram.isett.listeetudapirest.dto.EtudiantDTO;
import maram.isett.listeetudapirest.service.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
@Tag(name = "Étudiant", description = "API de gestion des étudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    @GetMapping
    @Operation(summary = "Lister tous les étudiants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants(
            @RequestParam(required = false) Integer annee,
            @RequestParam(required = false) Long departementId) {
        if (annee != null) {
            return ResponseEntity.ok(etudiantService.findByAnneePremiereInscription(annee));
        }
        if (departementId != null) {
            return ResponseEntity.ok(etudiantService.findByDepartementId(departementId));
        }
        return ResponseEntity.ok(etudiantService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    public ResponseEntity<EtudiantDTO> getEtudiantById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    public ResponseEntity<EtudiantDTO> createEtudiant(@Valid @RequestBody EtudiantDTO etudiantDTO) {
        EtudiantDTO created = etudiantService.create(etudiantDTO);
        return ResponseEntity
                .created(URI.create("/api/etudiants/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un étudiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou département non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<EtudiantDTO> updateEtudiant(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantDTO etudiantDTO) {
        return ResponseEntity.ok(etudiantService.update(id, etudiantDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}