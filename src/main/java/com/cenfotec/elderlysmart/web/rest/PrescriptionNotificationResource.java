package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.PrescriptionNotification;
import com.cenfotec.elderlysmart.repository.PrescriptionNotificationRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.PrescriptionNotification}.
 */
@RestController
@RequestMapping("/api")
public class PrescriptionNotificationResource {

    private final Logger log = LoggerFactory.getLogger(PrescriptionNotificationResource.class);

    private static final String ENTITY_NAME = "prescriptionNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescriptionNotificationRepository prescriptionNotificationRepository;

    public PrescriptionNotificationResource(PrescriptionNotificationRepository prescriptionNotificationRepository) {
        this.prescriptionNotificationRepository = prescriptionNotificationRepository;
    }

    /**
     * {@code POST  /prescription-notifications} : Create a new prescriptionNotification.
     *
     * @param prescriptionNotification the prescriptionNotification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescriptionNotification, or with status {@code 400 (Bad Request)} if the prescriptionNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescription-notifications")
    public ResponseEntity<PrescriptionNotification> createPrescriptionNotification(@RequestBody PrescriptionNotification prescriptionNotification) throws URISyntaxException {
        log.debug("REST request to save PrescriptionNotification : {}", prescriptionNotification);
        if (prescriptionNotification.getId() != null) {
            throw new BadRequestAlertException("A new prescriptionNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescriptionNotification result = prescriptionNotificationRepository.save(prescriptionNotification);
        return ResponseEntity.created(new URI("/api/prescription-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescription-notifications} : Updates an existing prescriptionNotification.
     *
     * @param prescriptionNotification the prescriptionNotification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescriptionNotification,
     * or with status {@code 400 (Bad Request)} if the prescriptionNotification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescriptionNotification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescription-notifications")
    public ResponseEntity<PrescriptionNotification> updatePrescriptionNotification(@RequestBody PrescriptionNotification prescriptionNotification) throws URISyntaxException {
        log.debug("REST request to update PrescriptionNotification : {}", prescriptionNotification);
        if (prescriptionNotification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescriptionNotification result = prescriptionNotificationRepository.save(prescriptionNotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescriptionNotification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescription-notifications} : get all the prescriptionNotifications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescriptionNotifications in body.
     */
    @GetMapping("/prescription-notifications")
    public List<PrescriptionNotification> getAllPrescriptionNotifications() {
        log.debug("REST request to get all PrescriptionNotifications");
        return prescriptionNotificationRepository.findAll();
    }

    /**
     * {@code GET  /prescription-notifications/:id} : get the "id" prescriptionNotification.
     *
     * @param id the id of the prescriptionNotification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescriptionNotification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescription-notifications/{id}")
    public ResponseEntity<PrescriptionNotification> getPrescriptionNotification(@PathVariable Long id) {
        log.debug("REST request to get PrescriptionNotification : {}", id);
        Optional<PrescriptionNotification> prescriptionNotification = prescriptionNotificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prescriptionNotification);
    }

    /**
     * {@code DELETE  /prescription-notifications/:id} : delete the "id" prescriptionNotification.
     *
     * @param id the id of the prescriptionNotification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescription-notifications/{id}")
    public ResponseEntity<Void> deletePrescriptionNotification(@PathVariable Long id) {
        log.debug("REST request to delete PrescriptionNotification : {}", id);
        prescriptionNotificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
