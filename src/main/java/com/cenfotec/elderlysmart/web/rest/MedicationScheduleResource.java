package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.MedicationSchedule;
import com.cenfotec.elderlysmart.repository.MedicationScheduleRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.MedicationSchedule}.
 */
@RestController
@RequestMapping("/api")
public class MedicationScheduleResource {

    private final Logger log = LoggerFactory.getLogger(MedicationScheduleResource.class);

    private static final String ENTITY_NAME = "medicationSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicationScheduleRepository medicationScheduleRepository;

    public MedicationScheduleResource(MedicationScheduleRepository medicationScheduleRepository) {
        this.medicationScheduleRepository = medicationScheduleRepository;
    }

    /**
     * {@code POST  /medication-schedules} : Create a new medicationSchedule.
     *
     * @param medicationSchedule the medicationSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicationSchedule, or with status {@code 400 (Bad Request)} if the medicationSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medication-schedules")
    public ResponseEntity<MedicationSchedule> createMedicationSchedule(@RequestBody MedicationSchedule medicationSchedule) throws URISyntaxException {
        log.debug("REST request to save MedicationSchedule : {}", medicationSchedule);
        if (medicationSchedule.getId() != null) {
            throw new BadRequestAlertException("A new medicationSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicationSchedule result = medicationScheduleRepository.save(medicationSchedule);
        return ResponseEntity.created(new URI("/api/medication-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medication-schedules} : Updates an existing medicationSchedule.
     *
     * @param medicationSchedule the medicationSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationSchedule,
     * or with status {@code 400 (Bad Request)} if the medicationSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicationSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medication-schedules")
    public ResponseEntity<MedicationSchedule> updateMedicationSchedule(@RequestBody MedicationSchedule medicationSchedule) throws URISyntaxException {
        log.debug("REST request to update MedicationSchedule : {}", medicationSchedule);
        if (medicationSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicationSchedule result = medicationScheduleRepository.save(medicationSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicationSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medication-schedules} : get all the medicationSchedules.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicationSchedules in body.
     */
    @GetMapping("/medication-schedules")
    public List<MedicationSchedule> getAllMedicationSchedules() {
        log.debug("REST request to get all MedicationSchedules");
        return medicationScheduleRepository.findAll();
    }

    /**
     * {@code GET  /medication-schedules/:id} : get the "id" medicationSchedule.
     *
     * @param id the id of the medicationSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicationSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medication-schedules/{id}")
    public ResponseEntity<MedicationSchedule> getMedicationSchedule(@PathVariable Long id) {
        log.debug("REST request to get MedicationSchedule : {}", id);
        Optional<MedicationSchedule> medicationSchedule = medicationScheduleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medicationSchedule);
    }

    /**
     * {@code DELETE  /medication-schedules/:id} : delete the "id" medicationSchedule.
     *
     * @param id the id of the medicationSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medication-schedules/{id}")
    public ResponseEntity<Void> deleteMedicationSchedule(@PathVariable Long id) {
        log.debug("REST request to delete MedicationSchedule : {}", id);
        medicationScheduleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
