package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Medicament;
import com.cenfotec.elderlysmart.repository.MedicamentRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Medicament}.
 */
@RestController
@RequestMapping("/api")
public class MedicamentResource {

    private final Logger log = LoggerFactory.getLogger(MedicamentResource.class);

    private static final String ENTITY_NAME = "medicament";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicamentRepository medicamentRepository;

    public MedicamentResource(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    /**
     * {@code POST  /medicaments} : Create a new medicament.
     *
     * @param medicament the medicament to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicament, or with status {@code 400 (Bad Request)} if the medicament has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicaments")
    public ResponseEntity<Medicament> createMedicament(@RequestBody Medicament medicament) throws URISyntaxException {
        log.debug("REST request to save Medicament : {}", medicament);
        if (medicament.getId() != null) {
            throw new BadRequestAlertException("A new medicament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medicament result = medicamentRepository.save(medicament);
        return ResponseEntity.created(new URI("/api/medicaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicaments} : Updates an existing medicament.
     *
     * @param medicament the medicament to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicament,
     * or with status {@code 400 (Bad Request)} if the medicament is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicament couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicaments")
    public ResponseEntity<Medicament> updateMedicament(@RequestBody Medicament medicament) throws URISyntaxException {
        log.debug("REST request to update Medicament : {}", medicament);
        if (medicament.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Medicament result = medicamentRepository.save(medicament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicament.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicaments} : get all the medicaments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicaments in body.
     */
    @GetMapping("/medicaments")
    public List<Medicament> getAllMedicaments() {
        log.debug("REST request to get all Medicaments");
        return medicamentRepository.findAll();
    }

    /**
     * {@code GET  /medicaments/:id} : get the "id" medicament.
     *
     * @param id the id of the medicament to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicament, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicaments/{id}")
    public ResponseEntity<Medicament> getMedicament(@PathVariable Long id) {
        log.debug("REST request to get Medicament : {}", id);
        Optional<Medicament> medicament = medicamentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medicament);
    }

    /**
     * {@code DELETE  /medicaments/:id} : delete the "id" medicament.
     *
     * @param id the id of the medicament to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicaments/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        log.debug("REST request to delete Medicament : {}", id);
        medicamentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
