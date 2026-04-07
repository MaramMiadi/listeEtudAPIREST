package maram.isett.listeetudapirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maram.isett.listeetudapirest.dto.DepartementDTO;
import maram.isett.listeetudapirest.service.DepartementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
@Tag(name = "Département", description = "API de gestion des départements")
public class DepartementController {

    private final DepartementService departementService;

    @GetMapping
    @Operation(summary = "Lister tous les départements")
    public ResponseEntity<List<DepartementDTO>> getAllDepartements() {
        return ResponseEntity.ok(departementService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un département par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département trouvé"),
            @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    public ResponseEntity<DepartementDTO> getDepartementById(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Département créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<DepartementDTO> createDepartement(@Valid @RequestBody DepartementDTO departementDTO) {
        DepartementDTO created = departementService.create(departementDTO);
        return ResponseEntity
                .created(URI.create("/api/departements/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Département non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<DepartementDTO> updateDepartement(
            @PathVariable Long id,
            @Valid @RequestBody DepartementDTO departementDTO) {
        return ResponseEntity.ok(departementService.update(id, departementDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Département supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Département non trouvé")
    })
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        departementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}