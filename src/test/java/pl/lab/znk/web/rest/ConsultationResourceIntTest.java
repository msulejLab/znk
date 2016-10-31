package pl.lab.znk.web.rest;

import pl.lab.znk.ZnkApp;

import pl.lab.znk.domain.Consultation;
import pl.lab.znk.repository.ConsultationRepository;
import pl.lab.znk.service.ConsultationService;
import pl.lab.znk.service.dto.ConsultationDTO;
import pl.lab.znk.service.mapper.ConsultationMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultationResource REST controller.
 *
 * @see ConsultationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZnkApp.class)
public class ConsultationResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TIME_STR = dateTimeFormatter.format(DEFAULT_DATE_TIME);

    private static final Boolean DEFAULT_CANCELLED = false;
    private static final Boolean UPDATED_CANCELLED = true;

    @Inject
    private ConsultationRepository consultationRepository;

    @Inject
    private ConsultationMapper consultationMapper;

    @Inject
    private ConsultationService consultationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsultationResource consultationResource = new ConsultationResource();
        ReflectionTestUtils.setField(consultationResource, "consultationService", consultationService);
        this.restConsultationMockMvc = MockMvcBuilders.standaloneSetup(consultationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultation createEntity(EntityManager em) {
        Consultation consultation = new Consultation()
                .dateTime(DEFAULT_DATE_TIME)
                .cancelled(DEFAULT_CANCELLED);
        return consultation;
    }

    @Before
    public void initTest() {
        consultation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.consultationToConsultationDTO(consultation);

        restConsultationMockMvc.perform(post("/api/consultations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
                .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultations.get(consultations.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testConsultation.isCancelled()).isEqualTo(DEFAULT_CANCELLED);
    }

    @Test
    @Transactional
    public void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultations
        restConsultationMockMvc.perform(get("/api/consultations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME_STR))
            .andExpect(jsonPath("$.cancelled").value(DEFAULT_CANCELLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        Consultation updatedConsultation = consultationRepository.findOne(consultation.getId());
        updatedConsultation
                .dateTime(UPDATED_DATE_TIME)
                .cancelled(UPDATED_CANCELLED);
        ConsultationDTO consultationDTO = consultationMapper.consultationToConsultationDTO(updatedConsultation);

        restConsultationMockMvc.perform(put("/api/consultations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
                .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultations.get(consultations.size() - 1);
        assertThat(testConsultation.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testConsultation.isCancelled()).isEqualTo(UPDATED_CANCELLED);
    }

    @Test
    @Transactional
    public void deleteConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);
        int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Get the consultation
        restConsultationMockMvc.perform(delete("/api/consultations/{id}", consultation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
