package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Allergies;
import com.cenfotec.elderlysmart.repository.AllergiesRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Allergies}.
 */
@RestController
@RequestMapping("/api")
public class AllergiesResource {

    private final Logger log = LoggerFactory.getLogger(AllergiesResource.class);

    private static final String ENTITY_NAME = "allergies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllergiesRepository allergiesRepository;

    public AllergiesResource(AllergiesRepository allergiesRepository) {
        this.allergiesRepository = allergiesRepository;
    }

    /**
     * {@code POST  /allergies} : Create a new allergies.
     *
     * @param allergies the allergies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allergies, or with status {@code 400 (Bad Request)} if the allergies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allergies")
    public ResponseEntity<Allergies> createAllergies(@RequestBody Allergies allergies) throws URISyntaxException {
        log.debug("REST request to save Allergies : {}", allergies);
        if (allergies.getId() != null) {
            throw new BadRequestAlertException("A new allergies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allergies result = allergiesRepository.save(allergies);
        return ResponseEntity.created(new URI("/api/allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allergies} : Updates an existing allergies.
     *
     * @param allergies the allergies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allergies,
     * or with status {@code 400 (Bad Request)} if the allergies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allergies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allergies")
    public ResponseEntity<Allergies> updateAllergies(@RequestBody Allergies allergies) throws URISyntaxException {
        log.debug("REST request to update Allergies : {}", allergies);
        if (allergies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allergies result = allergiesRepository.save(allergies);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allergies.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allergies} : get all the allergies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allergies in body.
     */
    @GetMapping("/allergies")
    public List<Allergies> getAllAllergies() {
        log.debug("REST request to get all Allergies");
        return allergiesRepository.findAll();
    }

    /**
     * {@code GET  /allergies/:id} : get the "id" allergies.
     *
     * @param id the id of the allergies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allergies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allergies/{id}")
    public ResponseEntity<Allergies> getAllergies(@PathVariable Long id) {
        log.debug("REST request to get Allergies : {}", id);
        Optional<Allergies> allergies = allergiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allergies);
    }

    /**
     * {@code DELETE  /allergies/:id} : delete the "id" allergies.
     *
     * @param id the id of the allergies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allergies/{id}")
    public ResponseEntity<Void> deleteAllergies(@PathVariable Long id) {
        log.debug("REST request to delete Allergies : {}", id);
        allergiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
