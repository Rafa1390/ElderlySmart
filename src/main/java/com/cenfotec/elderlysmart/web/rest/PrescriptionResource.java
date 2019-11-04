package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Prescription;
import com.cenfotec.elderlysmart.repository.PrescriptionRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Prescription}.
 */
@RestController
@RequestMapping("/api")
public class PrescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrescriptionResource.class);

    private static final String ENTITY_NAME = "prescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionResource(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * {@code POST  /prescriptions} : Create a new prescription.
     *
     * @param prescription the prescription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescription, or with status {@code 400 (Bad Request)} if the prescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescriptions")
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) throws URISyntaxException {
        log.debug("REST request to save Prescription : {}", prescription);
        if (prescription.getId() != null) {
            throw new BadRequestAlertException("A new prescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prescription result = prescriptionRepository.save(prescription);
        return ResponseEntity.created(new URI("/api/prescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescriptions} : Updates an existing prescription.
     *
     * @param prescription the prescription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescription,
     * or with status {@code 400 (Bad Request)} if the prescription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescriptions")
    public ResponseEntity<Prescription> updatePrescription(@RequestBody Prescription prescription) throws URISyntaxException {
        log.debug("REST request to update Prescription : {}", prescription);
        if (prescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prescription result = prescriptionRepository.save(prescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescription.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescriptions} : get all the prescriptions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescriptions in body.
     */
    @GetMapping("/prescriptions")
    public List<Prescription> getAllPrescriptions() {
        log.debug("REST request to get all Prescriptions");
        return prescriptionRepository.findAll();
    }

    /**
     * {@code GET  /prescriptions/:id} : get the "id" prescription.
     *
     * @param id the id of the prescription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<Prescription> getPrescription(@PathVariable Long id) {
        log.debug("REST request to get Prescription : {}", id);
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prescription);
    }

    /**
     * {@code DELETE  /prescriptions/:id} : delete the "id" prescription.
     *
     * @param id the id of the prescription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        log.debug("REST request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
