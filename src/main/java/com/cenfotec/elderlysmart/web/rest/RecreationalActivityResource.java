package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.RecreationalActivity;
import com.cenfotec.elderlysmart.repository.RecreationalActivityRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.RecreationalActivity}.
 */
@RestController
@RequestMapping("/api")
public class RecreationalActivityResource {

    private final Logger log = LoggerFactory.getLogger(RecreationalActivityResource.class);

    private static final String ENTITY_NAME = "recreationalActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecreationalActivityRepository recreationalActivityRepository;

    public RecreationalActivityResource(RecreationalActivityRepository recreationalActivityRepository) {
        this.recreationalActivityRepository = recreationalActivityRepository;
    }

    /**
     * {@code POST  /recreational-activities} : Create a new recreationalActivity.
     *
     * @param recreationalActivity the recreationalActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recreationalActivity, or with status {@code 400 (Bad Request)} if the recreationalActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recreational-activities")
    public ResponseEntity<RecreationalActivity> createRecreationalActivity(@RequestBody RecreationalActivity recreationalActivity) throws URISyntaxException {
        log.debug("REST request to save RecreationalActivity : {}", recreationalActivity);
        if (recreationalActivity.getId() != null) {
            throw new BadRequestAlertException("A new recreationalActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecreationalActivity result = recreationalActivityRepository.save(recreationalActivity);
        return ResponseEntity.created(new URI("/api/recreational-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recreational-activities} : Updates an existing recreationalActivity.
     *
     * @param recreationalActivity the recreationalActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recreationalActivity,
     * or with status {@code 400 (Bad Request)} if the recreationalActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recreationalActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recreational-activities")
    public ResponseEntity<RecreationalActivity> updateRecreationalActivity(@RequestBody RecreationalActivity recreationalActivity) throws URISyntaxException {
        log.debug("REST request to update RecreationalActivity : {}", recreationalActivity);
        if (recreationalActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecreationalActivity result = recreationalActivityRepository.save(recreationalActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recreationalActivity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recreational-activities} : get all the recreationalActivities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recreationalActivities in body.
     */
    @GetMapping("/recreational-activities")
    public List<RecreationalActivity> getAllRecreationalActivities() {
        log.debug("REST request to get all RecreationalActivities");
        return recreationalActivityRepository.findAll();
    }

    /**
     * {@code GET  /recreational-activities/:id} : get the "id" recreationalActivity.
     *
     * @param id the id of the recreationalActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recreationalActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recreational-activities/{id}")
    public ResponseEntity<RecreationalActivity> getRecreationalActivity(@PathVariable Long id) {
        log.debug("REST request to get RecreationalActivity : {}", id);
        Optional<RecreationalActivity> recreationalActivity = recreationalActivityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recreationalActivity);
    }

    /**
     * {@code DELETE  /recreational-activities/:id} : delete the "id" recreationalActivity.
     *
     * @param id the id of the recreationalActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recreational-activities/{id}")
    public ResponseEntity<Void> deleteRecreationalActivity(@PathVariable Long id) {
        log.debug("REST request to delete RecreationalActivity : {}", id);
        recreationalActivityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
