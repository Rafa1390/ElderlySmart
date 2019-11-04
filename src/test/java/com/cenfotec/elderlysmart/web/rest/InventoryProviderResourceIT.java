package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.InventoryProvider;
import com.cenfotec.elderlysmart.repository.InventoryProviderRepository;
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
 * Integration tests for the {@link InventoryProviderResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class InventoryProviderResourceIT {

    private static final String DEFAULT_ID_INVENTORY_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_ID_INVENTORY_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_CUANTITY = 1;
    private static final Integer UPDATED_CUANTITY = 2;

    @Autowired
    private InventoryProviderRepository inventoryProviderRepository;

    @Mock
    private InventoryProviderRepository inventoryProviderRepositoryMock;

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

    private MockMvc restInventoryProviderMockMvc;

    private InventoryProvider inventoryProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryProviderResource inventoryProviderResource = new InventoryProviderResource(inventoryProviderRepository);
        this.restInventoryProviderMockMvc = MockMvcBuilders.standaloneSetup(inventoryProviderResource)
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
    public static InventoryProvider createEntity(EntityManager em) {
        InventoryProvider inventoryProvider = new InventoryProvider()
            .idInventoryProvider(DEFAULT_ID_INVENTORY_PROVIDER)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .cuantity(DEFAULT_CUANTITY);
        return inventoryProvider;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryProvider createUpdatedEntity(EntityManager em) {
        InventoryProvider inventoryProvider = new InventoryProvider()
            .idInventoryProvider(UPDATED_ID_INVENTORY_PROVIDER)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .cuantity(UPDATED_CUANTITY);
        return inventoryProvider;
    }

    @BeforeEach
    public void initTest() {
        inventoryProvider = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryProvider() throws Exception {
        int databaseSizeBeforeCreate = inventoryProviderRepository.findAll().size();

        // Create the InventoryProvider
        restInventoryProviderMockMvc.perform(post("/api/inventory-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryProvider)))
            .andExpect(status().isCreated());

        // Validate the InventoryProvider in the database
        List<InventoryProvider> inventoryProviderList = inventoryProviderRepository.findAll();
        assertThat(inventoryProviderList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryProvider testInventoryProvider = inventoryProviderList.get(inventoryProviderList.size() - 1);
        assertThat(testInventoryProvider.getIdInventoryProvider()).isEqualTo(DEFAULT_ID_INVENTORY_PROVIDER);
        assertThat(testInventoryProvider.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInventoryProvider.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryProvider.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testInventoryProvider.getCuantity()).isEqualTo(DEFAULT_CUANTITY);
    }

    @Test
    @Transactional
    public void createInventoryProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryProviderRepository.findAll().size();

        // Create the InventoryProvider with an existing ID
        inventoryProvider.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryProviderMockMvc.perform(post("/api/inventory-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryProvider)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryProvider in the database
        List<InventoryProvider> inventoryProviderList = inventoryProviderRepository.findAll();
        assertThat(inventoryProviderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryProviders() throws Exception {
        // Initialize the database
        inventoryProviderRepository.saveAndFlush(inventoryProvider);

        // Get all the inventoryProviderList
        restInventoryProviderMockMvc.perform(get("/api/inventory-providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].idInventoryProvider").value(hasItem(DEFAULT_ID_INVENTORY_PROVIDER)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].cuantity").value(hasItem(DEFAULT_CUANTITY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllInventoryProvidersWithEagerRelationshipsIsEnabled() throws Exception {
        InventoryProviderResource inventoryProviderResource = new InventoryProviderResource(inventoryProviderRepositoryMock);
        when(inventoryProviderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restInventoryProviderMockMvc = MockMvcBuilders.standaloneSetup(inventoryProviderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInventoryProviderMockMvc.perform(get("/api/inventory-providers?eagerload=true"))
        .andExpect(status().isOk());

        verify(inventoryProviderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllInventoryProvidersWithEagerRelationshipsIsNotEnabled() throws Exception {
        InventoryProviderResource inventoryProviderResource = new InventoryProviderResource(inventoryProviderRepositoryMock);
            when(inventoryProviderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restInventoryProviderMockMvc = MockMvcBuilders.standaloneSetup(inventoryProviderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInventoryProviderMockMvc.perform(get("/api/inventory-providers?eagerload=true"))
        .andExpect(status().isOk());

            verify(inventoryProviderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getInventoryProvider() throws Exception {
        // Initialize the database
        inventoryProviderRepository.saveAndFlush(inventoryProvider);

        // Get the inventoryProvider
        restInventoryProviderMockMvc.perform(get("/api/inventory-providers/{id}", inventoryProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryProvider.getId().intValue()))
            .andExpect(jsonPath("$.idInventoryProvider").value(DEFAULT_ID_INVENTORY_PROVIDER))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.cuantity").value(DEFAULT_CUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryProvider() throws Exception {
        // Get the inventoryProvider
        restInventoryProviderMockMvc.perform(get("/api/inventory-providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryProvider() throws Exception {
        // Initialize the database
        inventoryProviderRepository.saveAndFlush(inventoryProvider);

        int databaseSizeBeforeUpdate = inventoryProviderRepository.findAll().size();

        // Update the inventoryProvider
        InventoryProvider updatedInventoryProvider = inventoryProviderRepository.findById(inventoryProvider.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryProvider are not directly saved in db
        em.detach(updatedInventoryProvider);
        updatedInventoryProvider
            .idInventoryProvider(UPDATED_ID_INVENTORY_PROVIDER)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .cuantity(UPDATED_CUANTITY);

        restInventoryProviderMockMvc.perform(put("/api/inventory-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryProvider)))
            .andExpect(status().isOk());

        // Validate the InventoryProvider in the database
        List<InventoryProvider> inventoryProviderList = inventoryProviderRepository.findAll();
        assertThat(inventoryProviderList).hasSize(databaseSizeBeforeUpdate);
        InventoryProvider testInventoryProvider = inventoryProviderList.get(inventoryProviderList.size() - 1);
        assertThat(testInventoryProvider.getIdInventoryProvider()).isEqualTo(UPDATED_ID_INVENTORY_PROVIDER);
        assertThat(testInventoryProvider.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInventoryProvider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryProvider.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testInventoryProvider.getCuantity()).isEqualTo(UPDATED_CUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryProvider() throws Exception {
        int databaseSizeBeforeUpdate = inventoryProviderRepository.findAll().size();

        // Create the InventoryProvider

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryProviderMockMvc.perform(put("/api/inventory-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryProvider)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryProvider in the database
        List<InventoryProvider> inventoryProviderList = inventoryProviderRepository.findAll();
        assertThat(inventoryProviderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryProvider() throws Exception {
        // Initialize the database
        inventoryProviderRepository.saveAndFlush(inventoryProvider);

        int databaseSizeBeforeDelete = inventoryProviderRepository.findAll().size();

        // Delete the inventoryProvider
        restInventoryProviderMockMvc.perform(delete("/api/inventory-providers/{id}", inventoryProvider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryProvider> inventoryProviderList = inventoryProviderRepository.findAll();
        assertThat(inventoryProviderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryProvider.class);
        InventoryProvider inventoryProvider1 = new InventoryProvider();
        inventoryProvider1.setId(1L);
        InventoryProvider inventoryProvider2 = new InventoryProvider();
        inventoryProvider2.setId(inventoryProvider1.getId());
        assertThat(inventoryProvider1).isEqualTo(inventoryProvider2);
        inventoryProvider2.setId(2L);
        assertThat(inventoryProvider1).isNotEqualTo(inventoryProvider2);
        inventoryProvider1.setId(null);
        assertThat(inventoryProvider1).isNotEqualTo(inventoryProvider2);
    }
}
