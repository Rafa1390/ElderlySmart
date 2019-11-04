package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Pathologies;
import com.cenfotec.elderlysmart.repository.PathologiesRepository;
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
 * Integration tests for the {@link PathologiesResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class PathologiesResourceIT {

    private static final Integer DEFAULT_ID_PATHOLOGIES = 1;
    private static final Integer UPDATED_ID_PATHOLOGIES = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PathologiesRepository pathologiesRepository;

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

    private MockMvc restPathologiesMockMvc;

    private Pathologies pathologies;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PathologiesResource pathologiesResource = new PathologiesResource(pathologiesRepository);
        this.restPathologiesMockMvc = MockMvcBuilders.standaloneSetup(pathologiesResource)
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
    public static Pathologies createEntity(EntityManager em) {
        Pathologies pathologies = new Pathologies()
            .idPathologies(DEFAULT_ID_PATHOLOGIES)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return pathologies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pathologies createUpdatedEntity(EntityManager em) {
        Pathologies pathologies = new Pathologies()
            .idPathologies(UPDATED_ID_PATHOLOGIES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return pathologies;
    }

    @BeforeEach
    public void initTest() {
        pathologies = createEntity(em);
    }

    @Test
    @Transactional
    public void createPathologies() throws Exception {
        int databaseSizeBeforeCreate = pathologiesRepository.findAll().size();

        // Create the Pathologies
        restPathologiesMockMvc.perform(post("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathologies)))
            .andExpect(status().isCreated());

        // Validate the Pathologies in the database
        List<Pathologies> pathologiesList = pathologiesRepository.findAll();
        assertThat(pathologiesList).hasSize(databaseSizeBeforeCreate + 1);
        Pathologies testPathologies = pathologiesList.get(pathologiesList.size() - 1);
        assertThat(testPathologies.getIdPathologies()).isEqualTo(DEFAULT_ID_PATHOLOGIES);
        assertThat(testPathologies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPathologies.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPathologiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pathologiesRepository.findAll().size();

        // Create the Pathologies with an existing ID
        pathologies.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathologiesMockMvc.perform(post("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathologies)))
            .andExpect(status().isBadRequest());

        // Validate the Pathologies in the database
        List<Pathologies> pathologiesList = pathologiesRepository.findAll();
        assertThat(pathologiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPathologies() throws Exception {
        // Initialize the database
        pathologiesRepository.saveAndFlush(pathologies);

        // Get all the pathologiesList
        restPathologiesMockMvc.perform(get("/api/pathologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pathologies.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPathologies").value(hasItem(DEFAULT_ID_PATHOLOGIES)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPathologies() throws Exception {
        // Initialize the database
        pathologiesRepository.saveAndFlush(pathologies);

        // Get the pathologies
        restPathologiesMockMvc.perform(get("/api/pathologies/{id}", pathologies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pathologies.getId().intValue()))
            .andExpect(jsonPath("$.idPathologies").value(DEFAULT_ID_PATHOLOGIES))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPathologies() throws Exception {
        // Get the pathologies
        restPathologiesMockMvc.perform(get("/api/pathologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePathologies() throws Exception {
        // Initialize the database
        pathologiesRepository.saveAndFlush(pathologies);

        int databaseSizeBeforeUpdate = pathologiesRepository.findAll().size();

        // Update the pathologies
        Pathologies updatedPathologies = pathologiesRepository.findById(pathologies.getId()).get();
        // Disconnect from session so that the updates on updatedPathologies are not directly saved in db
        em.detach(updatedPathologies);
        updatedPathologies
            .idPathologies(UPDATED_ID_PATHOLOGIES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restPathologiesMockMvc.perform(put("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPathologies)))
            .andExpect(status().isOk());

        // Validate the Pathologies in the database
        List<Pathologies> pathologiesList = pathologiesRepository.findAll();
        assertThat(pathologiesList).hasSize(databaseSizeBeforeUpdate);
        Pathologies testPathologies = pathologiesList.get(pathologiesList.size() - 1);
        assertThat(testPathologies.getIdPathologies()).isEqualTo(UPDATED_ID_PATHOLOGIES);
        assertThat(testPathologies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPathologies.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPathologies() throws Exception {
        int databaseSizeBeforeUpdate = pathologiesRepository.findAll().size();

        // Create the Pathologies

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathologiesMockMvc.perform(put("/api/pathologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pathologies)))
            .andExpect(status().isBadRequest());

        // Validate the Pathologies in the database
        List<Pathologies> pathologiesList = pathologiesRepository.findAll();
        assertThat(pathologiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePathologies() throws Exception {
        // Initialize the database
        pathologiesRepository.saveAndFlush(pathologies);

        int databaseSizeBeforeDelete = pathologiesRepository.findAll().size();

        // Delete the pathologies
        restPathologiesMockMvc.perform(delete("/api/pathologies/{id}", pathologies.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pathologies> pathologiesList = pathologiesRepository.findAll();
        assertThat(pathologiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pathologies.class);
        Pathologies pathologies1 = new Pathologies();
        pathologies1.setId(1L);
        Pathologies pathologies2 = new Pathologies();
        pathologies2.setId(pathologies1.getId());
        assertThat(pathologies1).isEqualTo(pathologies2);
        pathologies2.setId(2L);
        assertThat(pathologies1).isNotEqualTo(pathologies2);
        pathologies1.setId(null);
        assertThat(pathologies1).isNotEqualTo(pathologies2);
    }
}
