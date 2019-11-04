package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.UserApp;
import com.cenfotec.elderlysmart.repository.UserAppRepository;
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
 * Integration tests for the {@link UserAppResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class UserAppResourceIT {

    private static final String DEFAULT_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_TYPE_USER = 1;
    private static final Integer UPDATED_ID_TYPE_USER = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private UserAppRepository userAppRepository;

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

    private MockMvc restUserAppMockMvc;

    private UserApp userApp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAppResource userAppResource = new UserAppResource(userAppRepository);
        this.restUserAppMockMvc = MockMvcBuilders.standaloneSetup(userAppResource)
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
    public static UserApp createEntity(EntityManager em) {
        UserApp userApp = new UserApp()
            .identification(DEFAULT_IDENTIFICATION)
            .idTypeUser(DEFAULT_ID_TYPE_USER)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .name2(DEFAULT_NAME_2)
            .lastName(DEFAULT_LAST_NAME)
            .lastName2(DEFAULT_LAST_NAME_2)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .age(DEFAULT_AGE)
            .password(DEFAULT_PASSWORD)
            .state(DEFAULT_STATE);
        return userApp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApp createUpdatedEntity(EntityManager em) {
        UserApp userApp = new UserApp()
            .identification(UPDATED_IDENTIFICATION)
            .idTypeUser(UPDATED_ID_TYPE_USER)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .name2(UPDATED_NAME_2)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .age(UPDATED_AGE)
            .password(UPDATED_PASSWORD)
            .state(UPDATED_STATE);
        return userApp;
    }

    @BeforeEach
    public void initTest() {
        userApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApp() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();

        // Create the UserApp
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isCreated());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate + 1);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
        assertThat(testUserApp.getIdentification()).isEqualTo(DEFAULT_IDENTIFICATION);
        assertThat(testUserApp.getIdTypeUser()).isEqualTo(DEFAULT_ID_TYPE_USER);
        assertThat(testUserApp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserApp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserApp.getName2()).isEqualTo(DEFAULT_NAME_2);
        assertThat(testUserApp.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserApp.getLastName2()).isEqualTo(DEFAULT_LAST_NAME_2);
        assertThat(testUserApp.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testUserApp.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testUserApp.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserApp.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserApp.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createUserAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();

        // Create the UserApp with an existing ID
        userApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserApps() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get all the userAppList
        restUserAppMockMvc.perform(get("/api/user-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].identification").value(hasItem(DEFAULT_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].idTypeUser").value(hasItem(DEFAULT_ID_TYPE_USER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].name2").value(hasItem(DEFAULT_NAME_2)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].lastName2").value(hasItem(DEFAULT_LAST_NAME_2)))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1)))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", userApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userApp.getId().intValue()))
            .andExpect(jsonPath("$.identification").value(DEFAULT_IDENTIFICATION))
            .andExpect(jsonPath("$.idTypeUser").value(DEFAULT_ID_TYPE_USER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.name2").value(DEFAULT_NAME_2))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.lastName2").value(DEFAULT_LAST_NAME_2))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingUserApp() throws Exception {
        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Update the userApp
        UserApp updatedUserApp = userAppRepository.findById(userApp.getId()).get();
        // Disconnect from session so that the updates on updatedUserApp are not directly saved in db
        em.detach(updatedUserApp);
        updatedUserApp
            .identification(UPDATED_IDENTIFICATION)
            .idTypeUser(UPDATED_ID_TYPE_USER)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .name2(UPDATED_NAME_2)
            .lastName(UPDATED_LAST_NAME)
            .lastName2(UPDATED_LAST_NAME_2)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .age(UPDATED_AGE)
            .password(UPDATED_PASSWORD)
            .state(UPDATED_STATE);

        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserApp)))
            .andExpect(status().isOk());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
        assertThat(testUserApp.getIdentification()).isEqualTo(UPDATED_IDENTIFICATION);
        assertThat(testUserApp.getIdTypeUser()).isEqualTo(UPDATED_ID_TYPE_USER);
        assertThat(testUserApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserApp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserApp.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testUserApp.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserApp.getLastName2()).isEqualTo(UPDATED_LAST_NAME_2);
        assertThat(testUserApp.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testUserApp.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testUserApp.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserApp.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserApp.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApp() throws Exception {
        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Create the UserApp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeDelete = userAppRepository.findAll().size();

        // Delete the userApp
        restUserAppMockMvc.perform(delete("/api/user-apps/{id}", userApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApp.class);
        UserApp userApp1 = new UserApp();
        userApp1.setId(1L);
        UserApp userApp2 = new UserApp();
        userApp2.setId(userApp1.getId());
        assertThat(userApp1).isEqualTo(userApp2);
        userApp2.setId(2L);
        assertThat(userApp1).isNotEqualTo(userApp2);
        userApp1.setId(null);
        assertThat(userApp1).isNotEqualTo(userApp2);
    }
}
