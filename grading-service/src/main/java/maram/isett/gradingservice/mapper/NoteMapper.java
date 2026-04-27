package maram.isett.gradingservice.mapper;



import maram.isett.gradingservice.dto.NoteDTO;
import maram.isett.gradingservice.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {

    public NoteDTO toDTO(Note note) {
        if (note == null) {
            return null;
        }

        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setStudentId(note.getStudentId());
        dto.setMatiere(note.getMatiere());
        dto.setValeur(note.getValeur());

        return dto;
    }

    public Note toEntity(NoteDTO dto) {
        if (dto == null) {
            return null;
        }

        Note note = new Note();
        note.setId(dto.getId());
        note.setStudentId(dto.getStudentId());
        note.setMatiere(dto.getMatiere());
        note.setValeur(dto.getValeur());

        return note;
    }

    public List<NoteDTO> toDTOList(List<Note> notes) {
        if (notes == null) {
            return null;
        }

        return notes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Note> toEntityList(List<NoteDTO> noteDTOs) {
        if (noteDTOs == null) {
            return null;
        }

        return noteDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntity(Note existingNote, NoteDTO noteDTO) {
        if (noteDTO == null || existingNote == null) {
            return;
        }

        existingNote.setStudentId(noteDTO.getStudentId());
        existingNote.setMatiere(noteDTO.getMatiere());
        existingNote.setValeur(noteDTO.getValeur());
    }
}