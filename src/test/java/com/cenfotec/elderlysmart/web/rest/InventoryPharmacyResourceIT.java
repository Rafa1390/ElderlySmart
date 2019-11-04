package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.InventoryPharmacy;
import com.cenfotec.elderlysmart.repository.InventoryPharmacyRepository;
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
import java.util.List;

import static com.cenfotec.elderlysmart.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InventoryPharmacyResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class InventoryPharmacyResourceIT {

    private static final String DEFAULT_ID_INVENTORY_PHARMACY = "AAAAAAAAAA";
    private static final String UPDATED_ID_INVENTORY_PHARMACY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PURCHASE_PRICE = 1;
    private static final Integer UPDATED_PURCHASE_PRICE = 2;

    private static final Integer DEFAULT_SALE_PRICE = 1;
    private static final Integer UPDATED_SALE_PRICE = 2;

    private static final Integer DEFAULT_CUANTITY = 1;
    private static final Integer UPDATED_CUANTITY = 2;

    @Autowired
    private InventoryPharmacyRepository inventoryPharmacyRepository;

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

    private MockMvc restInventoryPharmacyMockMvc;

    private InventoryPharmacy inventoryPharmacy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryPharmacyResource inventoryPharmacyResource = new InventoryPharmacyResource(inventoryPharmacyRepository);
        this.restInventoryPharmacyMockMvc = MockMvcBuilders.standaloneSetup(inventoryPharmacyResource)
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
    public static InventoryPharmacy createEntity(EntityManager em) {
        InventoryPharmacy inventoryPharmacy = new InventoryPharmacy()
            .idInventoryPharmacy(DEFAULT_ID_INVENTORY_PHARMACY)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .purchasePrice(DEFAULT_PURCHASE_PRICE)
            .salePrice(DEFAULT_SALE_PRICE)
            .cuantity(DEFAULT_CUANTITY);
        return inventoryPharmacy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryPharmacy createUpdatedEntity(EntityManager em) {
        InventoryPharmacy inventoryPharmacy = new InventoryPharmacy()
            .idInventoryPharmacy(UPDATED_ID_INVENTORY_PHARMACY)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .purchasePrice(UPDATED_PURCHASE_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .cuantity(UPDATED_CUANTITY);
        return inventoryPharmacy;
    }

    @BeforeEach
    public void initTest() {
        inventoryPharmacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryPharmacy() throws Exception {
        int databaseSizeBeforeCreate = inventoryPharmacyRepository.findAll().size();

        // Create the InventoryPharmacy
        restInventoryPharmacyMockMvc.perform(post("/api/inventory-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryPharmacy)))
            .andExpect(status().isCreated());

        // Validate the InventoryPharmacy in the database
        List<InventoryPharmacy> inventoryPharmacyList = inventoryPharmacyRepository.findAll();
        assertThat(inventoryPharmacyList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryPharmacy testInventoryPharmacy = inventoryPharmacyList.get(inventoryPharmacyList.size() - 1);
        assertThat(testInventoryPharmacy.getIdInventoryPharmacy()).isEqualTo(DEFAULT_ID_INVENTORY_PHARMACY);
        assertThat(testInventoryPharmacy.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInventoryPharmacy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryPharmacy.getPurchasePrice()).isEqualTo(DEFAULT_PURCHASE_PRICE);
        assertThat(testInventoryPharmacy.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
        assertThat(testInventoryPharmacy.getCuantity()).isEqualTo(DEFAULT_CUANTITY);
    }

    @Test
    @Transactional
    public void createInventoryPharmacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryPharmacyRepository.findAll().size();

        // Create the InventoryPharmacy with an existing ID
        inventoryPharmacy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryPharmacyMockMvc.perform(post("/api/inventory-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryPharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryPharmacy in the database
        List<InventoryPharmacy> inventoryPharmacyList = inventoryPharmacyRepository.findAll();
        assertThat(inventoryPharmacyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryPharmacies() throws Exception {
        // Initialize the database
        inventoryPharmacyRepository.saveAndFlush(inventoryPharmacy);

        // Get all the inventoryPharmacyList
        restInventoryPharmacyMockMvc.perform(get("/api/inventory-pharmacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryPharmacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].idInventoryPharmacy").value(hasItem(DEFAULT_ID_INVENTORY_PHARMACY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].purchasePrice").value(hasItem(DEFAULT_PURCHASE_PRICE)))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE)))
            .andExpect(jsonPath("$.[*].cuantity").value(hasItem(DEFAULT_CUANTITY)));
    }
    
    @Test
    @Transactional
    public void getInventoryPharmacy() throws Exception {
        // Initialize the database
        inventoryPharmacyRepository.saveAndFlush(inventoryPharmacy);

        // Get the inventoryPharmacy
        restInventoryPharmacyMockMvc.perform(get("/api/inventory-pharmacies/{id}", inventoryPharmacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryPharmacy.getId().intValue()))
            .andExpect(jsonPath("$.idInventoryPharmacy").value(DEFAULT_ID_INVENTORY_PHARMACY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.purchasePrice").value(DEFAULT_PURCHASE_PRICE))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE))
            .andExpect(jsonPath("$.cuantity").value(DEFAULT_CUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryPharmacy() throws Exception {
        // Get the inventoryPharmacy
        restInventoryPharmacyMockMvc.perform(get("/api/inventory-pharmacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryPharmacy() throws Exception {
        // Initialize the database
        inventoryPharmacyRepository.saveAndFlush(inventoryPharmacy);

        int databaseSizeBeforeUpdate = inventoryPharmacyRepository.findAll().size();

        // Update the inventoryPharmacy
        InventoryPharmacy updatedInventoryPharmacy = inventoryPharmacyRepository.findById(inventoryPharmacy.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryPharmacy are not directly saved in db
        em.detach(updatedInventoryPharmacy);
        updatedInventoryPharmacy
            .idInventoryPharmacy(UPDATED_ID_INVENTORY_PHARMACY)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .purchasePrice(UPDATED_PURCHASE_PRICE)
            .salePrice(UPDATED_SALE_PRICE)
            .cuantity(UPDATED_CUANTITY);

        restInventoryPharmacyMockMvc.perform(put("/api/inventory-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryPharmacy)))
            .andExpect(status().isOk());

        // Validate the InventoryPharmacy in the database
        List<InventoryPharmacy> inventoryPharmacyList = inventoryPharmacyRepository.findAll();
        assertThat(inventoryPharmacyList).hasSize(databaseSizeBeforeUpdate);
        InventoryPharmacy testInventoryPharmacy = inventoryPharmacyList.get(inventoryPharmacyList.size() - 1);
        assertThat(testInventoryPharmacy.getIdInventoryPharmacy()).isEqualTo(UPDATED_ID_INVENTORY_PHARMACY);
        assertThat(testInventoryPharmacy.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInventoryPharmacy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryPharmacy.getPurchasePrice()).isEqualTo(UPDATED_PURCHASE_PRICE);
        assertThat(testInventoryPharmacy.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testInventoryPharmacy.getCuantity()).isEqualTo(UPDATED_CUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryPharmacy() throws Exception {
        int databaseSizeBeforeUpdate = inventoryPharmacyRepository.findAll().size();

        // Create the InventoryPharmacy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryPharmacyMockMvc.perform(put("/api/inventory-pharmacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryPharmacy)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryPharmacy in the database
        List<InventoryPharmacy> inventoryPharmacyList = inventoryPharmacyRepository.findAll();
        assertThat(inventoryPharmacyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryPharmacy() throws Exception {
        // Initialize the database
        inventoryPharmacyRepository.saveAndFlush(inventoryPharmacy);

        int databaseSizeBeforeDelete = inventoryPharmacyRepository.findAll().size();

        // Delete the inventoryPharmacy
        restInventoryPharmacyMockMvc.perform(delete("/api/inventory-pharmacies/{id}", inventoryPharmacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryPharmacy> inventoryPharmacyList = inventoryPharmacyRepository.findAll();
        assertThat(inventoryPharmacyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryPharmacy.class);
        InventoryPharmacy inventoryPharmacy1 = new InventoryPharmacy();
        inventoryPharmacy1.setId(1L);
        InventoryPharmacy inventoryPharmacy2 = new InventoryPharmacy();
        inventoryPharmacy2.setId(inventoryPharmacy1.getId());
        assertThat(inventoryPharmacy1).isEqualTo(inventoryPharmacy2);
        inventoryPharmacy2.setId(2L);
        assertThat(inventoryPharmacy1).isNotEqualTo(inventoryPharmacy2);
        inventoryPharmacy1.setId(null);
        assertThat(inventoryPharmacy1).isNotEqualTo(inventoryPharmacy2);
    }
}
