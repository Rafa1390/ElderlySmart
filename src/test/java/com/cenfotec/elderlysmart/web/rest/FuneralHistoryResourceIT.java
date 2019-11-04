package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.FuneralHistory;
import com.cenfotec.elderlysmart.repository.FuneralHistoryRepository;
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
 * Integration tests for the {@link FuneralHistoryResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class FuneralHistoryResourceIT {

    private static final Integer DEFAULT_ID_FUNERAL_HISTORY = 1;
    private static final Integer UPDATED_ID_FUNERAL_HISTORY = 2;

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_MADE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_MADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private FuneralHistoryRepository funeralHistoryRepository;

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

    private MockMvc restFuneralHistoryMockMvc;

    private FuneralHistory funeralHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuneralHistoryResource funeralHistoryResource = new FuneralHistoryResource(funeralHistoryRepository);
        this.restFuneralHistoryMockMvc = MockMvcBuilders.standaloneSetup(funeralHistoryResource)
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
    public static FuneralHistory createEntity(EntityManager em) {
        FuneralHistory funeralHistory = new FuneralHistory()
            .idFuneralHistory(DEFAULT_ID_FUNERAL_HISTORY)
            .customer(DEFAULT_CUSTOMER)
            .phone(DEFAULT_PHONE)
            .purchaseMade(DEFAULT_PURCHASE_MADE)
            .date(DEFAULT_DATE)
            .state(DEFAULT_STATE);
        return funeralHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuneralHistory createUpdatedEntity(EntityManager em) {
        FuneralHistory funeralHistory = new FuneralHistory()
            .idFuneralHistory(UPDATED_ID_FUNERAL_HISTORY)
            .customer(UPDATED_CUSTOMER)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE)
            .state(UPDATED_STATE);
        return funeralHistory;
    }

    @BeforeEach
    public void initTest() {
        funeralHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuneralHistory() throws Exception {
        int databaseSizeBeforeCreate = funeralHistoryRepository.findAll().size();

        // Create the FuneralHistory
        restFuneralHistoryMockMvc.perform(post("/api/funeral-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralHistory)))
            .andExpect(status().isCreated());

        // Validate the FuneralHistory in the database
        List<FuneralHistory> funeralHistoryList = funeralHistoryRepository.findAll();
        assertThat(funeralHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        FuneralHistory testFuneralHistory = funeralHistoryList.get(funeralHistoryList.size() - 1);
        assertThat(testFuneralHistory.getIdFuneralHistory()).isEqualTo(DEFAULT_ID_FUNERAL_HISTORY);
        assertThat(testFuneralHistory.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testFuneralHistory.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFuneralHistory.getPurchaseMade()).isEqualTo(DEFAULT_PURCHASE_MADE);
        assertThat(testFuneralHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFuneralHistory.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createFuneralHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funeralHistoryRepository.findAll().size();

        // Create the FuneralHistory with an existing ID
        funeralHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuneralHistoryMockMvc.perform(post("/api/funeral-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FuneralHistory in the database
        List<FuneralHistory> funeralHistoryList = funeralHistoryRepository.findAll();
        assertThat(funeralHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFuneralHistories() throws Exception {
        // Initialize the database
        funeralHistoryRepository.saveAndFlush(funeralHistory);

        // Get all the funeralHistoryList
        restFuneralHistoryMockMvc.perform(get("/api/funeral-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funeralHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFuneralHistory").value(hasItem(DEFAULT_ID_FUNERAL_HISTORY)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].purchaseMade").value(hasItem(DEFAULT_PURCHASE_MADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getFuneralHistory() throws Exception {
        // Initialize the database
        funeralHistoryRepository.saveAndFlush(funeralHistory);

        // Get the funeralHistory
        restFuneralHistoryMockMvc.perform(get("/api/funeral-histories/{id}", funeralHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(funeralHistory.getId().intValue()))
            .andExpect(jsonPath("$.idFuneralHistory").value(DEFAULT_ID_FUNERAL_HISTORY))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.purchaseMade").value(DEFAULT_PURCHASE_MADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingFuneralHistory() throws Exception {
        // Get the funeralHistory
        restFuneralHistoryMockMvc.perform(get("/api/funeral-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuneralHistory() throws Exception {
        // Initialize the database
        funeralHistoryRepository.saveAndFlush(funeralHistory);

        int databaseSizeBeforeUpdate = funeralHistoryRepository.findAll().size();

        // Update the funeralHistory
        FuneralHistory updatedFuneralHistory = funeralHistoryRepository.findById(funeralHistory.getId()).get();
        // Disconnect from session so that the updates on updatedFuneralHistory are not directly saved in db
        em.detach(updatedFuneralHistory);
        updatedFuneralHistory
            .idFuneralHistory(UPDATED_ID_FUNERAL_HISTORY)
            .customer(UPDATED_CUSTOMER)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE)
            .state(UPDATED_STATE);

        restFuneralHistoryMockMvc.perform(put("/api/funeral-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFuneralHistory)))
            .andExpect(status().isOk());

        // Validate the FuneralHistory in the database
        List<FuneralHistory> funeralHistoryList = funeralHistoryRepository.findAll();
        assertThat(funeralHistoryList).hasSize(databaseSizeBeforeUpdate);
        FuneralHistory testFuneralHistory = funeralHistoryList.get(funeralHistoryList.size() - 1);
        assertThat(testFuneralHistory.getIdFuneralHistory()).isEqualTo(UPDATED_ID_FUNERAL_HISTORY);
        assertThat(testFuneralHistory.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testFuneralHistory.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFuneralHistory.getPurchaseMade()).isEqualTo(UPDATED_PURCHASE_MADE);
        assertThat(testFuneralHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFuneralHistory.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFuneralHistory() throws Exception {
        int databaseSizeBeforeUpdate = funeralHistoryRepository.findAll().size();

        // Create the FuneralHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuneralHistoryMockMvc.perform(put("/api/funeral-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(funeralHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FuneralHistory in the database
        List<FuneralHistory> funeralHistoryList = funeralHistoryRepository.findAll();
        assertThat(funeralHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuneralHistory() throws Exception {
        // Initialize the database
        funeralHistoryRepository.saveAndFlush(funeralHistory);

        int databaseSizeBeforeDelete = funeralHistoryRepository.findAll().size();

        // Delete the funeralHistory
        restFuneralHistoryMockMvc.perform(delete("/api/funeral-histories/{id}", funeralHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuneralHistory> funeralHistoryList = funeralHistoryRepository.findAll();
        assertThat(funeralHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuneralHistory.class);
        FuneralHistory funeralHistory1 = new FuneralHistory();
        funeralHistory1.setId(1L);
        FuneralHistory funeralHistory2 = new FuneralHistory();
        funeralHistory2.setId(funeralHistory1.getId());
        assertThat(funeralHistory1).isEqualTo(funeralHistory2);
        funeralHistory2.setId(2L);
        assertThat(funeralHistory1).isNotEqualTo(funeralHistory2);
        funeralHistory1.setId(null);
        assertThat(funeralHistory1).isNotEqualTo(funeralHistory2);
    }
}
