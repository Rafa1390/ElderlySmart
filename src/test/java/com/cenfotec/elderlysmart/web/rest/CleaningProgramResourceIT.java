package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.CleaningProgram;
import com.cenfotec.elderlysmart.repository.CleaningProgramRepository;
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
 * Integration tests for the {@link CleaningProgramResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class CleaningProgramResourceIT {

    private static final Integer DEFAULT_ID_CLEANING_PROGRAM = 1;
    private static final Integer UPDATED_ID_CLEANING_PROGRAM = 2;

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CLEANING_TASK = "AAAAAAAAAA";
    private static final String UPDATED_CLEANING_TASK = "BBBBBBBBBB";

    @Autowired
    private CleaningProgramRepository cleaningProgramRepository;

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

    private MockMvc restCleaningProgramMockMvc;

    private CleaningProgram cleaningProgram;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CleaningProgramResource cleaningProgramResource = new CleaningProgramResource(cleaningProgramRepository);
        this.restCleaningProgramMockMvc = MockMvcBuilders.standaloneSetup(cleaningProgramResource)
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
    public static CleaningProgram createEntity(EntityManager em) {
        CleaningProgram cleaningProgram = new CleaningProgram()
            .idCleaningProgram(DEFAULT_ID_CLEANING_PROGRAM)
            .day(DEFAULT_DAY)
            .time(DEFAULT_TIME)
            .cleaningTask(DEFAULT_CLEANING_TASK);
        return cleaningProgram;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CleaningProgram createUpdatedEntity(EntityManager em) {
        CleaningProgram cleaningProgram = new CleaningProgram()
            .idCleaningProgram(UPDATED_ID_CLEANING_PROGRAM)
            .day(UPDATED_DAY)
            .time(UPDATED_TIME)
            .cleaningTask(UPDATED_CLEANING_TASK);
        return cleaningProgram;
    }

    @BeforeEach
    public void initTest() {
        cleaningProgram = createEntity(em);
    }

    @Test
    @Transactional
    public void createCleaningProgram() throws Exception {
        int databaseSizeBeforeCreate = cleaningProgramRepository.findAll().size();

        // Create the CleaningProgram
        restCleaningProgramMockMvc.perform(post("/api/cleaning-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaningProgram)))
            .andExpect(status().isCreated());

        // Validate the CleaningProgram in the database
        List<CleaningProgram> cleaningProgramList = cleaningProgramRepository.findAll();
        assertThat(cleaningProgramList).hasSize(databaseSizeBeforeCreate + 1);
        CleaningProgram testCleaningProgram = cleaningProgramList.get(cleaningProgramList.size() - 1);
        assertThat(testCleaningProgram.getIdCleaningProgram()).isEqualTo(DEFAULT_ID_CLEANING_PROGRAM);
        assertThat(testCleaningProgram.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testCleaningProgram.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCleaningProgram.getCleaningTask()).isEqualTo(DEFAULT_CLEANING_TASK);
    }

    @Test
    @Transactional
    public void createCleaningProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cleaningProgramRepository.findAll().size();

        // Create the CleaningProgram with an existing ID
        cleaningProgram.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCleaningProgramMockMvc.perform(post("/api/cleaning-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaningProgram)))
            .andExpect(status().isBadRequest());

        // Validate the CleaningProgram in the database
        List<CleaningProgram> cleaningProgramList = cleaningProgramRepository.findAll();
        assertThat(cleaningProgramList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCleaningPrograms() throws Exception {
        // Initialize the database
        cleaningProgramRepository.saveAndFlush(cleaningProgram);

        // Get all the cleaningProgramList
        restCleaningProgramMockMvc.perform(get("/api/cleaning-programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cleaningProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCleaningProgram").value(hasItem(DEFAULT_ID_CLEANING_PROGRAM)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].cleaningTask").value(hasItem(DEFAULT_CLEANING_TASK)));
    }
    
    @Test
    @Transactional
    public void getCleaningProgram() throws Exception {
        // Initialize the database
        cleaningProgramRepository.saveAndFlush(cleaningProgram);

        // Get the cleaningProgram
        restCleaningProgramMockMvc.perform(get("/api/cleaning-programs/{id}", cleaningProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cleaningProgram.getId().intValue()))
            .andExpect(jsonPath("$.idCleaningProgram").value(DEFAULT_ID_CLEANING_PROGRAM))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.cleaningTask").value(DEFAULT_CLEANING_TASK));
    }

    @Test
    @Transactional
    public void getNonExistingCleaningProgram() throws Exception {
        // Get the cleaningProgram
        restCleaningProgramMockMvc.perform(get("/api/cleaning-programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCleaningProgram() throws Exception {
        // Initialize the database
        cleaningProgramRepository.saveAndFlush(cleaningProgram);

        int databaseSizeBeforeUpdate = cleaningProgramRepository.findAll().size();

        // Update the cleaningProgram
        CleaningProgram updatedCleaningProgram = cleaningProgramRepository.findById(cleaningProgram.getId()).get();
        // Disconnect from session so that the updates on updatedCleaningProgram are not directly saved in db
        em.detach(updatedCleaningProgram);
        updatedCleaningProgram
            .idCleaningProgram(UPDATED_ID_CLEANING_PROGRAM)
            .day(UPDATED_DAY)
            .time(UPDATED_TIME)
            .cleaningTask(UPDATED_CLEANING_TASK);

        restCleaningProgramMockMvc.perform(put("/api/cleaning-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCleaningProgram)))
            .andExpect(status().isOk());

        // Validate the CleaningProgram in the database
        List<CleaningProgram> cleaningProgramList = cleaningProgramRepository.findAll();
        assertThat(cleaningProgramList).hasSize(databaseSizeBeforeUpdate);
        CleaningProgram testCleaningProgram = cleaningProgramList.get(cleaningProgramList.size() - 1);
        assertThat(testCleaningProgram.getIdCleaningProgram()).isEqualTo(UPDATED_ID_CLEANING_PROGRAM);
        assertThat(testCleaningProgram.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testCleaningProgram.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCleaningProgram.getCleaningTask()).isEqualTo(UPDATED_CLEANING_TASK);
    }

    @Test
    @Transactional
    public void updateNonExistingCleaningProgram() throws Exception {
        int databaseSizeBeforeUpdate = cleaningProgramRepository.findAll().size();

        // Create the CleaningProgram

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCleaningProgramMockMvc.perform(put("/api/cleaning-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaningProgram)))
            .andExpect(status().isBadRequest());

        // Validate the CleaningProgram in the database
        List<CleaningProgram> cleaningProgramList = cleaningProgramRepository.findAll();
        assertThat(cleaningProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCleaningProgram() throws Exception {
        // Initialize the database
        cleaningProgramRepository.saveAndFlush(cleaningProgram);

        int databaseSizeBeforeDelete = cleaningProgramRepository.findAll().size();

        // Delete the cleaningProgram
        restCleaningProgramMockMvc.perform(delete("/api/cleaning-programs/{id}", cleaningProgram.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CleaningProgram> cleaningProgramList = cleaningProgramRepository.findAll();
        assertThat(cleaningProgramList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CleaningProgram.class);
        CleaningProgram cleaningProgram1 = new CleaningProgram();
        cleaningProgram1.setId(1L);
        CleaningProgram cleaningProgram2 = new CleaningProgram();
        cleaningProgram2.setId(cleaningProgram1.getId());
        assertThat(cleaningProgram1).isEqualTo(cleaningProgram2);
        cleaningProgram2.setId(2L);
        assertThat(cleaningProgram1).isNotEqualTo(cleaningProgram2);
        cleaningProgram1.setId(null);
        assertThat(cleaningProgram1).isNotEqualTo(cleaningProgram2);
    }
}
