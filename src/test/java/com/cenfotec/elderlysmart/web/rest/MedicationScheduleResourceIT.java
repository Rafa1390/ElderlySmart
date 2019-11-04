package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.MedicationSchedule;
import com.cenfotec.elderlysmart.repository.MedicationScheduleRepository;
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
 * Integration tests for the {@link MedicationScheduleResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class MedicationScheduleResourceIT {

    private static final Integer DEFAULT_ID_MEDICATION_SCHEDULE = 1;
    private static final Integer UPDATED_ID_MEDICATION_SCHEDULE = 2;

    private static final String DEFAULT_METICATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METICATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ELDERLY = "AAAAAAAAAA";
    private static final String UPDATED_ELDERLY = "BBBBBBBBBB";

    private static final String DEFAULT_DOSE = "AAAAAAAAAA";
    private static final String UPDATED_DOSE = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private MedicationScheduleRepository medicationScheduleRepository;

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

    private MockMvc restMedicationScheduleMockMvc;

    private MedicationSchedule medicationSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicationScheduleResource medicationScheduleResource = new MedicationScheduleResource(medicationScheduleRepository);
        this.restMedicationScheduleMockMvc = MockMvcBuilders.standaloneSetup(medicationScheduleResource)
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
    public static MedicationSchedule createEntity(EntityManager em) {
        MedicationSchedule medicationSchedule = new MedicationSchedule()
            .idMedicationSchedule(DEFAULT_ID_MEDICATION_SCHEDULE)
            .meticationName(DEFAULT_METICATION_NAME)
            .elderly(DEFAULT_ELDERLY)
            .dose(DEFAULT_DOSE)
            .time(DEFAULT_TIME)
            .state(DEFAULT_STATE);
        return medicationSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicationSchedule createUpdatedEntity(EntityManager em) {
        MedicationSchedule medicationSchedule = new MedicationSchedule()
            .idMedicationSchedule(UPDATED_ID_MEDICATION_SCHEDULE)
            .meticationName(UPDATED_METICATION_NAME)
            .elderly(UPDATED_ELDERLY)
            .dose(UPDATED_DOSE)
            .time(UPDATED_TIME)
            .state(UPDATED_STATE);
        return medicationSchedule;
    }

    @BeforeEach
    public void initTest() {
        medicationSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicationSchedule() throws Exception {
        int databaseSizeBeforeCreate = medicationScheduleRepository.findAll().size();

        // Create the MedicationSchedule
        restMedicationScheduleMockMvc.perform(post("/api/medication-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicationSchedule)))
            .andExpect(status().isCreated());

        // Validate the MedicationSchedule in the database
        List<MedicationSchedule> medicationScheduleList = medicationScheduleRepository.findAll();
        assertThat(medicationScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        MedicationSchedule testMedicationSchedule = medicationScheduleList.get(medicationScheduleList.size() - 1);
        assertThat(testMedicationSchedule.getIdMedicationSchedule()).isEqualTo(DEFAULT_ID_MEDICATION_SCHEDULE);
        assertThat(testMedicationSchedule.getMeticationName()).isEqualTo(DEFAULT_METICATION_NAME);
        assertThat(testMedicationSchedule.getElderly()).isEqualTo(DEFAULT_ELDERLY);
        assertThat(testMedicationSchedule.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testMedicationSchedule.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMedicationSchedule.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createMedicationScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicationScheduleRepository.findAll().size();

        // Create the MedicationSchedule with an existing ID
        medicationSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicationScheduleMockMvc.perform(post("/api/medication-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicationSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the MedicationSchedule in the database
        List<MedicationSchedule> medicationScheduleList = medicationScheduleRepository.findAll();
        assertThat(medicationScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicationSchedules() throws Exception {
        // Initialize the database
        medicationScheduleRepository.saveAndFlush(medicationSchedule);

        // Get all the medicationScheduleList
        restMedicationScheduleMockMvc.perform(get("/api/medication-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicationSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedicationSchedule").value(hasItem(DEFAULT_ID_MEDICATION_SCHEDULE)))
            .andExpect(jsonPath("$.[*].meticationName").value(hasItem(DEFAULT_METICATION_NAME)))
            .andExpect(jsonPath("$.[*].elderly").value(hasItem(DEFAULT_ELDERLY)))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getMedicationSchedule() throws Exception {
        // Initialize the database
        medicationScheduleRepository.saveAndFlush(medicationSchedule);

        // Get the medicationSchedule
        restMedicationScheduleMockMvc.perform(get("/api/medication-schedules/{id}", medicationSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicationSchedule.getId().intValue()))
            .andExpect(jsonPath("$.idMedicationSchedule").value(DEFAULT_ID_MEDICATION_SCHEDULE))
            .andExpect(jsonPath("$.meticationName").value(DEFAULT_METICATION_NAME))
            .andExpect(jsonPath("$.elderly").value(DEFAULT_ELDERLY))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingMedicationSchedule() throws Exception {
        // Get the medicationSchedule
        restMedicationScheduleMockMvc.perform(get("/api/medication-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicationSchedule() throws Exception {
        // Initialize the database
        medicationScheduleRepository.saveAndFlush(medicationSchedule);

        int databaseSizeBeforeUpdate = medicationScheduleRepository.findAll().size();

        // Update the medicationSchedule
        MedicationSchedule updatedMedicationSchedule = medicationScheduleRepository.findById(medicationSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedMedicationSchedule are not directly saved in db
        em.detach(updatedMedicationSchedule);
        updatedMedicationSchedule
            .idMedicationSchedule(UPDATED_ID_MEDICATION_SCHEDULE)
            .meticationName(UPDATED_METICATION_NAME)
            .elderly(UPDATED_ELDERLY)
            .dose(UPDATED_DOSE)
            .time(UPDATED_TIME)
            .state(UPDATED_STATE);

        restMedicationScheduleMockMvc.perform(put("/api/medication-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicationSchedule)))
            .andExpect(status().isOk());

        // Validate the MedicationSchedule in the database
        List<MedicationSchedule> medicationScheduleList = medicationScheduleRepository.findAll();
        assertThat(medicationScheduleList).hasSize(databaseSizeBeforeUpdate);
        MedicationSchedule testMedicationSchedule = medicationScheduleList.get(medicationScheduleList.size() - 1);
        assertThat(testMedicationSchedule.getIdMedicationSchedule()).isEqualTo(UPDATED_ID_MEDICATION_SCHEDULE);
        assertThat(testMedicationSchedule.getMeticationName()).isEqualTo(UPDATED_METICATION_NAME);
        assertThat(testMedicationSchedule.getElderly()).isEqualTo(UPDATED_ELDERLY);
        assertThat(testMedicationSchedule.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testMedicationSchedule.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMedicationSchedule.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicationSchedule() throws Exception {
        int databaseSizeBeforeUpdate = medicationScheduleRepository.findAll().size();

        // Create the MedicationSchedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicationScheduleMockMvc.perform(put("/api/medication-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicationSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the MedicationSchedule in the database
        List<MedicationSchedule> medicationScheduleList = medicationScheduleRepository.findAll();
        assertThat(medicationScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicationSchedule() throws Exception {
        // Initialize the database
        medicationScheduleRepository.saveAndFlush(medicationSchedule);

        int databaseSizeBeforeDelete = medicationScheduleRepository.findAll().size();

        // Delete the medicationSchedule
        restMedicationScheduleMockMvc.perform(delete("/api/medication-schedules/{id}", medicationSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicationSchedule> medicationScheduleList = medicationScheduleRepository.findAll();
        assertThat(medicationScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicationSchedule.class);
        MedicationSchedule medicationSchedule1 = new MedicationSchedule();
        medicationSchedule1.setId(1L);
        MedicationSchedule medicationSchedule2 = new MedicationSchedule();
        medicationSchedule2.setId(medicationSchedule1.getId());
        assertThat(medicationSchedule1).isEqualTo(medicationSchedule2);
        medicationSchedule2.setId(2L);
        assertThat(medicationSchedule1).isNotEqualTo(medicationSchedule2);
        medicationSchedule1.setId(null);
        assertThat(medicationSchedule1).isNotEqualTo(medicationSchedule2);
    }
}
