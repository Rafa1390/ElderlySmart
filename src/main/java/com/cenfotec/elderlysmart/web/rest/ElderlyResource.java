package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.CaseFile;
import com.cenfotec.elderlysmart.domain.Elderly;
import com.cenfotec.elderlysmart.repository.CaseFileRepository;
import com.cenfotec.elderlysmart.repository.ElderlyRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Elderly}.
 */
@RestController
@RequestMapping("/api")
public class ElderlyResource {

    private final Logger log = LoggerFactory.getLogger(ElderlyResource.class);

    private static final String ENTITY_NAME = "elderly";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElderlyRepository elderlyRepository;


    public ElderlyResource(ElderlyRepository elderlyRepository) {
        this.elderlyRepository = elderlyRepository;
    }

    /**
     * {@code POST  /elderlies} : Create a new elderly.
     *
     * @param elderly the elderly to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elderly, or with status {@code 400 (Bad Request)} if the elderly has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elderlies")
    public ResponseEntity<Elderly> createElderly(@RequestBody Elderly elderly, CaseFile caseFile) throws URISyntaxException {
        log.debug("REST request to save Elderly : {}", elderly);

        LocalDate date = LocalDate.now();
        if (elderly.getId() != null) {
            throw new BadRequestAlertException("A new elderly cannot already have an ID", ENTITY_NAME, "idexists");
        }
        elderly.setAdmissionDate(date);

        Elderly result = elderlyRepository.save(elderly);
        return ResponseEntity.created(new URI("/api/elderlies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elderlies} : Updates an existing elderly.
     *
     * @param elderly the elderly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elderly,
     * or with status {@code 400 (Bad Request)} if the elderly is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elderly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elderlies")
    public ResponseEntity<Elderly> updateElderly(@RequestBody Elderly elderly) throws URISyntaxException {
        log.debug("REST request to update Elderly : {}", elderly);
        if (elderly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Elderly result = elderlyRepository.save(elderly);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elderly.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elderlies} : get all the elderlies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elderlies in body.
     */
    @GetMapping("/elderlies")
    public List<Elderly> getAllElderlies() {
        log.debug("REST request to get all Elderlies");
        return elderlyRepository.findAll();
    }

    /**
     * {@code GET  /elderlies/:id} : get the "id" elderly.
     *
     * @param id the id of the elderly to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elderly, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elderlies/{id}")
    public ResponseEntity<Elderly> getElderly(@PathVariable Long id) {
        log.debug("REST request to get Elderly : {}", id);
        Optional<Elderly> elderly = elderlyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elderly);
    }

    /**
     * {@code DELETE  /elderlies/:id} : delete the "id" elderly.
     *
     * @param id the id of the elderly to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elderlies/{id}")
    public ResponseEntity<Void> deleteElderly(@PathVariable Long id) {
        log.debug("REST request to delete Elderly : {}", id);
        elderlyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
