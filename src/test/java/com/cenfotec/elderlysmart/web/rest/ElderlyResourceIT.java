package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Elderly;
import com.cenfotec.elderlysmart.repository.ElderlyRepository;
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
 * Integration tests for the {@link ElderlyResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class ElderlyResourceIT {

    private static final String DEFAULT_ID_ELDERLY = "AAAAAAAAAA";
    private static final String UPDATED_ID_ELDERLY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private ElderlyRepository elderlyRepository;

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

    private MockMvc restElderlyMockMvc;

    private Elderly elderly;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElderlyResource elderlyResource = new ElderlyResource(elderlyRepository);
        this.restElderlyMockMvc = MockMvcBuilders.standaloneSetup(elderlyResource)
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
    public static Elderly createEntity(EntityManager em) {
        Elderly elderly = new Elderly()
            .idElderly(DEFAULT_ID_ELDERLY)
            .name(DEFAULT_NAME)
            .name2(DEFAULT_NAME_2)
            .lastName(DEFAULT_LAST_NAME)
            .lastName2(DEFAULT_LAST_NAME_2)
            .age(DEFAULT_AGE)
            .nationality(DEFAULT_NATIONALITY)
            .address(DEFAULT_ADDRESS)
            .admissionDate(DEFAULT_ADMISSION_DATE)
            .state(DEFAULT_STATE);
        return elderly;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elderly createUpdatedEntity(EntityManager em) {
        Elderly elderly = new Elderly()
            .idElderly(UPDATED_ID_ELDERLY)
            .name(UPDATED_NAME)
            .name2(UPDATED_NAME_2)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .age(UPDATED_AGE)
            .nationality(UPDATED_NATIONALITY)
            .address(UPDATED_ADDRESS)
            .admissionDate(UPDATED_ADMISSION_DATE)
            .state(UPDATED_STATE);
        return elderly;
    }

    @BeforeEach
    public void initTest() {
        elderly = createEntity(em);
    }

    @Test
    @Transactional
    public void createElderly() throws Exception {
        int databaseSizeBeforeCreate = elderlyRepository.findAll().size();

        // Create the Elderly
        restElderlyMockMvc.perform(post("/api/elderlies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elderly)))
            .andExpect(status().isCreated());

        // Validate the Elderly in the database
        List<Elderly> elderlyList = elderlyRepository.findAll();
        assertThat(elderlyList).hasSize(databaseSizeBeforeCreate + 1);
        Elderly testElderly = elderlyList.get(elderlyList.size() - 1);
        assertThat(testElderly.getIdElderly()).isEqualTo(DEFAULT_ID_ELDERLY);
        assertThat(testElderly.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElderly.getName2()).isEqualTo(DEFAULT_NAME_2);
        assertThat(testElderly.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testElderly.getLastName2()).isEqualTo(DEFAULT_LAST_NAME_2);
        assertThat(testElderly.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testElderly.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testElderly.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testElderly.getAdmissionDate()).isEqualTo(DEFAULT_ADMISSION_DATE);
        assertThat(testElderly.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createElderlyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elderlyRepository.findAll().size();

        // Create the Elderly with an existing ID
        elderly.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElderlyMockMvc.perform(post("/api/elderlies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elderly)))
            .andExpect(status().isBadRequest());

        // Validate the Elderly in the database
        List<Elderly> elderlyList = elderlyRepository.findAll();
        assertThat(elderlyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElderlies() throws Exception {
        // Initialize the database
        elderlyRepository.saveAndFlush(elderly);

        // Get all the elderlyList
        restElderlyMockMvc.perform(get("/api/elderlies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elderly.getId().intValue())))
            .andExpect(jsonPath("$.[*].idElderly").value(hasItem(DEFAULT_ID_ELDERLY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].name2").value(hasItem(DEFAULT_NAME_2)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].lastName2").value(hasItem(DEFAULT_LAST_NAME_2)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].admissionDate").value(hasItem(DEFAULT_ADMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getElderly() throws Exception {
        // Initialize the database
        elderlyRepository.saveAndFlush(elderly);

        // Get the elderly
        restElderlyMockMvc.perform(get("/api/elderlies/{id}", elderly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elderly.getId().intValue()))
            .andExpect(jsonPath("$.idElderly").value(DEFAULT_ID_ELDERLY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.name2").value(DEFAULT_NAME_2))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.lastName2").value(DEFAULT_LAST_NAME_2))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.admissionDate").value(DEFAULT_ADMISSION_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingElderly() throws Exception {
        // Get the elderly
        restElderlyMockMvc.perform(get("/api/elderlies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElderly() throws Exception {
        // Initialize the database
        elderlyRepository.saveAndFlush(elderly);

        int databaseSizeBeforeUpdate = elderlyRepository.findAll().size();

        // Update the elderly
        Elderly updatedElderly = elderlyRepository.findById(elderly.getId()).get();
        // Disconnect from session so that the updates on updatedElderly are not directly saved in db
        em.detach(updatedElderly);
        updatedElderly
            .idElderly(UPDATED_ID_ELDERLY)
            .name(UPDATED_NAME)
            .name2(UPDATED_NAME_2)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .age(UPDATED_AGE)
            .nationality(UPDATED_NATIONALITY)
            .address(UPDATED_ADDRESS)
            .admissionDate(UPDATED_ADMISSION_DATE)
            .state(UPDATED_STATE);

        restElderlyMockMvc.perform(put("/api/elderlies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElderly)))
            .andExpect(status().isOk());

        // Validate the Elderly in the database
        List<Elderly> elderlyList = elderlyRepository.findAll();
        assertThat(elderlyList).hasSize(databaseSizeBeforeUpdate);
        Elderly testElderly = elderlyList.get(elderlyList.size() - 1);
        assertThat(testElderly.getIdElderly()).isEqualTo(UPDATED_ID_ELDERLY);
        assertThat(testElderly.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElderly.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testElderly.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testElderly.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
        assertThat(testElderly.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testElderly.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testElderly.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testElderly.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
        assertThat(testElderly.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingElderly() throws Exception {
        int databaseSizeBeforeUpdate = elderlyRepository.findAll().size();

        // Create the Elderly

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElderlyMockMvc.perform(put("/api/elderlies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elderly)))
            .andExpect(status().isBadRequest());

        // Validate the Elderly in the database
        List<Elderly> elderlyList = elderlyRepository.findAll();
        assertThat(elderlyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElderly() throws Exception {
        // Initialize the database
        elderlyRepository.saveAndFlush(elderly);

        int databaseSizeBeforeDelete = elderlyRepository.findAll().size();

        // Delete the elderly
        restElderlyMockMvc.perform(delete("/api/elderlies/{id}", elderly.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Elderly> elderlyList = elderlyRepository.findAll();
        assertThat(elderlyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elderly.class);
        Elderly elderly1 = new Elderly();
        elderly1.setId(1L);
        Elderly elderly2 = new Elderly();
        elderly2.setId(elderly1.getId());
        assertThat(elderly1).isEqualTo(elderly2);
        elderly2.setId(2L);
        assertThat(elderly1).isNotEqualTo(elderly2);
        elderly1.setId(null);
        assertThat(elderly1).isNotEqualTo(elderly2);
    }
}
