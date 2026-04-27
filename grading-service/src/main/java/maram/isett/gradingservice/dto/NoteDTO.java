package maram.isett.gradingservice.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class NoteDTO {
    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Subject is required")
    private String matiere;

    @NotNull(message = "Grade is required")
    @Min(value = 0, message = "Grade must be at least 0")
    @Max(value = 20, message = "Grade cannot exceed 20")
    private Double valeur;
}
