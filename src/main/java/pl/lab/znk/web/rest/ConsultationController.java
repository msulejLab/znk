package pl.lab.znk.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lab.znk.domain.Consultation;
import pl.lab.znk.repository.ConsultationRepository;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ConsultationController {

    @Autowired
    private ConsultationRepository consultationRepository;

    @RequestMapping(value = "/consultations", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultations(Pageable pageable) {
        return ResponseEntity.ok(consultationRepository.findAll(pageable));
    }

    @RequestMapping(value = "/consultations/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConsultation(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(consultationRepository.findOne(id));
    }

    @RequestMapping(value = "/consultations", method = RequestMethod.POST)
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) throws URISyntaxException {
        return ResponseEntity
                .created(new URI("/consultations/" + consultation.getId()))
                .body(consultationRepository.save(consultation));
    }

    @RequestMapping(value = "/consultation", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConsultation(@RequestBody Consultation consultation){
        return ResponseEntity.ok(consultationRepository.save(consultation));
    }

    @RequestMapping(value = "/consultations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteConsultation(@PathVariable(name = "id") Long id){
        consultationRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/consultations/{teacherName}")
    public ResponseEntity<?> getConsultationsByTeacher(@PathVariable(name = "teacherName") String teacherName){
        return ResponseEntity.ok(consultationRepository.findByTeacherName(teacherName));
    }

    @RequestMapping(value = "/consultations/{place}")
    public ResponseEntity<?> getConsultationsByPlace(@PathVariable(name = "place") String place){
        return ResponseEntity.ok(consultationRepository.findByPlace(place));
    }

}
