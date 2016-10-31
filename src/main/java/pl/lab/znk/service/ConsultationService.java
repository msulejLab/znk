package pl.lab.znk.service;

import pl.lab.znk.domain.Consultation;
import pl.lab.znk.repository.ConsultationRepository;
import pl.lab.znk.service.dto.ConsultationDTO;
import pl.lab.znk.service.mapper.ConsultationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save
     * @return the persisted entity
     */
    public ConsultationDTO save(ConsultationDTO consultationDTO) {
        log.debug("Request to save Consultation : {}", consultationDTO);
        Consultation consultation = consultationMapper.consultationDTOToConsultation(consultationDTO);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.consultationToConsultationDTO(consultation);
        return result;
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