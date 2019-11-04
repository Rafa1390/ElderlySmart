package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.PharmacyHistory;
import com.cenfotec.elderlysmart.repository.PharmacyHistoryRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.PharmacyHistory}.
 */
@RestController
@RequestMapping("/api")
public class PharmacyHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PharmacyHistoryResource.class);

    private static final String ENTITY_NAME = "pharmacyHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmacyHistoryRepository pharmacyHistoryRepository;

    public PharmacyHistoryResource(PharmacyHistoryRepository pharmacyHistoryRepository) {
        this.pharmacyHistoryRepository = pharmacyHistoryRepository;
    }

    /**
     * {@code POST  /pharmacy-histories} : Create a new pharmacyHistory.
     *
     * @param pharmacyHistory the pharmacyHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmacyHistory, or with status {@code 400 (Bad Request)} if the pharmacyHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pharmacy-histories")
    public ResponseEntity<PharmacyHistory> createPharmacyHistory(@RequestBody PharmacyHistory pharmacyHistory) throws URISyntaxException {
        log.debug("REST request to save PharmacyHistory : {}", pharmacyHistory);
        if (pharmacyHistory.getId() != null) {
            throw new BadRequestAlertException("A new pharmacyHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PharmacyHistory result = pharmacyHistoryRepository.save(pharmacyHistory);
        return ResponseEntity.created(new URI("/api/pharmacy-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pharmacy-histories} : Updates an existing pharmacyHistory.
     *
     * @param pharmacyHistory the pharmacyHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmacyHistory,
     * or with status {@code 400 (Bad Request)} if the pharmacyHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmacyHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pharmacy-histories")
    public ResponseEntity<PharmacyHistory> updatePharmacyHistory(@RequestBody PharmacyHistory pharmacyHistory) throws URISyntaxException {
        log.debug("REST request to update PharmacyHistory : {}", pharmacyHistory);
        if (pharmacyHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PharmacyHistory result = pharmacyHistoryRepository.save(pharmacyHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pharmacyHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pharmacy-histories} : get all the pharmacyHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmacyHistories in body.
     */
    @GetMapping("/pharmacy-histories")
    public List<PharmacyHistory> getAllPharmacyHistories() {
        log.debug("REST request to get all PharmacyHistories");
        return pharmacyHistoryRepository.findAll();
    }

    /**
     * {@code GET  /pharmacy-histories/:id} : get the "id" pharmacyHistory.
     *
     * @param id the id of the pharmacyHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmacyHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pharmacy-histories/{id}")
    public ResponseEntity<PharmacyHistory> getPharmacyHistory(@PathVariable Long id) {
        log.debug("REST request to get PharmacyHistory : {}", id);
        Optional<PharmacyHistory> pharmacyHistory = pharmacyHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pharmacyHistory);
    }

    /**
     * {@code DELETE  /pharmacy-histories/:id} : delete the "id" pharmacyHistory.
     *
     * @param id the id of the pharmacyHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pharmacy-histories/{id}")
    public ResponseEntity<Void> deletePharmacyHistory(@PathVariable Long id) {
        log.debug("REST request to delete PharmacyHistory : {}", id);
        pharmacyHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
