package maram.isett.gradingservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maram.isett.gradingservice.dto.NoteDTO;
import maram.isett.gradingservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Note Management", description = "Endpoints for managing student grades")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "Get all notes", description = "Retrieves a list of all grades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by ID", description = "Retrieves a specific grade by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get notes by student ID", description = "Retrieves all grades for a specific student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<List<NoteDTO>> getNotesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(noteService.getNotesByStudentId(studentId));
    }

    @GetMapping("/matiere/{matiere}")
    @Operation(summary = "Get notes by subject", description = "Retrieves all grades for a specific subject")
    public ResponseEntity<List<NoteDTO>> getNotesByMatiere(@PathVariable String matiere) {
        return ResponseEntity.ok(noteService.getNotesByMatiere(matiere));
    }

    @GetMapping("/student/{studentId}/average")
    @Operation(summary = "Get average grade for student", description = "Calculates the average of all grades for a student")
    public ResponseEntity<Double> getAverageGrade(@PathVariable Long studentId) {
        return ResponseEntity.ok(noteService.getAverageGrade(studentId));
    }

    @GetMapping("/student/{studentId}/subjects")
    @Operation(summary = "Get subjects for student", description = "Retrieves a list of all subjects a student has grades for")
    public ResponseEntity<List<String>> getStudentSubjects(@PathVariable Long studentId) {
        return ResponseEntity.ok(noteService.getStudentSubjects(studentId));
    }

    @PostMapping
    @Operation(summary = "Create a new note", description = "Creates a new grade for a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or student not found")
    })
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(noteDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a note", description = "Updates an existing grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @Valid @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.updateNote(id, noteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a note", description = "Deletes a specific grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
