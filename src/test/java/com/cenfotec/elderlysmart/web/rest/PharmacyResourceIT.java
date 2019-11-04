package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Pharmacy;
import com.cenfotec.elderlysmart.repository.PharmacyRepository;
import com.cenfotec.elderlysmart.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.cenfotec.elderlysmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PharmacyResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class PharmacyResourceIT {

    private static final String DEFAULT_ID_PHARMACY = "AAAAAAAAAA";
    private static final String UPDATED_ID_PHARMACY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Mock
    private PharmacyRepository pharmacyRepositoryMock;

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

    private MockMvc restPharmacyMockMvc;

    private Pharmacy pharmacy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PharmacyResource pharmacyResource = new PharmacyResource(pharmacyRepository);
        this.restPharmacyMockMvc = MockMvcBuilders.standaloneSetup(pharmacyResource)
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
    public static Pharmacy createEntity(EntityManager em) {
        Pharmacy pharmacy = new Pharmacy()
            .idPharmacy(DEFAULT_ID_PHARMACY)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return pharmacy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pharmacy createUpdatedEntity(EntityManager em) {
        Pharmacy pharmacy = new Pharmacy()
            .idPharmacy(UPDATED_ID_PHARMACY)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        return pharmacy;
    }

    @BeforeEach
    public void initTest() {
        pharmacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPharmacy() throws Exception {
        int databaseSizeBeforeCreate = pharmacyRepository.findAll().size();

        // Create the Pharmacy
        restPharmacyMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isCreated());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeCreate + 1);
        Pharmacy testPharmacy = pharmacyList.get(pharmacyList.size() - 1);
        assertThat(testPharmacy.getIdPharmacy()).isEqualTo(DEFAULT_ID_PHARMACY);
        assertThat(testPharmacy.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPharmacy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPharmacy.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createPharmacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pharmacyRepository.findAll().size();

        // Create the Pharmacy with an existing ID
        pharmacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPharmacyMockMvc.perform(post("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPharmacies() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get all the pharmacyList
        restPharmacyMockMvc.perform(get("/api/pharmacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPharmacy").value(hasItem(DEFAULT_ID_PHARMACY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPharmaciesWithEagerRelationshipsIsEnabled() throws Exception {
        PharmacyResource pharmacyResource = new PharmacyResource(pharmacyRepositoryMock);
        when(pharmacyRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPharmacyMockMvc = MockMvcBuilders.standaloneSetup(pharmacyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPharmacyMockMvc.perform(get("/api/pharmacies?eagerload=true"))
        .andExpect(status().isOk());

        verify(pharmacyRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPharmaciesWithEagerRelationshipsIsNotEnabled() throws Exception {
        PharmacyResource pharmacyResource = new PharmacyResource(pharmacyRepositoryMock);
            when(pharmacyRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPharmacyMockMvc = MockMvcBuilders.standaloneSetup(pharmacyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPharmacyMockMvc.perform(get("/api/pharmacies?eagerload=true"))
        .andExpect(status().isOk());

            verify(pharmacyRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacies/{id}", pharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pharmacy.getId().intValue()))
            .andExpect(jsonPath("$.idPharmacy").value(DEFAULT_ID_PHARMACY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingPharmacy() throws Exception {
        // Get the pharmacy
        restPharmacyMockMvc.perform(get("/api/pharmacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        int databaseSizeBeforeUpdate = pharmacyRepository.findAll().size();

        // Update the pharmacy
        Pharmacy updatedPharmacy = pharmacyRepository.findById(pharmacy.getId()).get();
        // Disconnect from session so that the updates on updatedPharmacy are not directly saved in db
        em.detach(updatedPharmacy);
        updatedPharmacy
            .idPharmacy(UPDATED_ID_PHARMACY)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restPharmacyMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPharmacy)))
            .andExpect(status().isOk());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeUpdate);
        Pharmacy testPharmacy = pharmacyList.get(pharmacyList.size() - 1);
        assertThat(testPharmacy.getIdPharmacy()).isEqualTo(UPDATED_ID_PHARMACY);
        assertThat(testPharmacy.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPharmacy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPharmacy.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPharmacy() throws Exception {
        int databaseSizeBeforeUpdate = pharmacyRepository.findAll().size();

        // Create the Pharmacy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPharmacyMockMvc.perform(put("/api/pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the Pharmacy in the database
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePharmacy() throws Exception {
        // Initialize the database
        pharmacyRepository.saveAndFlush(pharmacy);

        int databaseSizeBeforeDelete = pharmacyRepository.findAll().size();

        // Delete the pharmacy
        restPharmacyMockMvc.perform(delete("/api/pharmacies/{id}", pharmacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        assertThat(pharmacyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pharmacy.class);
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setId(1L);
        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setId(pharmacy1.getId());
        assertThat(pharmacy1).isEqualTo(pharmacy2);
        pharmacy2.setId(2L);
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);
        pharmacy1.setId(null);
        assertThat(pharmacy1).isNotEqualTo(pharmacy2);
    }
}
