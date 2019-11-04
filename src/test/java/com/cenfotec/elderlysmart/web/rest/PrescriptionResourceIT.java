package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Prescription;
import com.cenfotec.elderlysmart.repository.PrescriptionRepository;
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
 * Integration tests for the {@link PrescriptionResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class PrescriptionResourceIT {

    private static final Integer DEFAULT_ID_PRESCRIPTION = 1;
    private static final Integer UPDATED_ID_PRESCRIPTION = 2;

    private static final String DEFAULT_OFFICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PATIENT_AGE = 1;
    private static final Integer UPDATED_PATIENT_AGE = 2;

    private static final String DEFAULT_PRESCRIPTION_DRUGS = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRIPTION_DRUGS = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private PrescriptionRepository prescriptionRepository;

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

    private MockMvc restPrescriptionMockMvc;

    private Prescription prescription;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescriptionResource prescriptionResource = new PrescriptionResource(prescriptionRepository);
        this.restPrescriptionMockMvc = MockMvcBuilders.standaloneSetup(prescriptionResource)
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
    public static Prescription createEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .idPrescription(DEFAULT_ID_PRESCRIPTION)
            .officeName(DEFAULT_OFFICE_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .doctorName(DEFAULT_DOCTOR_NAME)
            .patientName(DEFAULT_PATIENT_NAME)
            .patientAge(DEFAULT_PATIENT_AGE)
            .prescriptionDrugs(DEFAULT_PRESCRIPTION_DRUGS)
            .state(DEFAULT_STATE);
        return prescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prescription createUpdatedEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .idPrescription(UPDATED_ID_PRESCRIPTION)
            .officeName(UPDATED_OFFICE_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .doctorName(UPDATED_DOCTOR_NAME)
            .patientName(UPDATED_PATIENT_NAME)
            .patientAge(UPDATED_PATIENT_AGE)
            .prescriptionDrugs(UPDATED_PRESCRIPTION_DRUGS)
            .state(UPDATED_STATE);
        return prescription;
    }

    @BeforeEach
    public void initTest() {
        prescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescription() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isCreated());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getIdPrescription()).isEqualTo(DEFAULT_ID_PRESCRIPTION);
        assertThat(testPrescription.getOfficeName()).isEqualTo(DEFAULT_OFFICE_NAME);
        assertThat(testPrescription.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPrescription.getDoctorName()).isEqualTo(DEFAULT_DOCTOR_NAME);
        assertThat(testPrescription.getPatientName()).isEqualTo(DEFAULT_PATIENT_NAME);
        assertThat(testPrescription.getPatientAge()).isEqualTo(DEFAULT_PATIENT_AGE);
        assertThat(testPrescription.getPrescriptionDrugs()).isEqualTo(DEFAULT_PRESCRIPTION_DRUGS);
        assertThat(testPrescription.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createPrescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription with an existing ID
        prescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrescriptions() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPrescription").value(hasItem(DEFAULT_ID_PRESCRIPTION)))
            .andExpect(jsonPath("$.[*].officeName").value(hasItem(DEFAULT_OFFICE_NAME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].doctorName").value(hasItem(DEFAULT_DOCTOR_NAME)))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME)))
            .andExpect(jsonPath("$.[*].patientAge").value(hasItem(DEFAULT_PATIENT_AGE)))
            .andExpect(jsonPath("$.[*].prescriptionDrugs").value(hasItem(DEFAULT_PRESCRIPTION_DRUGS)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getPrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescription.getId().intValue()))
            .andExpect(jsonPath("$.idPrescription").value(DEFAULT_ID_PRESCRIPTION))
            .andExpect(jsonPath("$.officeName").value(DEFAULT_OFFICE_NAME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.doctorName").value(DEFAULT_DOCTOR_NAME))
            .andExpect(jsonPath("$.patientName").value(DEFAULT_PATIENT_NAME))
            .andExpect(jsonPath("$.patientAge").value(DEFAULT_PATIENT_AGE))
            .andExpect(jsonPath("$.prescriptionDrugs").value(DEFAULT_PRESCRIPTION_DRUGS))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingPrescription() throws Exception {
        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Update the prescription
        Prescription updatedPrescription = prescriptionRepository.findById(prescription.getId()).get();
        // Disconnect from session so that the updates on updatedPrescription are not directly saved in db
        em.detach(updatedPrescription);
        updatedPrescription
            .idPrescription(UPDATED_ID_PRESCRIPTION)
            .officeName(UPDATED_OFFICE_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .doctorName(UPDATED_DOCTOR_NAME)
            .patientName(UPDATED_PATIENT_NAME)
            .patientAge(UPDATED_PATIENT_AGE)
            .prescriptionDrugs(UPDATED_PRESCRIPTION_DRUGS)
            .state(UPDATED_STATE);

        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescription)))
            .andExpect(status().isOk());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getIdPrescription()).isEqualTo(UPDATED_ID_PRESCRIPTION);
        assertThat(testPrescription.getOfficeName()).isEqualTo(UPDATED_OFFICE_NAME);
        assertThat(testPrescription.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPrescription.getDoctorName()).isEqualTo(UPDATED_DOCTOR_NAME);
        assertThat(testPrescription.getPatientName()).isEqualTo(UPDATED_PATIENT_NAME);
        assertThat(testPrescription.getPatientAge()).isEqualTo(UPDATED_PATIENT_AGE);
        assertThat(testPrescription.getPrescriptionDrugs()).isEqualTo(UPDATED_PRESCRIPTION_DRUGS);
        assertThat(testPrescription.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescription() throws Exception {
        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Create the Prescription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        int databaseSizeBeforeDelete = prescriptionRepository.findAll().size();

        // Delete the prescription
        restPrescriptionMockMvc.perform(delete("/api/prescriptions/{id}", prescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prescription.class);
        Prescription prescription1 = new Prescription();
        prescription1.setId(1L);
        Prescription prescription2 = new Prescription();
        prescription2.setId(prescription1.getId());
        assertThat(prescription1).isEqualTo(prescription2);
        prescription2.setId(2L);
        assertThat(prescription1).isNotEqualTo(prescription2);
        prescription1.setId(null);
        assertThat(prescription1).isNotEqualTo(prescription2);
    }
}
