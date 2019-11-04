package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.CleaningProgram;
import com.cenfotec.elderlysmart.repository.CleaningProgramRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.CleaningProgram}.
 */
@RestController
@RequestMapping("/api")
public class CleaningProgramResource {

    private final Logger log = LoggerFactory.getLogger(CleaningProgramResource.class);

    private static final String ENTITY_NAME = "cleaningProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CleaningProgramRepository cleaningProgramRepository;

    public CleaningProgramResource(CleaningProgramRepository cleaningProgramRepository) {
        this.cleaningProgramRepository = cleaningProgramRepository;
    }

    /**
     * {@code POST  /cleaning-programs} : Create a new cleaningProgram.
     *
     * @param cleaningProgram the cleaningProgram to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cleaningProgram, or with status {@code 400 (Bad Request)} if the cleaningProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cleaning-programs")
    public ResponseEntity<CleaningProgram> createCleaningProgram(@RequestBody CleaningProgram cleaningProgram) throws URISyntaxException {
        log.debug("REST request to save CleaningProgram : {}", cleaningProgram);
        if (cleaningProgram.getId() != null) {
            throw new BadRequestAlertException("A new cleaningProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CleaningProgram result = cleaningProgramRepository.save(cleaningProgram);
        return ResponseEntity.created(new URI("/api/cleaning-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cleaning-programs} : Updates an existing cleaningProgram.
     *
     * @param cleaningProgram the cleaningProgram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cleaningProgram,
     * or with status {@code 400 (Bad Request)} if the cleaningProgram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cleaningProgram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cleaning-programs")
    public ResponseEntity<CleaningProgram> updateCleaningProgram(@RequestBody CleaningProgram cleaningProgram) throws URISyntaxException {
        log.debug("REST request to update CleaningProgram : {}", cleaningProgram);
        if (cleaningProgram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CleaningProgram result = cleaningProgramRepository.save(cleaningProgram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cleaningProgram.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cleaning-programs} : get all the cleaningPrograms.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cleaningPrograms in body.
     */
    @GetMapping("/cleaning-programs")
    public List<CleaningProgram> getAllCleaningPrograms() {
        log.debug("REST request to get all CleaningPrograms");
        return cleaningProgramRepository.findAll();
    }

    /**
     * {@code GET  /cleaning-programs/:id} : get the "id" cleaningProgram.
     *
     * @param id the id of the cleaningProgram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cleaningProgram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cleaning-programs/{id}")
    public ResponseEntity<CleaningProgram> getCleaningProgram(@PathVariable Long id) {
        log.debug("REST request to get CleaningProgram : {}", id);
        Optional<CleaningProgram> cleaningProgram = cleaningProgramRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cleaningProgram);
    }

    /**
     * {@code DELETE  /cleaning-programs/:id} : delete the "id" cleaningProgram.
     *
     * @param id the id of the cleaningProgram to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cleaning-programs/{id}")
    public ResponseEntity<Void> deleteCleaningProgram(@PathVariable Long id) {
        log.debug("REST request to delete CleaningProgram : {}", id);
        cleaningProgramRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
