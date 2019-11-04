package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.ProviderHistory;
import com.cenfotec.elderlysmart.repository.ProviderHistoryRepository;
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
 * Integration tests for the {@link ProviderHistoryResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class ProviderHistoryResourceIT {

    private static final Integer DEFAULT_ID_PROVIDER_HISTORY = 1;
    private static final Integer UPDATED_ID_PROVIDER_HISTORY = 2;

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
    private ProviderHistoryRepository providerHistoryRepository;

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

    private MockMvc restProviderHistoryMockMvc;

    private ProviderHistory providerHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProviderHistoryResource providerHistoryResource = new ProviderHistoryResource(providerHistoryRepository);
        this.restProviderHistoryMockMvc = MockMvcBuilders.standaloneSetup(providerHistoryResource)
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
    public static ProviderHistory createEntity(EntityManager em) {
        ProviderHistory providerHistory = new ProviderHistory()
            .idProviderHistory(DEFAULT_ID_PROVIDER_HISTORY)
            .code(DEFAULT_CODE)
            .client(DEFAULT_CLIENT)
            .phone(DEFAULT_PHONE)
            .purchaseMade(DEFAULT_PURCHASE_MADE)
            .date(DEFAULT_DATE);
        return providerHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProviderHistory createUpdatedEntity(EntityManager em) {
        ProviderHistory providerHistory = new ProviderHistory()
            .idProviderHistory(UPDATED_ID_PROVIDER_HISTORY)
            .code(UPDATED_CODE)
            .client(UPDATED_CLIENT)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE);
        return providerHistory;
    }

    @BeforeEach
    public void initTest() {
        providerHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderHistory() throws Exception {
        int databaseSizeBeforeCreate = providerHistoryRepository.findAll().size();

        // Create the ProviderHistory
        restProviderHistoryMockMvc.perform(post("/api/provider-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerHistory)))
            .andExpect(status().isCreated());

        // Validate the ProviderHistory in the database
        List<ProviderHistory> providerHistoryList = providerHistoryRepository.findAll();
        assertThat(providerHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderHistory testProviderHistory = providerHistoryList.get(providerHistoryList.size() - 1);
        assertThat(testProviderHistory.getIdProviderHistory()).isEqualTo(DEFAULT_ID_PROVIDER_HISTORY);
        assertThat(testProviderHistory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProviderHistory.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testProviderHistory.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProviderHistory.getPurchaseMade()).isEqualTo(DEFAULT_PURCHASE_MADE);
        assertThat(testProviderHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createProviderHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerHistoryRepository.findAll().size();

        // Create the ProviderHistory with an existing ID
        providerHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderHistoryMockMvc.perform(post("/api/provider-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderHistory in the database
        List<ProviderHistory> providerHistoryList = providerHistoryRepository.findAll();
        assertThat(providerHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProviderHistories() throws Exception {
        // Initialize the database
        providerHistoryRepository.saveAndFlush(providerHistory);

        // Get all the providerHistoryList
        restProviderHistoryMockMvc.perform(get("/api/provider-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProviderHistory").value(hasItem(DEFAULT_ID_PROVIDER_HISTORY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].purchaseMade").value(hasItem(DEFAULT_PURCHASE_MADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProviderHistory() throws Exception {
        // Initialize the database
        providerHistoryRepository.saveAndFlush(providerHistory);

        // Get the providerHistory
        restProviderHistoryMockMvc.perform(get("/api/provider-histories/{id}", providerHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerHistory.getId().intValue()))
            .andExpect(jsonPath("$.idProviderHistory").value(DEFAULT_ID_PROVIDER_HISTORY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.purchaseMade").value(DEFAULT_PURCHASE_MADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderHistory() throws Exception {
        // Get the providerHistory
        restProviderHistoryMockMvc.perform(get("/api/provider-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderHistory() throws Exception {
        // Initialize the database
        providerHistoryRepository.saveAndFlush(providerHistory);

        int databaseSizeBeforeUpdate = providerHistoryRepository.findAll().size();

        // Update the providerHistory
        ProviderHistory updatedProviderHistory = providerHistoryRepository.findById(providerHistory.getId()).get();
        // Disconnect from session so that the updates on updatedProviderHistory are not directly saved in db
        em.detach(updatedProviderHistory);
        updatedProviderHistory
            .idProviderHistory(UPDATED_ID_PROVIDER_HISTORY)
            .code(UPDATED_CODE)
            .client(UPDATED_CLIENT)
            .phone(UPDATED_PHONE)
            .purchaseMade(UPDATED_PURCHASE_MADE)
            .date(UPDATED_DATE);

        restProviderHistoryMockMvc.perform(put("/api/provider-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderHistory)))
            .andExpect(status().isOk());

        // Validate the ProviderHistory in the database
        List<ProviderHistory> providerHistoryList = providerHistoryRepository.findAll();
        assertThat(providerHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProviderHistory testProviderHistory = providerHistoryList.get(providerHistoryList.size() - 1);
        assertThat(testProviderHistory.getIdProviderHistory()).isEqualTo(UPDATED_ID_PROVIDER_HISTORY);
        assertThat(testProviderHistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProviderHistory.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testProviderHistory.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProviderHistory.getPurchaseMade()).isEqualTo(UPDATED_PURCHASE_MADE);
        assertThat(testProviderHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderHistory() throws Exception {
        int databaseSizeBeforeUpdate = providerHistoryRepository.findAll().size();

        // Create the ProviderHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviderHistoryMockMvc.perform(put("/api/provider-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderHistory in the database
        List<ProviderHistory> providerHistoryList = providerHistoryRepository.findAll();
        assertThat(providerHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProviderHistory() throws Exception {
        // Initialize the database
        providerHistoryRepository.saveAndFlush(providerHistory);

        int databaseSizeBeforeDelete = providerHistoryRepository.findAll().size();

        // Delete the providerHistory
        restProviderHistoryMockMvc.perform(delete("/api/provider-histories/{id}", providerHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProviderHistory> providerHistoryList = providerHistoryRepository.findAll();
        assertThat(providerHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderHistory.class);
        ProviderHistory providerHistory1 = new ProviderHistory();
        providerHistory1.setId(1L);
        ProviderHistory providerHistory2 = new ProviderHistory();
        providerHistory2.setId(providerHistory1.getId());
        assertThat(providerHistory1).isEqualTo(providerHistory2);
        providerHistory2.setId(2L);
        assertThat(providerHistory1).isNotEqualTo(providerHistory2);
        providerHistory1.setId(null);
        assertThat(providerHistory1).isNotEqualTo(providerHistory2);
    }
}
