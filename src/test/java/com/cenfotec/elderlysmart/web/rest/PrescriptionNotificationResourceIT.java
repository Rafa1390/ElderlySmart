package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.PrescriptionNotification;
import com.cenfotec.elderlysmart.repository.PrescriptionNotificationRepository;
import com.cenfotec.elderlysmart.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cenfotec.elderlysmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrescriptionNotificationResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class PrescriptionNotificationResourceIT {

    private static final Integer DEFAULT_ID_PRESCRIPTION_NOTIFICATION = 1;
    private static final Integer UPDATED_ID_PRESCRIPTION_NOTIFICATION = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private PrescriptionNotificationRepository prescriptionNotificationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPrescriptionNotificationMockMvc;

    private PrescriptionNotification prescriptionNotification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescriptionNotificationResource prescriptionNotificationResource = new PrescriptionNotificationResource(prescriptionNotificationRepository);
        this.restPrescriptionNotificationMockMvc = MockMvcBuilders.standaloneSetup(prescriptionNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescriptionNotification createEntity(EntityManager em) {
        PrescriptionNotification prescriptionNotification = new PrescriptionNotification()
            .idPrescriptionNotification(DEFAULT_ID_PRESCRIPTION_NOTIFICATION)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE);
        return prescriptionNotification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrescriptionNotification createUpdatedEntity(EntityManager em) {
        PrescriptionNotification prescriptionNotification = new PrescriptionNotification()
            .idPrescriptionNotification(UPDATED_ID_PRESCRIPTION_NOTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);
        return prescriptionNotification;
    }

    @BeforeEach
    public void initTest() {
        prescriptionNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescriptionNotification() throws Exception {
        int databaseSizeBeforeCreate = prescriptionNotificationRepository.findAll().size();

        // Create the PrescriptionNotification
        restPrescriptionNotificationMockMvc.perform(post("/api/prescription-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescriptionNotification)))
            .andExpect(status().isCreated());

        // Validate the PrescriptionNotification in the database
        List<PrescriptionNotification> prescriptionNotificationList = prescriptionNotificationRepository.findAll();
        assertThat(prescriptionNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        PrescriptionNotification testPrescriptionNotification = prescriptionNotificationList.get(prescriptionNotificationList.size() - 1);
        assertThat(testPrescriptionNotification.getIdPrescriptionNotification()).isEqualTo(DEFAULT_ID_PRESCRIPTION_NOTIFICATION);
        assertThat(testPrescriptionNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPrescriptionNotification.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createPrescriptionNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescriptionNotificationRepository.findAll().size();

        // Create the PrescriptionNotification with an existing ID
        prescriptionNotification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionNotificationMockMvc.perform(post("/api/prescription-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescriptionNotification)))
            .andExpect(status().isBadRequest());

        // Validate the PrescriptionNotification in the database
        List<PrescriptionNotification> prescriptionNotificationList = prescriptionNotificationRepository.findAll();
        assertThat(prescriptionNotificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrescriptionNotifications() throws Exception {
        // Initialize the database
        prescriptionNotificationRepository.saveAndFlush(prescriptionNotification);

        // Get all the prescriptionNotificationList
        restPrescriptionNotificationMockMvc.perform(get("/api/prescription-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescriptionNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPrescriptionNotification").value(hasItem(DEFAULT_ID_PRESCRIPTION_NOTIFICATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getPrescriptionNotification() throws Exception {
        // Initialize the database
        prescriptionNotificationRepository.saveAndFlush(prescriptionNotification);

        // Get the prescriptionNotification
        restPrescriptionNotificationMockMvc.perform(get("/api/prescription-notifications/{id}", prescriptionNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescriptionNotification.getId().intValue()))
            .andExpect(jsonPath("$.idPrescriptionNotification").value(DEFAULT_ID_PRESCRIPTION_NOTIFICATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingPrescriptionNotification() throws Exception {
        // Get the prescriptionNotification
        restPrescriptionNotificationMockMvc.perform(get("/api/prescription-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescriptionNotification() throws Exception {
        // Initialize the database
        prescriptionNotificationRepository.saveAndFlush(prescriptionNotification);

        int databaseSizeBeforeUpdate = prescriptionNotificationRepository.findAll().size();

        // Update the prescriptionNotification
        PrescriptionNotification updatedPrescriptionNotification = prescriptionNotificationRepository.findById(prescriptionNotification.getId()).get();
        // Disconnect from session so that the updates on updatedPrescriptionNotification are not directly saved in db
        em.detach(updatedPrescriptionNotification);
        updatedPrescriptionNotification
            .idPrescriptionNotification(UPDATED_ID_PRESCRIPTION_NOTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);

        restPrescriptionNotificationMockMvc.perform(put("/api/prescription-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescriptionNotification)))
            .andExpect(status().isOk());

        // Validate the PrescriptionNotification in the database
        List<PrescriptionNotification> prescriptionNotificationList = prescriptionNotificationRepository.findAll();
        assertThat(prescriptionNotificationList).hasSize(databaseSizeBeforeUpdate);
        PrescriptionNotification testPrescriptionNotification = prescriptionNotificationList.get(prescriptionNotificationList.size() - 1);
        assertThat(testPrescriptionNotification.getIdPrescriptionNotification()).isEqualTo(UPDATED_ID_PRESCRIPTION_NOTIFICATION);
        assertThat(testPrescriptionNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPrescriptionNotification.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescriptionNotification() throws Exception {
        int databaseSizeBeforeUpdate = prescriptionNotificationRepository.findAll().size();

        // Create the PrescriptionNotification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionNotificationMockMvc.perform(put("/api/prescription-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescriptionNotification)))
            .andExpect(status().isBadRequest());

        // Validate the PrescriptionNotification in the database
        List<PrescriptionNotification> prescriptionNotificationList = prescriptionNotificationRepository.findAll();
        assertThat(prescriptionNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrescriptionNotification() throws Exception {
        // Initialize the database
        prescriptionNotificationRepository.saveAndFlush(prescriptionNotification);

        int databaseSizeBeforeDelete = prescriptionNotificationRepository.findAll().size();

        // Delete the prescriptionNotification
        restPrescriptionNotificationMockMvc.perform(delete("/api/prescription-notifications/{id}", prescriptionNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrescriptionNotification> prescriptionNotificationList = prescriptionNotificationRepository.findAll();
        assertThat(prescriptionNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescriptionNotification.class);
        PrescriptionNotification prescriptionNotification1 = new PrescriptionNotification();
        prescriptionNotification1.setId(1L);
        PrescriptionNotification prescriptionNotification2 = new PrescriptionNotification();
        prescriptionNotification2.setId(prescriptionNotification1.getId());
        assertThat(prescriptionNotification1).isEqualTo(prescriptionNotification2);
        prescriptionNotification2.setId(2L);
        assertThat(prescriptionNotification1).isNotEqualTo(prescriptionNotification2);
        prescriptionNotification1.setId(null);
        assertThat(prescriptionNotification1).isNotEqualTo(prescriptionNotification2);
    }
}
