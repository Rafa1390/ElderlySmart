package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.AppointmentNotification;
import com.cenfotec.elderlysmart.repository.AppointmentNotificationRepository;
import com.cenfotec.elderlysmart.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.AppointmentNotification}.
 */
@RestController
@RequestMapping("/api")
public class AppointmentNotificationResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentNotificationResource.class);

    private static final String ENTITY_NAME = "appointmentNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentNotificationRepository appointmentNotificationRepository;

    public AppointmentNotificationResource(AppointmentNotificationRepository appointmentNotificationRepository) {
        this.appointmentNotificationRepository = appointmentNotificationRepository;
    }

    /**
     * {@code POST  /appointment-notifications} : Create a new appointmentNotification.
     *
     * @param appointmentNotification the appointmentNotification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointmentNotification, or with status {@code 400 (Bad Request)} if the appointmentNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appointment-notifications")
    public ResponseEntity<AppointmentNotification> createAppointmentNotification(@RequestBody AppointmentNotification appointmentNotification) throws URISyntaxException {
        log.debug("REST request to save AppointmentNotification : {}", appointmentNotification);
        if (appointmentNotification.getId() != null) {
            throw new BadRequestAlertException("A new appointmentNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppointmentNotification result = appointmentNotificationRepository.save(appointmentNotification);
        return ResponseEntity.created(new URI("/api/appointment-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appointment-notifications} : Updates an existing appointmentNotification.
     *
     * @param appointmentNotification the appointmentNotification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentNotification,
     * or with status {@code 400 (Bad Request)} if the appointmentNotification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointmentNotification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appointment-notifications")
    public ResponseEntity<AppointmentNotification> updateAppointmentNotification(@RequestBody AppointmentNotification appointmentNotification) throws URISyntaxException {
        log.debug("REST request to update AppointmentNotification : {}", appointmentNotification);
        if (appointmentNotification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppointmentNotification result = appointmentNotificationRepository.save(appointmentNotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointmentNotification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appointment-notifications} : get all the appointmentNotifications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointmentNotifications in body.
     */
    @GetMapping("/appointment-notifications")
    public List<AppointmentNotification> getAllAppointmentNotifications() {
        log.debug("REST request to get all AppointmentNotifications");
        return appointmentNotificationRepository.findAll();
    }

    /**
     * {@code GET  /appointment-notifications/:id} : get the "id" appointmentNotification.
     *
     * @param id the id of the appointmentNotification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointmentNotification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appointment-notifications/{id}")
    public ResponseEntity<AppointmentNotification> getAppointmentNotification(@PathVariable Long id) {
        log.debug("REST request to get AppointmentNotification : {}", id);
        Optional<AppointmentNotification> appointmentNotification = appointmentNotificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appointmentNotification);
    }

    /**
     * {@code DELETE  /appointment-notifications/:id} : delete the "id" appointmentNotification.
     *
     * @param id the id of the appointmentNotification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appointment-notifications/{id}")
    public ResponseEntity<Void> deleteAppointmentNotification(@PathVariable Long id) {
        log.debug("REST request to delete AppointmentNotification : {}", id);
        appointmentNotificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
