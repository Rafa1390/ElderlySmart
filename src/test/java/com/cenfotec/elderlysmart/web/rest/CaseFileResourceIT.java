package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.CaseFile;
import com.cenfotec.elderlysmart.repository.CaseFileRepository;
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
 * Integration tests for the {@link CaseFileResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class CaseFileResourceIT {

    private static final Integer DEFAULT_ID_CASE_FILE = 1;
    private static final Integer UPDATED_ID_CASE_FILE = 2;

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FULL_NAME_ELDERLY = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_ELDERLY = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_WEIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_HEIGHT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RESUSCITATION = "AAAAAAAAAA";
    private static final String UPDATED_RESUSCITATION = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAN_DONATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGAN_DONATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private CaseFileRepository caseFileRepository;

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

    private MockMvc restCaseFileMockMvc;

    private CaseFile caseFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaseFileResource caseFileResource = new CaseFileResource(caseFileRepository);
        this.restCaseFileMockMvc = MockMvcBuilders.standaloneSetup(caseFileResource)
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
    public static CaseFile createEntity(EntityManager em) {
        CaseFile caseFile = new CaseFile()
            .idCaseFile(DEFAULT_ID_CASE_FILE)
            .creationDate(DEFAULT_CREATION_DATE)
            .fullNameElderly(DEFAULT_FULL_NAME_ELDERLY)
            .age(DEFAULT_AGE)
            .religion(DEFAULT_RELIGION)
            .nationality(DEFAULT_NATIONALITY)
            .weight(DEFAULT_WEIGHT)
            .height(DEFAULT_HEIGHT)
            .birth(DEFAULT_BIRTH)
            .gender(DEFAULT_GENDER)
            .bloodType(DEFAULT_BLOOD_TYPE)
            .resuscitation(DEFAULT_RESUSCITATION)
            .organDonation(DEFAULT_ORGAN_DONATION)
            .state(DEFAULT_STATE);
        return caseFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseFile createUpdatedEntity(EntityManager em) {
        CaseFile caseFile = new CaseFile()
            .idCaseFile(UPDATED_ID_CASE_FILE)
            .creationDate(UPDATED_CREATION_DATE)
            .fullNameElderly(UPDATED_FULL_NAME_ELDERLY)
            .age(UPDATED_AGE)
            .religion(UPDATED_RELIGION)
            .nationality(UPDATED_NATIONALITY)
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .birth(UPDATED_BIRTH)
            .gender(UPDATED_GENDER)
            .bloodType(UPDATED_BLOOD_TYPE)
            .resuscitation(UPDATED_RESUSCITATION)
            .organDonation(UPDATED_ORGAN_DONATION)
            .state(UPDATED_STATE);
        return caseFile;
    }

    @BeforeEach
    public void initTest() {
        caseFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaseFile() throws Exception {
        int databaseSizeBeforeCreate = caseFileRepository.findAll().size();

        // Create the CaseFile
        restCaseFileMockMvc.perform(post("/api/case-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseFile)))
            .andExpect(status().isCreated());

        // Validate the CaseFile in the database
        List<CaseFile> caseFileList = caseFileRepository.findAll();
        assertThat(caseFileList).hasSize(databaseSizeBeforeCreate + 1);
        CaseFile testCaseFile = caseFileList.get(caseFileList.size() - 1);
        assertThat(testCaseFile.getIdCaseFile()).isEqualTo(DEFAULT_ID_CASE_FILE);
        assertThat(testCaseFile.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCaseFile.getFullNameElderly()).isEqualTo(DEFAULT_FULL_NAME_ELDERLY);
        assertThat(testCaseFile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCaseFile.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testCaseFile.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCaseFile.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCaseFile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testCaseFile.getBirth()).isEqualTo(DEFAULT_BIRTH);
        assertThat(testCaseFile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCaseFile.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
        assertThat(testCaseFile.getResuscitation()).isEqualTo(DEFAULT_RESUSCITATION);
        assertThat(testCaseFile.getOrganDonation()).isEqualTo(DEFAULT_ORGAN_DONATION);
        assertThat(testCaseFile.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createCaseFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caseFileRepository.findAll().size();

        // Create the CaseFile with an existing ID
        caseFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseFileMockMvc.perform(post("/api/case-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseFile)))
            .andExpect(status().isBadRequest());

        // Validate the CaseFile in the database
        List<CaseFile> caseFileList = caseFileRepository.findAll();
        assertThat(caseFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCaseFiles() throws Exception {
        // Initialize the database
        caseFileRepository.saveAndFlush(caseFile);

        // Get all the caseFileList
        restCaseFileMockMvc.perform(get("/api/case-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCaseFile").value(hasItem(DEFAULT_ID_CASE_FILE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].fullNameElderly").value(hasItem(DEFAULT_FULL_NAME_ELDERLY)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE)))
            .andExpect(jsonPath("$.[*].resuscitation").value(hasItem(DEFAULT_RESUSCITATION)))
            .andExpect(jsonPath("$.[*].organDonation").value(hasItem(DEFAULT_ORGAN_DONATION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getCaseFile() throws Exception {
        // Initialize the database
        caseFileRepository.saveAndFlush(caseFile);

        // Get the caseFile
        restCaseFileMockMvc.perform(get("/api/case-files/{id}", caseFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caseFile.getId().intValue()))
            .andExpect(jsonPath("$.idCaseFile").value(DEFAULT_ID_CASE_FILE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.fullNameElderly").value(DEFAULT_FULL_NAME_ELDERLY))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.birth").value(DEFAULT_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.bloodType").value(DEFAULT_BLOOD_TYPE))
            .andExpect(jsonPath("$.resuscitation").value(DEFAULT_RESUSCITATION))
            .andExpect(jsonPath("$.organDonation").value(DEFAULT_ORGAN_DONATION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingCaseFile() throws Exception {
        // Get the caseFile
        restCaseFileMockMvc.perform(get("/api/case-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaseFile() throws Exception {
        // Initialize the database
        caseFileRepository.saveAndFlush(caseFile);

        int databaseSizeBeforeUpdate = caseFileRepository.findAll().size();

        // Update the caseFile
        CaseFile updatedCaseFile = caseFileRepository.findById(caseFile.getId()).get();
        // Disconnect from session so that the updates on updatedCaseFile are not directly saved in db
        em.detach(updatedCaseFile);
        updatedCaseFile
            .idCaseFile(UPDATED_ID_CASE_FILE)
            .creationDate(UPDATED_CREATION_DATE)
            .fullNameElderly(UPDATED_FULL_NAME_ELDERLY)
            .age(UPDATED_AGE)
            .religion(UPDATED_RELIGION)
            .nationality(UPDATED_NATIONALITY)
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .birth(UPDATED_BIRTH)
            .gender(UPDATED_GENDER)
            .bloodType(UPDATED_BLOOD_TYPE)
            .resuscitation(UPDATED_RESUSCITATION)
            .organDonation(UPDATED_ORGAN_DONATION)
            .state(UPDATED_STATE);

        restCaseFileMockMvc.perform(put("/api/case-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaseFile)))
            .andExpect(status().isOk());

        // Validate the CaseFile in the database
        List<CaseFile> caseFileList = caseFileRepository.findAll();
        assertThat(caseFileList).hasSize(databaseSizeBeforeUpdate);
        CaseFile testCaseFile = caseFileList.get(caseFileList.size() - 1);
        assertThat(testCaseFile.getIdCaseFile()).isEqualTo(UPDATED_ID_CASE_FILE);
        assertThat(testCaseFile.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCaseFile.getFullNameElderly()).isEqualTo(UPDATED_FULL_NAME_ELDERLY);
        assertThat(testCaseFile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCaseFile.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testCaseFile.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCaseFile.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCaseFile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testCaseFile.getBirth()).isEqualTo(UPDATED_BIRTH);
        assertThat(testCaseFile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCaseFile.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testCaseFile.getResuscitation()).isEqualTo(UPDATED_RESUSCITATION);
        assertThat(testCaseFile.getOrganDonation()).isEqualTo(UPDATED_ORGAN_DONATION);
        assertThat(testCaseFile.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCaseFile() throws Exception {
        int databaseSizeBeforeUpdate = caseFileRepository.findAll().size();

        // Create the CaseFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseFileMockMvc.perform(put("/api/case-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseFile)))
            .andExpect(status().isBadRequest());

        // Validate the CaseFile in the database
        List<CaseFile> caseFileList = caseFileRepository.findAll();
        assertThat(caseFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaseFile() throws Exception {
        // Initialize the database
        caseFileRepository.saveAndFlush(caseFile);

        int databaseSizeBeforeDelete = caseFileRepository.findAll().size();

        // Delete the caseFile
        restCaseFileMockMvc.perform(delete("/api/case-files/{id}", caseFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaseFile> caseFileList = caseFileRepository.findAll();
        assertThat(caseFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseFile.class);
        CaseFile caseFile1 = new CaseFile();
        caseFile1.setId(1L);
        CaseFile caseFile2 = new CaseFile();
        caseFile2.setId(caseFile1.getId());
        assertThat(caseFile1).isEqualTo(caseFile2);
        caseFile2.setId(2L);
        assertThat(caseFile1).isNotEqualTo(caseFile2);
        caseFile1.setId(null);
        assertThat(caseFile1).isNotEqualTo(caseFile2);
    }
}
