package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.ElderlySmartApp;
import com.cenfotec.elderlysmart.domain.AppointmentNotification;
import com.cenfotec.elderlysmart.repository.AppointmentNotificationRepository;
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
 * Integration tests for the {@link AppointmentNotificationResource} REST controller.
 */
@SpringBootTest(classes = ElderlySmartApp.class)
public class AppointmentNotificationResourceIT {

    private static final Integer DEFAULT_ID_APPOINTMENT_NOTIFICATION = 1;
    private static final Integer UPDATED_ID_APPOINTMENT_NOTIFICATION = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private AppointmentNotificationRepository appointmentNotificationRepository;

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

    private MockMvc restAppointmentNotificationMockMvc;

    private AppointmentNotification appointmentNotification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentNotificationResource appointmentNotificationResource = new AppointmentNotificationResource(appointmentNotificationRepository);
        this.restAppointmentNotificationMockMvc = MockMvcBuilders.standaloneSetup(appointmentNotificationResource)
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
    public static AppointmentNotification createEntity(EntityManager em) {
        AppointmentNotification appointmentNotification = new AppointmentNotification()
            .idAppointmentNotification(DEFAULT_ID_APPOINTMENT_NOTIFICATION)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE);
        return appointmentNotification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppointmentNotification createUpdatedEntity(EntityManager em) {
        AppointmentNotification appointmentNotification = new AppointmentNotification()
            .idAppointmentNotification(UPDATED_ID_APPOINTMENT_NOTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);
        return appointmentNotification;
    }

    @BeforeEach
    public void initTest() {
        appointmentNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointmentNotification() throws Exception {
        int databaseSizeBeforeCreate = appointmentNotificationRepository.findAll().size();

        // Create the AppointmentNotification
        restAppointmentNotificationMockMvc.perform(post("/api/appointment-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentNotification)))
            .andExpect(status().isCreated());

        // Validate the AppointmentNotification in the database
        List<AppointmentNotification> appointmentNotificationList = appointmentNotificationRepository.findAll();
        assertThat(appointmentNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        AppointmentNotification testAppointmentNotification = appointmentNotificationList.get(appointmentNotificationList.size() - 1);
        assertThat(testAppointmentNotification.getIdAppointmentNotification()).isEqualTo(DEFAULT_ID_APPOINTMENT_NOTIFICATION);
        assertThat(testAppointmentNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppointmentNotification.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createAppointmentNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentNotificationRepository.findAll().size();

        // Create the AppointmentNotification with an existing ID
        appointmentNotification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentNotificationMockMvc.perform(post("/api/appointment-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentNotification)))
            .andExpect(status().isBadRequest());

        // Validate the AppointmentNotification in the database
        List<AppointmentNotification> appointmentNotificationList = appointmentNotificationRepository.findAll();
        assertThat(appointmentNotificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppointmentNotifications() throws Exception {
        // Initialize the database
        appointmentNotificationRepository.saveAndFlush(appointmentNotification);

        // Get all the appointmentNotificationList
        restAppointmentNotificationMockMvc.perform(get("/api/appointment-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAppointmentNotification").value(hasItem(DEFAULT_ID_APPOINTMENT_NOTIFICATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }
    
    @Test
    @Transactional
    public void getAppointmentNotification() throws Exception {
        // Initialize the database
        appointmentNotificationRepository.saveAndFlush(appointmentNotification);

        // Get the appointmentNotification
        restAppointmentNotificationMockMvc.perform(get("/api/appointment-notifications/{id}", appointmentNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointmentNotification.getId().intValue()))
            .andExpect(jsonPath("$.idAppointmentNotification").value(DEFAULT_ID_APPOINTMENT_NOTIFICATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE));
    }

    @Test
    @Transactional
    public void getNonExistingAppointmentNotification() throws Exception {
        // Get the appointmentNotification
        restAppointmentNotificationMockMvc.perform(get("/api/appointment-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointmentNotification() throws Exception {
        // Initialize the database
        appointmentNotificationRepository.saveAndFlush(appointmentNotification);

        int databaseSizeBeforeUpdate = appointmentNotificationRepository.findAll().size();

        // Update the appointmentNotification
        AppointmentNotification updatedAppointmentNotification = appointmentNotificationRepository.findById(appointmentNotification.getId()).get();
        // Disconnect from session so that the updates on updatedAppointmentNotification are not directly saved in db
        em.detach(updatedAppointmentNotification);
        updatedAppointmentNotification
            .idAppointmentNotification(UPDATED_ID_APPOINTMENT_NOTIFICATION)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);

        restAppointmentNotificationMockMvc.perform(put("/api/appointment-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppointmentNotification)))
            .andExpect(status().isOk());

        // Validate the AppointmentNotification in the database
        List<AppointmentNotification> appointmentNotificationList = appointmentNotificationRepository.findAll();
        assertThat(appointmentNotificationList).hasSize(databaseSizeBeforeUpdate);
        AppointmentNotification testAppointmentNotification = appointmentNotificationList.get(appointmentNotificationList.size() - 1);
        assertThat(testAppointmentNotification.getIdAppointmentNotification()).isEqualTo(UPDATED_ID_APPOINTMENT_NOTIFICATION);
        assertThat(testAppointmentNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppointmentNotification.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointmentNotification() throws Exception {
        int databaseSizeBeforeUpdate = appointmentNotificationRepository.findAll().size();

        // Create the AppointmentNotification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentNotificationMockMvc.perform(put("/api/appointment-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentNotification)))
            .andExpect(status().isBadRequest());

        // Validate the AppointmentNotification in the database
        List<AppointmentNotification> appointmentNotificationList = appointmentNotificationRepository.findAll();
        assertThat(appointmentNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppointmentNotification() throws Exception {
        // Initialize the database
        appointmentNotificationRepository.saveAndFlush(appointmentNotification);

        int databaseSizeBeforeDelete = appointmentNotificationRepository.findAll().size();

        // Delete the appointmentNotification
        restAppointmentNotificationMockMvc.perform(delete("/api/appointment-notifications/{id}", appointmentNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppointmentNotification> appointmentNotificationList = appointmentNotificationRepository.findAll();
        assertThat(appointmentNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppointmentNotification.class);
        AppointmentNotification appointmentNotification1 = new AppointmentNotification();
        appointmentNotification1.setId(1L);
        AppointmentNotification appointmentNotification2 = new AppointmentNotification();
        appointmentNotification2.setId(appointmentNotification1.getId());
        assertThat(appointmentNotification1).isEqualTo(appointmentNotification2);
        appointmentNotification2.setId(2L);
        assertThat(appointmentNotification1).isNotEqualTo(appointmentNotification2);
        appointmentNotification1.setId(null);
        assertThat(appointmentNotification1).isNotEqualTo(appointmentNotification2);
    }
}
