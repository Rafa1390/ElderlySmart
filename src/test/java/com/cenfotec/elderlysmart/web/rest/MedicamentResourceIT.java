package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Medicament;
import com.cenfotec.elderlysmart.repository.MedicamentRepository;
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
 * Integration tests for the {@link MedicamentResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class MedicamentResourceIT {

    private static final Integer DEFAULT_ID_MEDICAMENT = 1;
    private static final Integer UPDATED_ID_MEDICAMENT = 2;

    private static final String DEFAULT_PRESENTATION = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_EXPIRY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPIRY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_CUANTITY = "BBBBBBBBBB";

    @Autowired
    private MedicamentRepository medicamentRepository;

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

    private MockMvc restMedicamentMockMvc;

    private Medicament medicament;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicamentResource medicamentResource = new MedicamentResource(medicamentRepository);
        this.restMedicamentMockMvc = MockMvcBuilders.standaloneSetup(medicamentResource)
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
    public static Medicament createEntity(EntityManager em) {
        Medicament medicament = new Medicament()
            .idMedicament(DEFAULT_ID_MEDICAMENT)
            .presentation(DEFAULT_PRESENTATION)
            .dateExpiry(DEFAULT_DATE_EXPIRY)
            .cuantity(DEFAULT_CUANTITY);
        return medicament;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicament createUpdatedEntity(EntityManager em) {
        Medicament medicament = new Medicament()
            .idMedicament(UPDATED_ID_MEDICAMENT)
            .presentation(UPDATED_PRESENTATION)
            .dateExpiry(UPDATED_DATE_EXPIRY)
            .cuantity(UPDATED_CUANTITY);
        return medicament;
    }

    @BeforeEach
    public void initTest() {
        medicament = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicament() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament
        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isCreated());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate + 1);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getIdMedicament()).isEqualTo(DEFAULT_ID_MEDICAMENT);
        assertThat(testMedicament.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testMedicament.getDateExpiry()).isEqualTo(DEFAULT_DATE_EXPIRY);
        assertThat(testMedicament.getCuantity()).isEqualTo(DEFAULT_CUANTITY);
    }

    @Test
    @Transactional
    public void createMedicamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament with an existing ID
        medicament.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isBadRequest());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicaments() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        // Get all the medicamentList
        restMedicamentMockMvc.perform(get("/api/medicaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicament.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMedicament").value(hasItem(DEFAULT_ID_MEDICAMENT)))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION)))
            .andExpect(jsonPath("$.[*].dateExpiry").value(hasItem(DEFAULT_DATE_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].cuantity").value(hasItem(DEFAULT_CUANTITY)));
    }
    
    @Test
    @Transactional
    public void getMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", medicament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicament.getId().intValue()))
            .andExpect(jsonPath("$.idMedicament").value(DEFAULT_ID_MEDICAMENT))
            .andExpect(jsonPath("$.presentation").value(DEFAULT_PRESENTATION))
            .andExpect(jsonPath("$.dateExpiry").value(DEFAULT_DATE_EXPIRY.toString()))
            .andExpect(jsonPath("$.cuantity").value(DEFAULT_CUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingMedicament() throws Exception {
        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Update the medicament
        Medicament updatedMedicament = medicamentRepository.findById(medicament.getId()).get();
        // Disconnect from session so that the updates on updatedMedicament are not directly saved in db
        em.detach(updatedMedicament);
        updatedMedicament
            .idMedicament(UPDATED_ID_MEDICAMENT)
            .presentation(UPDATED_PRESENTATION)
            .dateExpiry(UPDATED_DATE_EXPIRY)
            .cuantity(UPDATED_CUANTITY);

        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicament)))
            .andExpect(status().isOk());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getIdMedicament()).isEqualTo(UPDATED_ID_MEDICAMENT);
        assertThat(testMedicament.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testMedicament.getDateExpiry()).isEqualTo(UPDATED_DATE_EXPIRY);
        assertThat(testMedicament.getCuantity()).isEqualTo(UPDATED_CUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicament() throws Exception {
        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Create the Medicament

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isBadRequest());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        int databaseSizeBeforeDelete = medicamentRepository.findAll().size();

        // Delete the medicament
        restMedicamentMockMvc.perform(delete("/api/medicaments/{id}", medicament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicament.class);
        Medicament medicament1 = new Medicament();
        medicament1.setId(1L);
        Medicament medicament2 = new Medicament();
        medicament2.setId(medicament1.getId());
        assertThat(medicament1).isEqualTo(medicament2);
        medicament2.setId(2L);
        assertThat(medicament1).isNotEqualTo(medicament2);
        medicament1.setId(null);
        assertThat(medicament1).isNotEqualTo(medicament2);
    }
}
