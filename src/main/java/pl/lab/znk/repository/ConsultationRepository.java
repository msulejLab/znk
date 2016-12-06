package pl.lab.znk.repository;

import pl.lab.znk.domain.Consultation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Consultation entity.
 */
@SuppressWarnings("unused")
public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    @Query("select consultation from Consultation consultation where consultation.teacher.login = ?#{principal.username}")
    List<Consultation> findByTeacherIsCurrentUser();

    @Query("select distinct consultation from Consultation consultation left join fetch consultation.registeredStudents")
    List<Consultation> findAllWithEagerRelationships();

    @Query("select consultation from Consultation consultation left join fetch consultation.registeredStudents where consultation.id =:id")
    Consultation findOneWithEagerRelationships(@Param("id") Long id);

    List<Consultation> findByTeacher_id(Long teacherId);

    @Query("select c from Consultation c join c.registeredStudents rs where rs.id = :studentId")
    List<Consultation> findByIdInRegisteredStudents(@Param("studentId") Long studentId);
}
