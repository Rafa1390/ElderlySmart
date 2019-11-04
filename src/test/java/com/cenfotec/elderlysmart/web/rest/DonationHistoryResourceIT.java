package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.DonationHistory;
import com.cenfotec.elderlysmart.repository.DonationHistoryRepository;
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
 * Integration tests for the {@link DonationHistoryResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class DonationHistoryResourceIT {

    private static final Integer DEFAULT_ID_DONATION_HISTORY = 1;
    private static final Integer UPDATED_ID_DONATION_HISTORY = 2;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DONATION_MADE = 1;
    private static final Integer UPDATED_DONATION_MADE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

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

    private MockMvc restDonationHistoryMockMvc;

    private DonationHistory donationHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonationHistoryResource donationHistoryResource = new DonationHistoryResource(donationHistoryRepository);
        this.restDonationHistoryMockMvc = MockMvcBuilders.standaloneSetup(donationHistoryResource)
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
    public static DonationHistory createEntity(EntityManager em) {
        DonationHistory donationHistory = new DonationHistory()
            .idDonationHistory(DEFAULT_ID_DONATION_HISTORY)
            .phone(DEFAULT_PHONE)
            .donationMade(DEFAULT_DONATION_MADE)
            .date(DEFAULT_DATE);
        return donationHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationHistory createUpdatedEntity(EntityManager em) {
        DonationHistory donationHistory = new DonationHistory()
            .idDonationHistory(UPDATED_ID_DONATION_HISTORY)
            .phone(UPDATED_PHONE)
            .donationMade(UPDATED_DONATION_MADE)
            .date(UPDATED_DATE);
        return donationHistory;
    }

    @BeforeEach
    public void initTest() {
        donationHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonationHistory() throws Exception {
        int databaseSizeBeforeCreate = donationHistoryRepository.findAll().size();

        // Create the DonationHistory
        restDonationHistoryMockMvc.perform(post("/api/donation-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donationHistory)))
            .andExpect(status().isCreated());

        // Validate the DonationHistory in the database
        List<DonationHistory> donationHistoryList = donationHistoryRepository.findAll();
        assertThat(donationHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DonationHistory testDonationHistory = donationHistoryList.get(donationHistoryList.size() - 1);
        assertThat(testDonationHistory.getIdDonationHistory()).isEqualTo(DEFAULT_ID_DONATION_HISTORY);
        assertThat(testDonationHistory.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDonationHistory.getDonationMade()).isEqualTo(DEFAULT_DONATION_MADE);
        assertThat(testDonationHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDonationHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donationHistoryRepository.findAll().size();

        // Create the DonationHistory with an existing ID
        donationHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationHistoryMockMvc.perform(post("/api/donation-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donationHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DonationHistory in the database
        List<DonationHistory> donationHistoryList = donationHistoryRepository.findAll();
        assertThat(donationHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDonationHistories() throws Exception {
        // Initialize the database
        donationHistoryRepository.saveAndFlush(donationHistory);

        // Get all the donationHistoryList
        restDonationHistoryMockMvc.perform(get("/api/donation-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDonationHistory").value(hasItem(DEFAULT_ID_DONATION_HISTORY)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].donationMade").value(hasItem(DEFAULT_DONATION_MADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDonationHistory() throws Exception {
        // Initialize the database
        donationHistoryRepository.saveAndFlush(donationHistory);

        // Get the donationHistory
        restDonationHistoryMockMvc.perform(get("/api/donation-histories/{id}", donationHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donationHistory.getId().intValue()))
            .andExpect(jsonPath("$.idDonationHistory").value(DEFAULT_ID_DONATION_HISTORY))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.donationMade").value(DEFAULT_DONATION_MADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonationHistory() throws Exception {
        // Get the donationHistory
        restDonationHistoryMockMvc.perform(get("/api/donation-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonationHistory() throws Exception {
        // Initialize the database
        donationHistoryRepository.saveAndFlush(donationHistory);

        int databaseSizeBeforeUpdate = donationHistoryRepository.findAll().size();

        // Update the donationHistory
        DonationHistory updatedDonationHistory = donationHistoryRepository.findById(donationHistory.getId()).get();
        // Disconnect from session so that the updates on updatedDonationHistory are not directly saved in db
        em.detach(updatedDonationHistory);
        updatedDonationHistory
            .idDonationHistory(UPDATED_ID_DONATION_HISTORY)
            .phone(UPDATED_PHONE)
            .donationMade(UPDATED_DONATION_MADE)
            .date(UPDATED_DATE);

        restDonationHistoryMockMvc.perform(put("/api/donation-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonationHistory)))
            .andExpect(status().isOk());

        // Validate the DonationHistory in the database
        List<DonationHistory> donationHistoryList = donationHistoryRepository.findAll();
        assertThat(donationHistoryList).hasSize(databaseSizeBeforeUpdate);
        DonationHistory testDonationHistory = donationHistoryList.get(donationHistoryList.size() - 1);
        assertThat(testDonationHistory.getIdDonationHistory()).isEqualTo(UPDATED_ID_DONATION_HISTORY);
        assertThat(testDonationHistory.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDonationHistory.getDonationMade()).isEqualTo(UPDATED_DONATION_MADE);
        assertThat(testDonationHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDonationHistory() throws Exception {
        int databaseSizeBeforeUpdate = donationHistoryRepository.findAll().size();

        // Create the DonationHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationHistoryMockMvc.perform(put("/api/donation-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donationHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DonationHistory in the database
        List<DonationHistory> donationHistoryList = donationHistoryRepository.findAll();
        assertThat(donationHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonationHistory() throws Exception {
        // Initialize the database
        donationHistoryRepository.saveAndFlush(donationHistory);

        int databaseSizeBeforeDelete = donationHistoryRepository.findAll().size();

        // Delete the donationHistory
        restDonationHistoryMockMvc.perform(delete("/api/donation-histories/{id}", donationHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationHistory> donationHistoryList = donationHistoryRepository.findAll();
        assertThat(donationHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationHistory.class);
        DonationHistory donationHistory1 = new DonationHistory();
        donationHistory1.setId(1L);
        DonationHistory donationHistory2 = new DonationHistory();
        donationHistory2.setId(donationHistory1.getId());
        assertThat(donationHistory1).isEqualTo(donationHistory2);
        donationHistory2.setId(2L);
        assertThat(donationHistory1).isNotEqualTo(donationHistory2);
        donationHistory1.setId(null);
        assertThat(donationHistory1).isNotEqualTo(donationHistory2);
    }
}
