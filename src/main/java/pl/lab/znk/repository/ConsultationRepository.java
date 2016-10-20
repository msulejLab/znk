package pl.lab.znk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lab.znk.domain.Consultation;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPlace(String place);
    List<Consultation> findByTeacherName(String teacherName);
    List<Consultation> findByTime(String time);
}
