package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.MedicalAppointment;
import com.cenfotec.elderlysmart.repository.MedicalAppointmentRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.cenfotec.elderlysmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicalAppointmentResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class MedicalAppointmentResourceIT {

    private static final Integer DEFAULT_ID_MEDICAL_APPOINTMENT = 1;
    private static final Integer UPDATED_ID_MEDICAL_APPOINTMENT = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;

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

    private MockMvc restMedicalAppointmentMockMvc;

    private MedicalAppointment medicalAppointment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalAppointmentResource medicalAppointmentResource = new MedicalAppointmentResource(medicalAppointmentRepository);
        this.restMedicalAppointmentMockMvc = MockMvcBuilders.standaloneSetup(medicalAppointmentResource)
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
    public static MedicalAppointment createEntity(EntityManager em) {
        MedicalAppointment medicalAppointment = new MedicalAppointment()
            .idMedicalAppointment(DEFAULT_ID_MEDICAL_APPOINTMENT)
            .date(DEFAULT_DATE)
            .time(DEFAULT_TIME)
            .state(DEFAULT_STATE);
        return medicalAppointment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalAppointment createUpdatedEntity(EntityManager em) {
        MedicalAppointment medicalAppointment = new MedicalAppointment()
            .idMedicalAppointment(UPDATED_ID_MEDICAL_APPOINTMENT)
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .state(UPDATED_STATE);
        return medicalAppointment;
    }

    @BeforeEach
    public void initTest() {
        medicalAppointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalAppointment() throws Exception {
        int databaseSizeBeforeCreate = medicalAppointmentRepository.findAll().size();

        // Create the MedicalAppointment
        restMedicalAppointmentMockMvc.perform(post("/api/medical-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAppointment)))
            .andExpect(status().isCreated());

        // Validate the MedicalAppointment in the database
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAll();
        assertThat(medicalAppointmentList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalAppointment testMedicalAppointment = medicalAppointmentList.get(medicalAppointmentList.size() - 1);
        assertThat(testMedicalAppointment.getIdMedicalAppointment()).isEqualTo(DEFAULT_ID_MEDICAL_APPOINTMENT);
        assertThat(testMedicalAppointment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMedicalAppointment.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMedicalAppointment.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createMedicalAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalAppointmentRepository.findAll().size();

        // Create the MedicalAppointment with an existing ID
        medicalAppointment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalAppointmentMockMvc.perform(post("/api/medical-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAppointment)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalAppointment in the database
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAll();
        assertThat(medicalAppointmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicalAppointments() throws Exception {
        // Initialize the database
        medicalAppointmentRepository.saveAndFlush(medicalAppointment);

        // Get all the medicalAppointmentList
        restMedicalAppointmentMockMvc.perform(get("/api/medical-appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalAppointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedicalAppointment").value(hasItem(DEFAULT_ID_MEDICAL_APPOINTMENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getMedicalAppointment() throws Exception {
        // Initialize the database
        medicalAppointmentRepository.saveAndFlush(medicalAppointment);

        // Get the medicalAppointment
        restMedicalAppointmentMockMvc.perform(get("/api/medical-appointments/{id}", medicalAppointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalAppointment.getId().intValue()))
            .andExpect(jsonPath("$.idMedicalAppointment").value(DEFAULT_ID_MEDICAL_APPOINTMENT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalAppointment() throws Exception {
        // Get the medicalAppointment
        restMedicalAppointmentMockMvc.perform(get("/api/medical-appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalAppointment() throws Exception {
        // Initialize the database
        medicalAppointmentRepository.saveAndFlush(medicalAppointment);

        int databaseSizeBeforeUpdate = medicalAppointmentRepository.findAll().size();

        // Update the medicalAppointment
        MedicalAppointment updatedMedicalAppointment = medicalAppointmentRepository.findById(medicalAppointment.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalAppointment are not directly saved in db
        em.detach(updatedMedicalAppointment);
        updatedMedicalAppointment
            .idMedicalAppointment(UPDATED_ID_MEDICAL_APPOINTMENT)
            .date(UPDATED_DATE)
            .time(UPDATED_TIME)
            .state(UPDATED_STATE);

        restMedicalAppointmentMockMvc.perform(put("/api/medical-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalAppointment)))
            .andExpect(status().isOk());

        // Validate the MedicalAppointment in the database
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAll();
        assertThat(medicalAppointmentList).hasSize(databaseSizeBeforeUpdate);
        MedicalAppointment testMedicalAppointment = medicalAppointmentList.get(medicalAppointmentList.size() - 1);
        assertThat(testMedicalAppointment.getIdMedicalAppointment()).isEqualTo(UPDATED_ID_MEDICAL_APPOINTMENT);
        assertThat(testMedicalAppointment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMedicalAppointment.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMedicalAppointment.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalAppointment() throws Exception {
        int databaseSizeBeforeUpdate = medicalAppointmentRepository.findAll().size();

        // Create the MedicalAppointment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalAppointmentMockMvc.perform(put("/api/medical-appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAppointment)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalAppointment in the database
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAll();
        assertThat(medicalAppointmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalAppointment() throws Exception {
        // Initialize the database
        medicalAppointmentRepository.saveAndFlush(medicalAppointment);

        int databaseSizeBeforeDelete = medicalAppointmentRepository.findAll().size();

        // Delete the medicalAppointment
        restMedicalAppointmentMockMvc.perform(delete("/api/medical-appointments/{id}", medicalAppointment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAll();
        assertThat(medicalAppointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalAppointment.class);
        MedicalAppointment medicalAppointment1 = new MedicalAppointment();
        medicalAppointment1.setId(1L);
        MedicalAppointment medicalAppointment2 = new MedicalAppointment();
        medicalAppointment2.setId(medicalAppointment1.getId());
        assertThat(medicalAppointment1).isEqualTo(medicalAppointment2);
        medicalAppointment2.setId(2L);
        assertThat(medicalAppointment1).isNotEqualTo(medicalAppointment2);
        medicalAppointment1.setId(null);
        assertThat(medicalAppointment1).isNotEqualTo(medicalAppointment2);
    }
}
