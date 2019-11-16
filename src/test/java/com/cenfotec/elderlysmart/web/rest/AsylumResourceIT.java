package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.Asylum;
import com.cenfotec.elderlysmart.repository.AsylumRepository;
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
 * Integration tests for the {@link AsylumResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class AsylumResourceIT {

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private AsylumRepository asylumRepository;

    @Mock
    private AsylumRepository asylumRepositoryMock;

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

    private MockMvc restAsylumMockMvc;

    private Asylum asylum;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AsylumResource asylumResource = new AsylumResource(asylumRepository);
        this.restAsylumMockMvc = MockMvcBuilders.standaloneSetup(asylumResource)
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
    public static Asylum createEntity(EntityManager em) {
        Asylum asylum = new Asylum()
            .identification(DEFAULT_IDENTIFICATION)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return asylum;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asylum createUpdatedEntity(EntityManager em) {
        Asylum asylum = new Asylum()
            .identification(UPDATED_IDENTIFICATION)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        return asylum;
    }

    @BeforeEach
    public void initTest() {
        asylum = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsylum() throws Exception {
        int databaseSizeBeforeCreate = asylumRepository.findAll().size();

        // Create the Asylum
        restAsylumMockMvc.perform(post("/api/asylums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asylum)))
            .andExpect(status().isCreated());

        // Validate the Asylum in the database
        List<Asylum> asylumList = asylumRepository.findAll();
        assertThat(asylumList).hasSize(databaseSizeBeforeCreate + 1);
        Asylum testAsylum = asylumList.get(asylumList.size() - 1);
        assertThat(testAsylum.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testAsylum.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAsylum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsylum.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createAsylumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = asylumRepository.findAll().size();

        // Create the Asylum with an existing ID
        asylum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsylumMockMvc.perform(post("/api/asylums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asylum)))
            .andExpect(status().isBadRequest());

        // Validate the Asylum in the database
        List<Asylum> asylumList = asylumRepository.findAll();
        assertThat(asylumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAsylums() throws Exception {
        // Initialize the database
        asylumRepository.saveAndFlush(asylum);

        // Get all the asylumList
        restAsylumMockMvc.perform(get("/api/asylums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asylum.getId().intValue())))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAsylumsWithEagerRelationshipsIsEnabled() throws Exception {
        AsylumResource asylumResource = new AsylumResource(asylumRepositoryMock);
        when(asylumRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAsylumMockMvc = MockMvcBuilders.standaloneSetup(asylumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAsylumMockMvc.perform(get("/api/asylums?eagerload=true"))
        .andExpect(status().isOk());

        verify(asylumRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAsylumsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AsylumResource asylumResource = new AsylumResource(asylumRepositoryMock);
            when(asylumRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAsylumMockMvc = MockMvcBuilders.standaloneSetup(asylumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAsylumMockMvc.perform(get("/api/asylums?eagerload=true"))
        .andExpect(status().isOk());

            verify(asylumRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAsylum() throws Exception {
        // Initialize the database
        asylumRepository.saveAndFlush(asylum);

        // Get the asylum
        restAsylumMockMvc.perform(get("/api/asylums/{id}", asylum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asylum.getId().intValue()))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingAsylum() throws Exception {
        // Get the asylum
        restAsylumMockMvc.perform(get("/api/asylums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsylum() throws Exception {
        // Initialize the database
        asylumRepository.saveAndFlush(asylum);

        int databaseSizeBeforeUpdate = asylumRepository.findAll().size();

        // Update the asylum
        Asylum updatedAsylum = asylumRepository.findById(asylum.getId()).get();
        // Disconnect from session so that the updates on updatedAsylum are not directly saved in db
        em.detach(updatedAsylum);
        updatedAsylum
            .identification(UPDATED_IDENTIFICATION)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restAsylumMockMvc.perform(put("/api/asylums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsylum)))
            .andExpect(status().isOk());

        // Validate the Asylum in the database
        List<Asylum> asylumList = asylumRepository.findAll();
        assertThat(asylumList).hasSize(databaseSizeBeforeUpdate);
        Asylum testAsylum = asylumList.get(asylumList.size() - 1);
        assertThat(testAsylum.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testAsylum.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAsylum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsylum.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingAsylum() throws Exception {
        int databaseSizeBeforeUpdate = asylumRepository.findAll().size();

        // Create the Asylum

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsylumMockMvc.perform(put("/api/asylums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asylum)))
            .andExpect(status().isBadRequest());

        // Validate the Asylum in the database
        List<Asylum> asylumList = asylumRepository.findAll();
        assertThat(asylumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAsylum() throws Exception {
        // Initialize the database
        asylumRepository.saveAndFlush(asylum);

        int databaseSizeBeforeDelete = asylumRepository.findAll().size();

        // Delete the asylum
        restAsylumMockMvc.perform(delete("/api/asylums/{id}", asylum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Asylum> asylumList = asylumRepository.findAll();
        assertThat(asylumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asylum.class);
        Asylum asylum1 = new Asylum();
        asylum1.setId(1L);
        Asylum asylum2 = new Asylum();
        asylum2.setId(asylum1.getId());
        assertThat(asylum1).isEqualTo(asylum2);
        asylum2.setId(2L);
        assertThat(asylum1).isNotEqualTo(asylum2);
        asylum1.setId(null);
        assertThat(asylum1).isNotEqualTo(asylum2);
    }
}
