package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.FuneralPackages;
import com.cenfotec.elderlysmart.repository.FuneralPackagesRepository;
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
 * Integration tests for the {@link FuneralPackagesResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class FuneralPackagesResourceIT {

    private static final Integer DEFAULT_ID_FUNERAL_PACKAGES = 1;
    private static final Integer UPDATED_ID_FUNERAL_PACKAGES = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private FuneralPackagesRepository funeralPackagesRepository;

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

    private MockMvc restFuneralPackagesMockMvc;

    private FuneralPackages funeralPackages;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuneralPackagesResource funeralPackagesResource = new FuneralPackagesResource(funeralPackagesRepository);
        this.restFuneralPackagesMockMvc = MockMvcBuilders.standaloneSetup(funeralPackagesResource)
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
    public static FuneralPackages createEntity(EntityManager em) {
        FuneralPackages funeralPackages = new FuneralPackages()
            .idFuneralPackages(DEFAULT_ID_FUNERAL_PACKAGES)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .state(DEFAULT_STATE);
        return funeralPackages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuneralPackages createUpdatedEntity(EntityManager em) {
        FuneralPackages funeralPackages = new FuneralPackages()
            .idFuneralPackages(UPDATED_ID_FUNERAL_PACKAGES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .state(UPDATED_STATE);
        return funeralPackages;
    }

    @BeforeEach
    public void initTest() {
        funeralPackages = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuneralPackages() throws Exception {
        int databaseSizeBeforeCreate = funeralPackagesRepository.findAll().size();

        // Create the FuneralPackages
        restFuneralPackagesMockMvc.perform(post("/api/funeral-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralPackages)))
            .andExpect(status().isCreated());

        // Validate the FuneralPackages in the database
        List<FuneralPackages> funeralPackagesList = funeralPackagesRepository.findAll();
        assertThat(funeralPackagesList).hasSize(databaseSizeBeforeCreate + 1);
        FuneralPackages testFuneralPackages = funeralPackagesList.get(funeralPackagesList.size() - 1);
        assertThat(testFuneralPackages.getIdFuneralPackages()).isEqualTo(DEFAULT_ID_FUNERAL_PACKAGES);
        assertThat(testFuneralPackages.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFuneralPackages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFuneralPackages.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFuneralPackages.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createFuneralPackagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funeralPackagesRepository.findAll().size();

        // Create the FuneralPackages with an existing ID
        funeralPackages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuneralPackagesMockMvc.perform(post("/api/funeral-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralPackages)))
            .andExpect(status().isBadRequest());

        // Validate the FuneralPackages in the database
        List<FuneralPackages> funeralPackagesList = funeralPackagesRepository.findAll();
        assertThat(funeralPackagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFuneralPackages() throws Exception {
        // Initialize the database
        funeralPackagesRepository.saveAndFlush(funeralPackages);

        // Get all the funeralPackagesList
        restFuneralPackagesMockMvc.perform(get("/api/funeral-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funeralPackages.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFuneralPackages").value(hasItem(DEFAULT_ID_FUNERAL_PACKAGES)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getFuneralPackages() throws Exception {
        // Initialize the database
        funeralPackagesRepository.saveAndFlush(funeralPackages);

        // Get the funeralPackages
        restFuneralPackagesMockMvc.perform(get("/api/funeral-packages/{id}", funeralPackages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funeralPackages.getId().intValue()))
            .andExpect(jsonPath("$.idFuneralPackages").value(DEFAULT_ID_FUNERAL_PACKAGES))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingFuneralPackages() throws Exception {
        // Get the funeralPackages
        restFuneralPackagesMockMvc.perform(get("/api/funeral-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuneralPackages() throws Exception {
        // Initialize the database
        funeralPackagesRepository.saveAndFlush(funeralPackages);

        int databaseSizeBeforeUpdate = funeralPackagesRepository.findAll().size();

        // Update the funeralPackages
        FuneralPackages updatedFuneralPackages = funeralPackagesRepository.findById(funeralPackages.getId()).get();
        // Disconnect from session so that the updates on updatedFuneralPackages are not directly saved in db
        em.detach(updatedFuneralPackages);
        updatedFuneralPackages
            .idFuneralPackages(UPDATED_ID_FUNERAL_PACKAGES)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .state(UPDATED_STATE);

        restFuneralPackagesMockMvc.perform(put("/api/funeral-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuneralPackages)))
            .andExpect(status().isOk());

        // Validate the FuneralPackages in the database
        List<FuneralPackages> funeralPackagesList = funeralPackagesRepository.findAll();
        assertThat(funeralPackagesList).hasSize(databaseSizeBeforeUpdate);
        FuneralPackages testFuneralPackages = funeralPackagesList.get(funeralPackagesList.size() - 1);
        assertThat(testFuneralPackages.getIdFuneralPackages()).isEqualTo(UPDATED_ID_FUNERAL_PACKAGES);
        assertThat(testFuneralPackages.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFuneralPackages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFuneralPackages.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFuneralPackages.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFuneralPackages() throws Exception {
        int databaseSizeBeforeUpdate = funeralPackagesRepository.findAll().size();

        // Create the FuneralPackages

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuneralPackagesMockMvc.perform(put("/api/funeral-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralPackages)))
            .andExpect(status().isBadRequest());

        // Validate the FuneralPackages in the database
        List<FuneralPackages> funeralPackagesList = funeralPackagesRepository.findAll();
        assertThat(funeralPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuneralPackages() throws Exception {
        // Initialize the database
        funeralPackagesRepository.saveAndFlush(funeralPackages);

        int databaseSizeBeforeDelete = funeralPackagesRepository.findAll().size();

        // Delete the funeralPackages
        restFuneralPackagesMockMvc.perform(delete("/api/funeral-packages/{id}", funeralPackages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuneralPackages> funeralPackagesList = funeralPackagesRepository.findAll();
        assertThat(funeralPackagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuneralPackages.class);
        FuneralPackages funeralPackages1 = new FuneralPackages();
        funeralPackages1.setId(1L);
        FuneralPackages funeralPackages2 = new FuneralPackages();
        funeralPackages2.setId(funeralPackages1.getId());
        assertThat(funeralPackages1).isEqualTo(funeralPackages2);
        funeralPackages2.setId(2L);
        assertThat(funeralPackages1).isNotEqualTo(funeralPackages2);
        funeralPackages1.setId(null);
        assertThat(funeralPackages1).isNotEqualTo(funeralPackages2);
    }
}
