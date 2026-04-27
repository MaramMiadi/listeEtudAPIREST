package maram.isett.gradingservice.repository;


import maram.isett.gradingservice.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // Find all notes for a specific student
    List<Note> findByStudentId(Long studentId);

    // Find notes by subject
    List<Note> findByMatiere(String matiere);

    // Find notes by student and subject
    Optional<Note> findByStudentIdAndMatiere(Long studentId, String matiere);

    // Find notes with grade above a certain value
    List<Note> findByValeurGreaterThan(Double valeur);

    // Find notes with grade below a certain value
    List<Note> findByValeurLessThan(Double valeur);

    // Delete all notes for a student
    void deleteByStudentId(Long studentId);

    // Custom query to calculate average grade for a student
    @Query("SELECT AVG(n.valeur) FROM Note n WHERE n.studentId = :studentId")
    Optional<Double> calculateAverageGradeByStudentId(@Param("studentId") Long studentId);

    // Custom query to get all subjects for a student
    @Query("SELECT DISTINCT n.matiere FROM Note n WHERE n.studentId = :studentId")
    List<String> findDistinctSubjectsByStudentId(@Param("studentId") Long studentId);

    // Count notes by student
    long countByStudentId(Long studentId);

    // Find top N notes for a student
    List<Note> findTop3ByStudentIdOrderByValeurDesc(Long studentId);
}
