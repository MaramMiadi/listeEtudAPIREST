package maram.isett.gradingservice.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maram.isett.gradingservice.client.EtudiantClient;
import maram.isett.gradingservice.dto.NoteDTO;
import maram.isett.gradingservice.entity.Note;
import maram.isett.gradingservice.exception.ResourceNotFoundException;
import maram.isett.gradingservice.mapper.NoteMapper;
import maram.isett.gradingservice.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final EtudiantClient etudiantClient;

    public List<NoteDTO> getAllNotes() {
        return noteMapper.toDTOList(noteRepository.findAll());
    }

    public NoteDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return noteMapper.toDTO(note);
    }

    public NoteDTO createNote(NoteDTO noteDTO) {
        try {
            etudiantClient.findById(noteDTO.getStudentId());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student with ID " + noteDTO.getStudentId() + " does not exist"
            );
        }

        Note note = noteMapper.toEntity(noteDTO);
        return noteMapper.toDTO(noteRepository.save(note));
    }

    public NoteDTO updateNote(Long id, @Valid NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        if (!existingNote.getStudentId().equals(noteDTO.getStudentId())) {
            try {
                etudiantClient.findById(noteDTO.getStudentId());
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Student with ID " + noteDTO.getStudentId() + " does not exist"
                );
            }
        }

        noteMapper.updateEntity(existingNote, noteDTO);
        return noteMapper.toDTO(noteRepository.save(existingNote));
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    public List<NoteDTO> getNotesByStudentId(Long studentId) {
        return noteMapper.toDTOList(noteRepository.findByStudentId(studentId));
    }

    public List<NoteDTO> getNotesByMatiere(String matiere) {
        return noteMapper.toDTOList(noteRepository.findByMatiere(matiere));
    }

    public Double getAverageGrade(Long studentId) {
        return noteRepository.calculateAverageGradeByStudentId(studentId)
                .orElse(0.0);
    }

    public List<String> getStudentSubjects(Long studentId) {
        return noteRepository.findDistinctSubjectsByStudentId(studentId);
    }
}