package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.PharmacyHistory;
import com.cenfotec.elderlysmart.repository.PharmacyHistoryRepository;
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
 * Integration tests for the {@link PharmacyHistoryResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class PharmacyHistoryResourceIT {

    private static final Integer DEFAULT_ID_PHARMACY_HISTORY = 1;
    private static final Integer UPDATED_ID_PHARMACY_HISTORY = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_MADE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_MADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PharmacyHistoryRepository pharmacyHistoryRepository;

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

    private MockMvc restPharmacyHistoryMockMvc;

    private PharmacyHistory pharmacyHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmacyHistoryResource pharmacyHistoryResource = new PharmacyHistoryResource(pharmacyHistoryRepository);
        this.restPharmacyHistoryMockMvc = MockMvcBuilders.standaloneSetup(pharmacyHistoryResource)
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
    public static PharmacyHistory createEntity(EntityManager em) {
        PharmacyHistory pharmacyHistory = new PharmacyHistory()
            .idPharmacyHistory(DEFAULT_ID_PHARMACY_HISTORY)
            .code(DEFAULT_CODE)
            .client(DEFAULT_CLIENT)
            .phone(DEFAULT_PHONE)
            .purchaseMade(DEFAULT_PURCHASE_MADE)
            .date(DEFAULT_DATE);
        return pharmacyHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PharmacyHistory createUpdatedEntity(EntityManager em) {
        PharmacyHistory pharmacyHistory = new PharmacyHistory()
            .idPharmacyHistory(UPDATED_ID_PHARMACY_HISTORY)
            .code(UPDATED_CODE)
            .client(UPDATED_CLIENT)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE);
        return pharmacyHistory;
    }

    @BeforeEach
    public void initTest() {
        pharmacyHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacyHistory() throws Exception {
        int databaseSizeBeforeCreate = pharmacyHistoryRepository.findAll().size();

        // Create the PharmacyHistory
        restPharmacyHistoryMockMvc.perform(post("/api/pharmacy-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyHistory)))
            .andExpect(status().isCreated());

        // Validate the PharmacyHistory in the database
        List<PharmacyHistory> pharmacyHistoryList = pharmacyHistoryRepository.findAll();
        assertThat(pharmacyHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PharmacyHistory testPharmacyHistory = pharmacyHistoryList.get(pharmacyHistoryList.size() - 1);
        assertThat(testPharmacyHistory.getIdPharmacyHistory()).isEqualTo(DEFAULT_ID_PHARMACY_HISTORY);
        assertThat(testPharmacyHistory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPharmacyHistory.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testPharmacyHistory.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPharmacyHistory.getPurchaseMade()).isEqualTo(DEFAULT_PURCHASE_MADE);
        assertThat(testPharmacyHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPharmacyHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacyHistoryRepository.findAll().size();

        // Create the PharmacyHistory with an existing ID
        pharmacyHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacyHistoryMockMvc.perform(post("/api/pharmacy-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PharmacyHistory in the database
        List<PharmacyHistory> pharmacyHistoryList = pharmacyHistoryRepository.findAll();
        assertThat(pharmacyHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPharmacyHistories() throws Exception {
        // Initialize the database
        pharmacyHistoryRepository.saveAndFlush(pharmacyHistory);

        // Get all the pharmacyHistoryList
        restPharmacyHistoryMockMvc.perform(get("/api/pharmacy-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacyHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPharmacyHistory").value(hasItem(DEFAULT_ID_PHARMACY_HISTORY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].purchaseMade").value(hasItem(DEFAULT_PURCHASE_MADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPharmacyHistory() throws Exception {
        // Initialize the database
        pharmacyHistoryRepository.saveAndFlush(pharmacyHistory);

        // Get the pharmacyHistory
        restPharmacyHistoryMockMvc.perform(get("/api/pharmacy-histories/{id}", pharmacyHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacyHistory.getId().intValue()))
            .andExpect(jsonPath("$.idPharmacyHistory").value(DEFAULT_ID_PHARMACY_HISTORY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.purchaseMade").value(DEFAULT_PURCHASE_MADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacyHistory() throws Exception {
        // Get the pharmacyHistory
        restPharmacyHistoryMockMvc.perform(get("/api/pharmacy-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacyHistory() throws Exception {
        // Initialize the database
        pharmacyHistoryRepository.saveAndFlush(pharmacyHistory);

        int databaseSizeBeforeUpdate = pharmacyHistoryRepository.findAll().size();

        // Update the pharmacyHistory
        PharmacyHistory updatedPharmacyHistory = pharmacyHistoryRepository.findById(pharmacyHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPharmacyHistory are not directly saved in db
        em.detach(updatedPharmacyHistory);
        updatedPharmacyHistory
            .idPharmacyHistory(UPDATED_ID_PHARMACY_HISTORY)
            .code(UPDATED_CODE)
            .client(UPDATED_CLIENT)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE);

        restPharmacyHistoryMockMvc.perform(put("/api/pharmacy-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacyHistory)))
            .andExpect(status().isOk());

        // Validate the PharmacyHistory in the database
        List<PharmacyHistory> pharmacyHistoryList = pharmacyHistoryRepository.findAll();
        assertThat(pharmacyHistoryList).hasSize(databaseSizeBeforeUpdate);
        PharmacyHistory testPharmacyHistory = pharmacyHistoryList.get(pharmacyHistoryList.size() - 1);
        assertThat(testPharmacyHistory.getIdPharmacyHistory()).isEqualTo(UPDATED_ID_PHARMACY_HISTORY);
        assertThat(testPharmacyHistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPharmacyHistory.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testPharmacyHistory.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPharmacyHistory.getPurchaseMade()).isEqualTo(UPDATED_PURCHASE_MADE);
        assertThat(testPharmacyHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacyHistory() throws Exception {
        int databaseSizeBeforeUpdate = pharmacyHistoryRepository.findAll().size();

        // Create the PharmacyHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyHistoryMockMvc.perform(put("/api/pharmacy-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacyHistory)))
            .andExpect(status().isBadRequest());

        // Validate the PharmacyHistory in the database
        List<PharmacyHistory> pharmacyHistoryList = pharmacyHistoryRepository.findAll();
        assertThat(pharmacyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePharmacyHistory() throws Exception {
        // Initialize the database
        pharmacyHistoryRepository.saveAndFlush(pharmacyHistory);

        int databaseSizeBeforeDelete = pharmacyHistoryRepository.findAll().size();

        // Delete the pharmacyHistory
        restPharmacyHistoryMockMvc.perform(delete("/api/pharmacy-histories/{id}", pharmacyHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PharmacyHistory> pharmacyHistoryList = pharmacyHistoryRepository.findAll();
        assertThat(pharmacyHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PharmacyHistory.class);
        PharmacyHistory pharmacyHistory1 = new PharmacyHistory();
        pharmacyHistory1.setId(1L);
        PharmacyHistory pharmacyHistory2 = new PharmacyHistory();
        pharmacyHistory2.setId(pharmacyHistory1.getId());
        assertThat(pharmacyHistory1).isEqualTo(pharmacyHistory2);
        pharmacyHistory2.setId(2L);
        assertThat(pharmacyHistory1).isNotEqualTo(pharmacyHistory2);
        pharmacyHistory1.setId(null);
        assertThat(pharmacyHistory1).isNotEqualTo(pharmacyHistory2);
    }
}
