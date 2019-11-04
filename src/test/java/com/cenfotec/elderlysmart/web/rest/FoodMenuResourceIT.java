package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.FoodMenu;
import com.cenfotec.elderlysmart.repository.FoodMenuRepository;
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
 * Integration tests for the {@link FoodMenuResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class FoodMenuResourceIT {

    private static final Integer DEFAULT_ID_FOOD_MENU = 1;
    private static final Integer UPDATED_ID_FOOD_MENU = 2;

    private static final String DEFAULT_FEEDING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_FEEDING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FoodMenuRepository foodMenuRepository;

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

    private MockMvc restFoodMenuMockMvc;

    private FoodMenu foodMenu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodMenuResource foodMenuResource = new FoodMenuResource(foodMenuRepository);
        this.restFoodMenuMockMvc = MockMvcBuilders.standaloneSetup(foodMenuResource)
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
    public static FoodMenu createEntity(EntityManager em) {
        FoodMenu foodMenu = new FoodMenu()
            .idFoodMenu(DEFAULT_ID_FOOD_MENU)
            .feedingTime(DEFAULT_FEEDING_TIME)
            .description(DEFAULT_DESCRIPTION);
        return foodMenu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodMenu createUpdatedEntity(EntityManager em) {
        FoodMenu foodMenu = new FoodMenu()
            .idFoodMenu(UPDATED_ID_FOOD_MENU)
            .feedingTime(UPDATED_FEEDING_TIME)
            .description(UPDATED_DESCRIPTION);
        return foodMenu;
    }

    @BeforeEach
    public void initTest() {
        foodMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodMenu() throws Exception {
        int databaseSizeBeforeCreate = foodMenuRepository.findAll().size();

        // Create the FoodMenu
        restFoodMenuMockMvc.perform(post("/api/food-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodMenu)))
            .andExpect(status().isCreated());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeCreate + 1);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getIdFoodMenu()).isEqualTo(DEFAULT_ID_FOOD_MENU);
        assertThat(testFoodMenu.getFeedingTime()).isEqualTo(DEFAULT_FEEDING_TIME);
        assertThat(testFoodMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFoodMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodMenuRepository.findAll().size();

        // Create the FoodMenu with an existing ID
        foodMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodMenuMockMvc.perform(post("/api/food-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodMenu)))
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFoodMenus() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        // Get all the foodMenuList
        restFoodMenuMockMvc.perform(get("/api/food-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFoodMenu").value(hasItem(DEFAULT_ID_FOOD_MENU)))
            .andExpect(jsonPath("$.[*].feedingTime").value(hasItem(DEFAULT_FEEDING_TIME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        // Get the foodMenu
        restFoodMenuMockMvc.perform(get("/api/food-menus/{id}", foodMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodMenu.getId().intValue()))
            .andExpect(jsonPath("$.idFoodMenu").value(DEFAULT_ID_FOOD_MENU))
            .andExpect(jsonPath("$.feedingTime").value(DEFAULT_FEEDING_TIME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingFoodMenu() throws Exception {
        // Get the foodMenu
        restFoodMenuMockMvc.perform(get("/api/food-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();

        // Update the foodMenu
        FoodMenu updatedFoodMenu = foodMenuRepository.findById(foodMenu.getId()).get();
        // Disconnect from session so that the updates on updatedFoodMenu are not directly saved in db
        em.detach(updatedFoodMenu);
        updatedFoodMenu
            .idFoodMenu(UPDATED_ID_FOOD_MENU)
            .feedingTime(UPDATED_FEEDING_TIME)
            .description(UPDATED_DESCRIPTION);

        restFoodMenuMockMvc.perform(put("/api/food-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodMenu)))
            .andExpect(status().isOk());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
        FoodMenu testFoodMenu = foodMenuList.get(foodMenuList.size() - 1);
        assertThat(testFoodMenu.getIdFoodMenu()).isEqualTo(UPDATED_ID_FOOD_MENU);
        assertThat(testFoodMenu.getFeedingTime()).isEqualTo(UPDATED_FEEDING_TIME);
        assertThat(testFoodMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodMenu() throws Exception {
        int databaseSizeBeforeUpdate = foodMenuRepository.findAll().size();

        // Create the FoodMenu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodMenuMockMvc.perform(put("/api/food-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodMenu)))
            .andExpect(status().isBadRequest());

        // Validate the FoodMenu in the database
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodMenu() throws Exception {
        // Initialize the database
        foodMenuRepository.saveAndFlush(foodMenu);

        int databaseSizeBeforeDelete = foodMenuRepository.findAll().size();

        // Delete the foodMenu
        restFoodMenuMockMvc.perform(delete("/api/food-menus/{id}", foodMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodMenu> foodMenuList = foodMenuRepository.findAll();
        assertThat(foodMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodMenu.class);
        FoodMenu foodMenu1 = new FoodMenu();
        foodMenu1.setId(1L);
        FoodMenu foodMenu2 = new FoodMenu();
        foodMenu2.setId(foodMenu1.getId());
        assertThat(foodMenu1).isEqualTo(foodMenu2);
        foodMenu2.setId(2L);
        assertThat(foodMenu1).isNotEqualTo(foodMenu2);
        foodMenu1.setId(null);
        assertThat(foodMenu1).isNotEqualTo(foodMenu2);
    }
}
