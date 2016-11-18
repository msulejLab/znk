package pl.lab.znk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lab.znk.domain.Consultation;
import pl.lab.znk.domain.User;
import pl.lab.znk.repository.ConsultationRepository;
import pl.lab.znk.service.dto.ConsultationDTO;
import pl.lab.znk.service.mapper.ConsultationMapper;

import javax.inject.Inject;

/**
 * Service Implementation for managing Consultation.
 */
@Service
@Transactional
public class ConsultationService {

    private final Logger log = LoggerFactory.getLogger(ConsultationService.class);

    @Inject
    private ConsultationRepository consultationRepository;

    @Inject
    private ConsultationMapper consultationMapper;

    @Inject
    private UserService userService;

    /**
     * Cancel consultation
     * @param consultationId consultation id
     * @return persisted entity
     */
    public ConsultationDTO makeCancelled(Long consultationId){
        log.debug("Request to cancel consultation id: {}", consultationId);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(consultationId);
        consultation.setCancelled(true);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.consultationToConsultationDTO(consultation);
        return result;
    }

    /**
     * Book consultation by student
     *
     * @param consultationId consultation id
     * @param studentId student id
     * @return the persisted entity
     */
    public ConsultationDTO bookConsultation(Long consultationId, Long studentId){
        log.debug("Request to book consultation id: {}, by student id: {}", consultationId, studentId);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(consultationId);
        User student = userService.getUserById(studentId);
        consultation.addRegisteredStudents(student);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.consultationToConsultationDTO(consultation);
        return result;
    }

    /**
     * UnBook consultation by student
     *
     * @param consultationId
     * @param studentId
     * @return
     */
    public ConsultationDTO unBookConsultation(Long consultationId, Long studentId){
        log.debug("Request to unBook consultation id: {}, by student id: {}", consultationId, studentId);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(consultationId);
        User student = userService.getUserById(studentId);
        consultation.getRegisteredStudents().remove(student);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.consultationToConsultationDTO(consultation);
        return result;
    }

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save
     * @return the persisted entity
     */
    public ConsultationDTO create(ConsultationDTO consultationDTO) {
        log.debug("Request to save Consultation : {}", consultationDTO);
        Consultation consultation = consultationMapper.consultationDTOToConsultation(consultationDTO);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.consultationToConsultationDTO(consultation);
        return result;
    }

    public ConsultationDTO update(ConsultationDTO consultationDTO) {
        Consultation existingConsultation = consultationRepository.findOne(consultationDTO.getId());

        existingConsultation.setDescription(consultationDTO.getDescription());
        existingConsultation.setCancelled(consultationDTO.getCancelled());

        Consultation consultation = consultationRepository.save(existingConsultation);
        return consultationMapper.consultationToConsultationDTO(consultation);
    }

    /**
     *  Get all the consultations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConsultationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consultations");
        Page<Consultation> result = consultationRepository.findAll(pageable);
        return result.map(consultation -> consultationMapper.consultationToConsultationDTO(consultation));
    }

    /**
     *  Get one consultation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ConsultationDTO findOne(Long id) {
        log.debug("Request to get Consultation : {}", id);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(id);
        ConsultationDTO consultationDTO = consultationMapper.consultationToConsultationDTO(consultation);
        return consultationDTO;
    }

    /**
     *  Delete the  consultation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Consultation : {}", id);
        consultationRepository.delete(id);
    }
}
