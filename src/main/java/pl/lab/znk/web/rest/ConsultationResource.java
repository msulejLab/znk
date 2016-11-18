package pl.lab.znk.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lab.znk.service.ConsultationService;
import pl.lab.znk.service.dto.ConsultationDTO;
import pl.lab.znk.web.rest.util.HeaderUtil;
import pl.lab.znk.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consultation.
 */
@RestController
@RequestMapping("/api")
public class ConsultationResource {

    private final Logger log = LoggerFactory.getLogger(ConsultationResource.class);

    @Inject
    private ConsultationService consultationService;

    @RequestMapping(value = "/consultations/{id}/cancel",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> makeCancelled(@PathVariable(name = "id") Long consultationId) throws URISyntaxException {
        log.debug("REST request to cancel Consultation: {}", consultationId);
        ConsultationDTO result = consultationService.makeCancelled(consultationId);
        return ResponseEntity.created(new URI("/api/consultations/" + consultationId + "/cancel"))
                .headers(HeaderUtil.createEntityCreationAlert("consultation", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/consultations/{id}/book/{studentId}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> bookConsultation(@PathVariable(name = "id") Long consultationId,
                                                            @PathVariable(name = "studentId") Long studentId) throws URISyntaxException{
        log.debug("REST request to book Consultation: {} by student id: {}", consultationId, studentId);
        ConsultationDTO result = consultationService.bookConsultation(consultationId, studentId);
        return ResponseEntity.created(new URI("/api/consultations/" + consultationId + "bookBy/" + studentId))
                .headers(HeaderUtil.createEntityCreationAlert("consultation", result.getId().toString()))
                .body(result);
    }

    @RequestMapping(value = "/consultations/{id}/unBook/{studentId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> unBookConsultation(@PathVariable(name = "id") Long consultationId,
                                                              @PathVariable(name = "studentId") Long studentId) throws URISyntaxException{
        log.debug("REST request to unBook Consultation: {} by student id: {}", consultationId, studentId);
        ConsultationDTO result = consultationService.unBookConsultation(consultationId, studentId);
        return ResponseEntity.created(new URI("/api/consultations/" + consultationId + "bookBy/" + studentId))
                .headers(HeaderUtil.createEntityCreationAlert("consultation", result.getId().toString()))
                .body(result);
    }

    /**
     * POST  /consultations : Create a new consultation.
     *
     * @param consultationDTO the consultationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultationDTO, or with status 400 (Bad Request) if the consultation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> createConsultation(@RequestBody ConsultationDTO consultationDTO) throws URISyntaxException {
        log.debug("REST request to save Consultation : {}", consultationDTO);
        if (consultationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("consultation", "idexists", "A new consultation cannot already have an ID")).body(null);
        }
        ConsultationDTO result = consultationService.create(consultationDTO);
        return ResponseEntity.created(new URI("/api/consultations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("consultation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultations : Updates an existing consultation.
     *
     * @param consultationDTO the consultationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultationDTO,
     * or with status 400 (Bad Request) if the consultationDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> updateConsultation(@RequestBody ConsultationDTO consultationDTO) throws URISyntaxException {
        log.debug("REST request to update Consultation : {}", consultationDTO);
        if (consultationDTO.getId() == null) {
            return createConsultation(consultationDTO);
        }
        ConsultationDTO result = consultationService.update(consultationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("consultation", consultationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultations : get all the consultations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Consultations");
        Page<ConsultationDTO> page = consultationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultations/:id : get the "id" consultation.
     *
     * @param id the id of the consultationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/consultations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsultationDTO> getConsultation(@PathVariable Long id) {
        log.debug("REST request to get Consultation : {}", id);
        ConsultationDTO consultationDTO = consultationService.findOne(id);
        return Optional.ofNullable(consultationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /consultations/:id : delete the "id" consultation.
     *
     * @param id the id of the consultationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/consultations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        log.debug("REST request to delete Consultation : {}", id);
        consultationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("consultation", id.toString())).build();
    }

}
