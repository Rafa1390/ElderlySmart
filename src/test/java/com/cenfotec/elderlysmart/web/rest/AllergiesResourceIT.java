package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Allergies;
import com.cenfotec.elderlysmart.repository.AllergiesRepository;
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
 * Integration tests for the {@link AllergiesResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class AllergiesResourceIT {

    private static final Integer DEFAULT_ID_ALLERGIES = 1;
    private static final Integer UPDATED_ID_ALLERGIES = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AllergiesRepository allergiesRepository;

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

    private MockMvc restAllergiesMockMvc;

    private Allergies allergies;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergiesResource allergiesResource = new AllergiesResource(allergiesRepository);
        this.restAllergiesMockMvc = MockMvcBuilders.standaloneSetup(allergiesResource)
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
    public static Allergies createEntity(EntityManager em) {
        Allergies allergies = new Allergies()
            .idAllergies(DEFAULT_ID_ALLERGIES)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return allergies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allergies createUpdatedEntity(EntityManager em) {
        Allergies allergies = new Allergies()
            .idAllergies(UPDATED_ID_ALLERGIES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return allergies;
    }

    @BeforeEach
    public void initTest() {
        allergies = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergies() throws Exception {
        int databaseSizeBeforeCreate = allergiesRepository.findAll().size();

        // Create the Allergies
        restAllergiesMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergies)))
            .andExpect(status().isCreated());

        // Validate the Allergies in the database
        List<Allergies> allergiesList = allergiesRepository.findAll();
        assertThat(allergiesList).hasSize(databaseSizeBeforeCreate + 1);
        Allergies testAllergies = allergiesList.get(allergiesList.size() - 1);
        assertThat(testAllergies.getIdAllergies()).isEqualTo(DEFAULT_ID_ALLERGIES);
        assertThat(testAllergies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAllergies.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAllergiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergiesRepository.findAll().size();

        // Create the Allergies with an existing ID
        allergies.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergiesMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergies)))
            .andExpect(status().isBadRequest());

        // Validate the Allergies in the database
        List<Allergies> allergiesList = allergiesRepository.findAll();
        assertThat(allergiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllergies() throws Exception {
        // Initialize the database
        allergiesRepository.saveAndFlush(allergies);

        // Get all the allergiesList
        restAllergiesMockMvc.perform(get("/api/allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergies.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAllergies").value(hasItem(DEFAULT_ID_ALLERGIES)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAllergies() throws Exception {
        // Initialize the database
        allergiesRepository.saveAndFlush(allergies);

        // Get the allergies
        restAllergiesMockMvc.perform(get("/api/allergies/{id}", allergies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergies.getId().intValue()))
            .andExpect(jsonPath("$.idAllergies").value(DEFAULT_ID_ALLERGIES))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAllergies() throws Exception {
        // Get the allergies
        restAllergiesMockMvc.perform(get("/api/allergies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergies() throws Exception {
        // Initialize the database
        allergiesRepository.saveAndFlush(allergies);

        int databaseSizeBeforeUpdate = allergiesRepository.findAll().size();

        // Update the allergies
        Allergies updatedAllergies = allergiesRepository.findById(allergies.getId()).get();
        // Disconnect from session so that the updates on updatedAllergies are not directly saved in db
        em.detach(updatedAllergies);
        updatedAllergies
            .idAllergies(UPDATED_ID_ALLERGIES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restAllergiesMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllergies)))
            .andExpect(status().isOk());

        // Validate the Allergies in the database
        List<Allergies> allergiesList = allergiesRepository.findAll();
        assertThat(allergiesList).hasSize(databaseSizeBeforeUpdate);
        Allergies testAllergies = allergiesList.get(allergiesList.size() - 1);
        assertThat(testAllergies.getIdAllergies()).isEqualTo(UPDATED_ID_ALLERGIES);
        assertThat(testAllergies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAllergies.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergies() throws Exception {
        int databaseSizeBeforeUpdate = allergiesRepository.findAll().size();

        // Create the Allergies

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllergiesMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergies)))
            .andExpect(status().isBadRequest());

        // Validate the Allergies in the database
        List<Allergies> allergiesList = allergiesRepository.findAll();
        assertThat(allergiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllergies() throws Exception {
        // Initialize the database
        allergiesRepository.saveAndFlush(allergies);

        int databaseSizeBeforeDelete = allergiesRepository.findAll().size();

        // Delete the allergies
        restAllergiesMockMvc.perform(delete("/api/allergies/{id}", allergies.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allergies> allergiesList = allergiesRepository.findAll();
        assertThat(allergiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergies.class);
        Allergies allergies1 = new Allergies();
        allergies1.setId(1L);
        Allergies allergies2 = new Allergies();
        allergies2.setId(allergies1.getId());
        assertThat(allergies1).isEqualTo(allergies2);
        allergies2.setId(2L);
        assertThat(allergies1).isNotEqualTo(allergies2);
        allergies1.setId(null);
        assertThat(allergies1).isNotEqualTo(allergies2);
    }
}
