package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.InventoryPharmacy;
import com.cenfotec.elderlysmart.repository.InventoryPharmacyRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.InventoryPharmacy}.
 */
@RestController
@RequestMapping("/api")
public class InventoryPharmacyResource {

    private final Logger log = LoggerFactory.getLogger(InventoryPharmacyResource.class);

    private static final String ENTITY_NAME = "inventoryPharmacy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryPharmacyRepository inventoryPharmacyRepository;

    public InventoryPharmacyResource(InventoryPharmacyRepository inventoryPharmacyRepository) {
        this.inventoryPharmacyRepository = inventoryPharmacyRepository;
    }

    /**
     * {@code POST  /inventory-pharmacies} : Create a new inventoryPharmacy.
     *
     * @param inventoryPharmacy the inventoryPharmacy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryPharmacy, or with status {@code 400 (Bad Request)} if the inventoryPharmacy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-pharmacies")
    public ResponseEntity<InventoryPharmacy> createInventoryPharmacy(@RequestBody InventoryPharmacy inventoryPharmacy) throws URISyntaxException {
        log.debug("REST request to save InventoryPharmacy : {}", inventoryPharmacy);
        if (inventoryPharmacy.getId() != null) {
            throw new BadRequestAlertException("A new inventoryPharmacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryPharmacy result = inventoryPharmacyRepository.save(inventoryPharmacy);
        return ResponseEntity.created(new URI("/api/inventory-pharmacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-pharmacies} : Updates an existing inventoryPharmacy.
     *
     * @param inventoryPharmacy the inventoryPharmacy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryPharmacy,
     * or with status {@code 400 (Bad Request)} if the inventoryPharmacy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryPharmacy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-pharmacies")
    public ResponseEntity<InventoryPharmacy> updateInventoryPharmacy(@RequestBody InventoryPharmacy inventoryPharmacy) throws URISyntaxException {
        log.debug("REST request to update InventoryPharmacy : {}", inventoryPharmacy);
        if (inventoryPharmacy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryPharmacy result = inventoryPharmacyRepository.save(inventoryPharmacy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryPharmacy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-pharmacies} : get all the inventoryPharmacies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryPharmacies in body.
     */
    @GetMapping("/inventory-pharmacies")
    public List<InventoryPharmacy> getAllInventoryPharmacies() {
        log.debug("REST request to get all InventoryPharmacies");
        return inventoryPharmacyRepository.findAll();
    }

    /**
     * {@code GET  /inventory-pharmacies/:id} : get the "id" inventoryPharmacy.
     *
     * @param id the id of the inventoryPharmacy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryPharmacy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-pharmacies/{id}")
    public ResponseEntity<InventoryPharmacy> getInventoryPharmacy(@PathVariable Long id) {
        log.debug("REST request to get InventoryPharmacy : {}", id);
        Optional<InventoryPharmacy> inventoryPharmacy = inventoryPharmacyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventoryPharmacy);
    }

    /**
     * {@code DELETE  /inventory-pharmacies/:id} : delete the "id" inventoryPharmacy.
     *
     * @param id the id of the inventoryPharmacy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-pharmacies/{id}")
    public ResponseEntity<Void> deleteInventoryPharmacy(@PathVariable Long id) {
        log.debug("REST request to delete InventoryPharmacy : {}", id);
        inventoryPharmacyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
