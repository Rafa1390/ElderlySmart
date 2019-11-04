package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.MedicalAppointment;
import com.cenfotec.elderlysmart.repository.MedicalAppointmentRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.MedicalAppointment}.
 */
@RestController
@RequestMapping("/api")
public class MedicalAppointmentResource {

    private final Logger log = LoggerFactory.getLogger(MedicalAppointmentResource.class);

    private static final String ENTITY_NAME = "medicalAppointment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentResource(MedicalAppointmentRepository medicalAppointmentRepository) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    /**
     * {@code POST  /medical-appointments} : Create a new medicalAppointment.
     *
     * @param medicalAppointment the medicalAppointment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalAppointment, or with status {@code 400 (Bad Request)} if the medicalAppointment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-appointments")
    public ResponseEntity<MedicalAppointment> createMedicalAppointment(@RequestBody MedicalAppointment medicalAppointment) throws URISyntaxException {
        log.debug("REST request to save MedicalAppointment : {}", medicalAppointment);
        if (medicalAppointment.getId() != null) {
            throw new BadRequestAlertException("A new medicalAppointment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalAppointment result = medicalAppointmentRepository.save(medicalAppointment);
        return ResponseEntity.created(new URI("/api/medical-appointments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-appointments} : Updates an existing medicalAppointment.
     *
     * @param medicalAppointment the medicalAppointment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalAppointment,
     * or with status {@code 400 (Bad Request)} if the medicalAppointment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalAppointment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-appointments")
    public ResponseEntity<MedicalAppointment> updateMedicalAppointment(@RequestBody MedicalAppointment medicalAppointment) throws URISyntaxException {
        log.debug("REST request to update MedicalAppointment : {}", medicalAppointment);
        if (medicalAppointment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalAppointment result = medicalAppointmentRepository.save(medicalAppointment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalAppointment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-appointments} : get all the medicalAppointments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalAppointments in body.
     */
    @GetMapping("/medical-appointments")
    public List<MedicalAppointment> getAllMedicalAppointments() {
        log.debug("REST request to get all MedicalAppointments");
        return medicalAppointmentRepository.findAll();
    }

    /**
     * {@code GET  /medical-appointments/:id} : get the "id" medicalAppointment.
     *
     * @param id the id of the medicalAppointment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalAppointment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-appointments/{id}")
    public ResponseEntity<MedicalAppointment> getMedicalAppointment(@PathVariable Long id) {
        log.debug("REST request to get MedicalAppointment : {}", id);
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medicalAppointment);
    }

    /**
     * {@code DELETE  /medical-appointments/:id} : delete the "id" medicalAppointment.
     *
     * @param id the id of the medicalAppointment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-appointments/{id}")
    public ResponseEntity<Void> deleteMedicalAppointment(@PathVariable Long id) {
        log.debug("REST request to delete MedicalAppointment : {}", id);
        medicalAppointmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
