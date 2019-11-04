package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.FuneralHistory;
import com.cenfotec.elderlysmart.repository.FuneralHistoryRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.FuneralHistory}.
 */
@RestController
@RequestMapping("/api")
public class FuneralHistoryResource {

    private final Logger log = LoggerFactory.getLogger(FuneralHistoryResource.class);

    private static final String ENTITY_NAME = "funeralHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuneralHistoryRepository funeralHistoryRepository;

    public FuneralHistoryResource(FuneralHistoryRepository funeralHistoryRepository) {
        this.funeralHistoryRepository = funeralHistoryRepository;
    }

    /**
     * {@code POST  /funeral-histories} : Create a new funeralHistory.
     *
     * @param funeralHistory the funeralHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funeralHistory, or with status {@code 400 (Bad Request)} if the funeralHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funeral-histories")
    public ResponseEntity<FuneralHistory> createFuneralHistory(@RequestBody FuneralHistory funeralHistory) throws URISyntaxException {
        log.debug("REST request to save FuneralHistory : {}", funeralHistory);
        if (funeralHistory.getId() != null) {
            throw new BadRequestAlertException("A new funeralHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuneralHistory result = funeralHistoryRepository.save(funeralHistory);
        return ResponseEntity.created(new URI("/api/funeral-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funeral-histories} : Updates an existing funeralHistory.
     *
     * @param funeralHistory the funeralHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funeralHistory,
     * or with status {@code 400 (Bad Request)} if the funeralHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funeralHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funeral-histories")
    public ResponseEntity<FuneralHistory> updateFuneralHistory(@RequestBody FuneralHistory funeralHistory) throws URISyntaxException {
        log.debug("REST request to update FuneralHistory : {}", funeralHistory);
        if (funeralHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuneralHistory result = funeralHistoryRepository.save(funeralHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funeralHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /funeral-histories} : get all the funeralHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funeralHistories in body.
     */
    @GetMapping("/funeral-histories")
    public List<FuneralHistory> getAllFuneralHistories() {
        log.debug("REST request to get all FuneralHistories");
        return funeralHistoryRepository.findAll();
    }

    /**
     * {@code GET  /funeral-histories/:id} : get the "id" funeralHistory.
     *
     * @param id the id of the funeralHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funeralHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funeral-histories/{id}")
    public ResponseEntity<FuneralHistory> getFuneralHistory(@PathVariable Long id) {
        log.debug("REST request to get FuneralHistory : {}", id);
        Optional<FuneralHistory> funeralHistory = funeralHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funeralHistory);
    }

    /**
     * {@code DELETE  /funeral-histories/:id} : delete the "id" funeralHistory.
     *
     * @param id the id of the funeralHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funeral-histories/{id}")
    public ResponseEntity<Void> deleteFuneralHistory(@PathVariable Long id) {
        log.debug("REST request to delete FuneralHistory : {}", id);
        funeralHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
