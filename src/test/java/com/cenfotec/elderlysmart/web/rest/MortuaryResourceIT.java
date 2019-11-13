package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Mortuary;
import com.cenfotec.elderlysmart.repository.MortuaryRepository;
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
 * Integration tests for the {@link MortuaryResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class MortuaryResourceIT {

    private static final String DEFAULT_ID_MORTUARY = "AAAAAAAAAA";
    private static final String UPDATED_ID_MORTUARY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private MortuaryRepository mortuaryRepository;

    @Mock
    private MortuaryRepository mortuaryRepositoryMock;

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

    private MockMvc restMortuaryMockMvc;

    private Mortuary mortuary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MortuaryResource mortuaryResource = new MortuaryResource(mortuaryRepository);
        this.restMortuaryMockMvc = MockMvcBuilders.standaloneSetup(mortuaryResource)
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
    public static Mortuary createEntity(EntityManager em) {
        Mortuary mortuary = new Mortuary()
            .idMortuary(DEFAULT_ID_MORTUARY)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return mortuary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mortuary createUpdatedEntity(EntityManager em) {
        Mortuary mortuary = new Mortuary()
            .idMortuary(UPDATED_ID_MORTUARY)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        return mortuary;
    }

    @BeforeEach
    public void initTest() {
        mortuary = createEntity(em);
    }

    @Test
    @Transactional
    public void createMortuary() throws Exception {
        int databaseSizeBeforeCreate = mortuaryRepository.findAll().size();

        // Create the Mortuary
        restMortuaryMockMvc.perform(post("/api/mortuaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortuary)))
            .andExpect(status().isCreated());

        // Validate the Mortuary in the database
        List<Mortuary> mortuaryList = mortuaryRepository.findAll();
        assertThat(mortuaryList).hasSize(databaseSizeBeforeCreate + 1);
        Mortuary testMortuary = mortuaryList.get(mortuaryList.size() - 1);
        assertThat(testMortuary.getIdMortuary()).isEqualTo(DEFAULT_ID_MORTUARY);
        assertThat(testMortuary.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMortuary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMortuary.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createMortuaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mortuaryRepository.findAll().size();

        // Create the Mortuary with an existing ID
        mortuary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMortuaryMockMvc.perform(post("/api/mortuaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortuary)))
            .andExpect(status().isBadRequest());

        // Validate the Mortuary in the database
        List<Mortuary> mortuaryList = mortuaryRepository.findAll();
        assertThat(mortuaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMortuaries() throws Exception {
        // Initialize the database
        mortuaryRepository.saveAndFlush(mortuary);

        // Get all the mortuaryList
        restMortuaryMockMvc.perform(get("/api/mortuaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mortuary.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMortuary").value(hasItem(DEFAULT_ID_MORTUARY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMortuariesWithEagerRelationshipsIsEnabled() throws Exception {
        MortuaryResource mortuaryResource = new MortuaryResource(mortuaryRepositoryMock);
        when(mortuaryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMortuaryMockMvc = MockMvcBuilders.standaloneSetup(mortuaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMortuaryMockMvc.perform(get("/api/mortuaries?eagerload=true"))
        .andExpect(status().isOk());

        verify(mortuaryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMortuariesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MortuaryResource mortuaryResource = new MortuaryResource(mortuaryRepositoryMock);
            when(mortuaryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMortuaryMockMvc = MockMvcBuilders.standaloneSetup(mortuaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMortuaryMockMvc.perform(get("/api/mortuaries?eagerload=true"))
        .andExpect(status().isOk());

            verify(mortuaryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMortuary() throws Exception {
        // Initialize the database
        mortuaryRepository.saveAndFlush(mortuary);

        // Get the mortuary
        restMortuaryMockMvc.perform(get("/api/mortuaries/{id}", mortuary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mortuary.getId().intValue()))
            .andExpect(jsonPath("$.idMortuary").value(DEFAULT_ID_MORTUARY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingMortuary() throws Exception {
        // Get the mortuary
        restMortuaryMockMvc.perform(get("/api/mortuaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMortuary() throws Exception {
        // Initialize the database
        mortuaryRepository.saveAndFlush(mortuary);

        int databaseSizeBeforeUpdate = mortuaryRepository.findAll().size();

        // Update the mortuary
        Mortuary updatedMortuary = mortuaryRepository.findById(mortuary.getId()).get();
        // Disconnect from session so that the updates on updatedMortuary are not directly saved in db
        em.detach(updatedMortuary);
        updatedMortuary
            .idMortuary(UPDATED_ID_MORTUARY)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restMortuaryMockMvc.perform(put("/api/mortuaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMortuary)))
            .andExpect(status().isOk());

        // Validate the Mortuary in the database
        List<Mortuary> mortuaryList = mortuaryRepository.findAll();
        assertThat(mortuaryList).hasSize(databaseSizeBeforeUpdate);
        Mortuary testMortuary = mortuaryList.get(mortuaryList.size() - 1);
        assertThat(testMortuary.getIdMortuary()).isEqualTo(UPDATED_ID_MORTUARY);
        assertThat(testMortuary.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMortuary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMortuary.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingMortuary() throws Exception {
        int databaseSizeBeforeUpdate = mortuaryRepository.findAll().size();

        // Create the Mortuary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortuaryMockMvc.perform(put("/api/mortuaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mortuary)))
            .andExpect(status().isBadRequest());

        // Validate the Mortuary in the database
        List<Mortuary> mortuaryList = mortuaryRepository.findAll();
        assertThat(mortuaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMortuary() throws Exception {
        // Initialize the database
        mortuaryRepository.saveAndFlush(mortuary);

        int databaseSizeBeforeDelete = mortuaryRepository.findAll().size();

        // Delete the mortuary
        restMortuaryMockMvc.perform(delete("/api/mortuaries/{id}", mortuary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mortuary> mortuaryList = mortuaryRepository.findAll();
        assertThat(mortuaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mortuary.class);
        Mortuary mortuary1 = new Mortuary();
        mortuary1.setId(1L);
        Mortuary mortuary2 = new Mortuary();
        mortuary2.setId(mortuary1.getId());
        assertThat(mortuary1).isEqualTo(mortuary2);
        mortuary2.setId(2L);
        assertThat(mortuary1).isNotEqualTo(mortuary2);
        mortuary1.setId(null);
        assertThat(mortuary1).isNotEqualTo(mortuary2);
    }
}
