package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.ProviderHistory;
import com.cenfotec.elderlysmart.repository.ProviderHistoryRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.ProviderHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProviderHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProviderHistoryResource.class);

    private static final String ENTITY_NAME = "providerHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProviderHistoryRepository providerHistoryRepository;

    public ProviderHistoryResource(ProviderHistoryRepository providerHistoryRepository) {
        this.providerHistoryRepository = providerHistoryRepository;
    }

    /**
     * {@code POST  /provider-histories} : Create a new providerHistory.
     *
     * @param providerHistory the providerHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new providerHistory, or with status {@code 400 (Bad Request)} if the providerHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provider-histories")
    public ResponseEntity<ProviderHistory> createProviderHistory(@RequestBody ProviderHistory providerHistory) throws URISyntaxException {
        log.debug("REST request to save ProviderHistory : {}", providerHistory);
        if (providerHistory.getId() != null) {
            throw new BadRequestAlertException("A new providerHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderHistory result = providerHistoryRepository.save(providerHistory);
        return ResponseEntity.created(new URI("/api/provider-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provider-histories} : Updates an existing providerHistory.
     *
     * @param providerHistory the providerHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated providerHistory,
     * or with status {@code 400 (Bad Request)} if the providerHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the providerHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provider-histories")
    public ResponseEntity<ProviderHistory> updateProviderHistory(@RequestBody ProviderHistory providerHistory) throws URISyntaxException {
        log.debug("REST request to update ProviderHistory : {}", providerHistory);
        if (providerHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProviderHistory result = providerHistoryRepository.save(providerHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, providerHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /provider-histories} : get all the providerHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of providerHistories in body.
     */
    @GetMapping("/provider-histories")
    public List<ProviderHistory> getAllProviderHistories() {
        log.debug("REST request to get all ProviderHistories");
        return providerHistoryRepository.findAll();
    }

    /**
     * {@code GET  /provider-histories/:id} : get the "id" providerHistory.
     *
     * @param id the id of the providerHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the providerHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provider-histories/{id}")
    public ResponseEntity<ProviderHistory> getProviderHistory(@PathVariable Long id) {
        log.debug("REST request to get ProviderHistory : {}", id);
        Optional<ProviderHistory> providerHistory = providerHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(providerHistory);
    }

    /**
     * {@code DELETE  /provider-histories/:id} : delete the "id" providerHistory.
     *
     * @param id the id of the providerHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provider-histories/{id}")
    public ResponseEntity<Void> deleteProviderHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProviderHistory : {}", id);
        providerHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
