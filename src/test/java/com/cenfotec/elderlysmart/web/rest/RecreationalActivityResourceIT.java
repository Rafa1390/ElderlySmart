package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.RecreationalActivity;
import com.cenfotec.elderlysmart.repository.RecreationalActivityRepository;
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
 * Integration tests for the {@link RecreationalActivityResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class RecreationalActivityResourceIT {

    private static final Integer DEFAULT_ID_RECREATIONAL_ACTIVITY = 1;
    private static final Integer UPDATED_ID_RECREATIONAL_ACTIVITY = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private RecreationalActivityRepository recreationalActivityRepository;

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

    private MockMvc restRecreationalActivityMockMvc;

    private RecreationalActivity recreationalActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecreationalActivityResource recreationalActivityResource = new RecreationalActivityResource(recreationalActivityRepository);
        this.restRecreationalActivityMockMvc = MockMvcBuilders.standaloneSetup(recreationalActivityResource)
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
    public static RecreationalActivity createEntity(EntityManager em) {
        RecreationalActivity recreationalActivity = new RecreationalActivity()
            .idRecreationalActivity(DEFAULT_ID_RECREATIONAL_ACTIVITY)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .state(DEFAULT_STATE);
        return recreationalActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecreationalActivity createUpdatedEntity(EntityManager em) {
        RecreationalActivity recreationalActivity = new RecreationalActivity()
            .idRecreationalActivity(UPDATED_ID_RECREATIONAL_ACTIVITY)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .state(UPDATED_STATE);
        return recreationalActivity;
    }

    @BeforeEach
    public void initTest() {
        recreationalActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecreationalActivity() throws Exception {
        int databaseSizeBeforeCreate = recreationalActivityRepository.findAll().size();

        // Create the RecreationalActivity
        restRecreationalActivityMockMvc.perform(post("/api/recreational-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recreationalActivity)))
            .andExpect(status().isCreated());

        // Validate the RecreationalActivity in the database
        List<RecreationalActivity> recreationalActivityList = recreationalActivityRepository.findAll();
        assertThat(recreationalActivityList).hasSize(databaseSizeBeforeCreate + 1);
        RecreationalActivity testRecreationalActivity = recreationalActivityList.get(recreationalActivityList.size() - 1);
        assertThat(testRecreationalActivity.getIdRecreationalActivity()).isEqualTo(DEFAULT_ID_RECREATIONAL_ACTIVITY);
        assertThat(testRecreationalActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecreationalActivity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecreationalActivity.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRecreationalActivity.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testRecreationalActivity.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testRecreationalActivity.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createRecreationalActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recreationalActivityRepository.findAll().size();

        // Create the RecreationalActivity with an existing ID
        recreationalActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecreationalActivityMockMvc.perform(post("/api/recreational-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recreationalActivity)))
            .andExpect(status().isBadRequest());

        // Validate the RecreationalActivity in the database
        List<RecreationalActivity> recreationalActivityList = recreationalActivityRepository.findAll();
        assertThat(recreationalActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecreationalActivities() throws Exception {
        // Initialize the database
        recreationalActivityRepository.saveAndFlush(recreationalActivity);

        // Get all the recreationalActivityList
        restRecreationalActivityMockMvc.perform(get("/api/recreational-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recreationalActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRecreationalActivity").value(hasItem(DEFAULT_ID_RECREATIONAL_ACTIVITY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getRecreationalActivity() throws Exception {
        // Initialize the database
        recreationalActivityRepository.saveAndFlush(recreationalActivity);

        // Get the recreationalActivity
        restRecreationalActivityMockMvc.perform(get("/api/recreational-activities/{id}", recreationalActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recreationalActivity.getId().intValue()))
            .andExpect(jsonPath("$.idRecreationalActivity").value(DEFAULT_ID_RECREATIONAL_ACTIVITY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingRecreationalActivity() throws Exception {
        // Get the recreationalActivity
        restRecreationalActivityMockMvc.perform(get("/api/recreational-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecreationalActivity() throws Exception {
        // Initialize the database
        recreationalActivityRepository.saveAndFlush(recreationalActivity);

        int databaseSizeBeforeUpdate = recreationalActivityRepository.findAll().size();

        // Update the recreationalActivity
        RecreationalActivity updatedRecreationalActivity = recreationalActivityRepository.findById(recreationalActivity.getId()).get();
        // Disconnect from session so that the updates on updatedRecreationalActivity are not directly saved in db
        em.detach(updatedRecreationalActivity);
        updatedRecreationalActivity
            .idRecreationalActivity(UPDATED_ID_RECREATIONAL_ACTIVITY)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .state(UPDATED_STATE);

        restRecreationalActivityMockMvc.perform(put("/api/recreational-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecreationalActivity)))
            .andExpect(status().isOk());

        // Validate the RecreationalActivity in the database
        List<RecreationalActivity> recreationalActivityList = recreationalActivityRepository.findAll();
        assertThat(recreationalActivityList).hasSize(databaseSizeBeforeUpdate);
        RecreationalActivity testRecreationalActivity = recreationalActivityList.get(recreationalActivityList.size() - 1);
        assertThat(testRecreationalActivity.getIdRecreationalActivity()).isEqualTo(UPDATED_ID_RECREATIONAL_ACTIVITY);
        assertThat(testRecreationalActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecreationalActivity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecreationalActivity.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRecreationalActivity.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testRecreationalActivity.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testRecreationalActivity.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecreationalActivity() throws Exception {
        int databaseSizeBeforeUpdate = recreationalActivityRepository.findAll().size();

        // Create the RecreationalActivity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecreationalActivityMockMvc.perform(put("/api/recreational-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recreationalActivity)))
            .andExpect(status().isBadRequest());

        // Validate the RecreationalActivity in the database
        List<RecreationalActivity> recreationalActivityList = recreationalActivityRepository.findAll();
        assertThat(recreationalActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecreationalActivity() throws Exception {
        // Initialize the database
        recreationalActivityRepository.saveAndFlush(recreationalActivity);

        int databaseSizeBeforeDelete = recreationalActivityRepository.findAll().size();

        // Delete the recreationalActivity
        restRecreationalActivityMockMvc.perform(delete("/api/recreational-activities/{id}", recreationalActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecreationalActivity> recreationalActivityList = recreationalActivityRepository.findAll();
        assertThat(recreationalActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecreationalActivity.class);
        RecreationalActivity recreationalActivity1 = new RecreationalActivity();
        recreationalActivity1.setId(1L);
        RecreationalActivity recreationalActivity2 = new RecreationalActivity();
        recreationalActivity2.setId(recreationalActivity1.getId());
        assertThat(recreationalActivity1).isEqualTo(recreationalActivity2);
        recreationalActivity2.setId(2L);
        assertThat(recreationalActivity1).isNotEqualTo(recreationalActivity2);
        recreationalActivity1.setId(null);
        assertThat(recreationalActivity1).isNotEqualTo(recreationalActivity2);
    }
}
