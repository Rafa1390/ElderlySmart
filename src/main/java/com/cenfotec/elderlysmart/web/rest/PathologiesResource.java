package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Pathologies;
import com.cenfotec.elderlysmart.repository.PathologiesRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Pathologies}.
 */
@RestController
@RequestMapping("/api")
public class PathologiesResource {

    private final Logger log = LoggerFactory.getLogger(PathologiesResource.class);

    private static final String ENTITY_NAME = "pathologies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathologiesRepository pathologiesRepository;

    public PathologiesResource(PathologiesRepository pathologiesRepository) {
        this.pathologiesRepository = pathologiesRepository;
    }

    /**
     * {@code POST  /pathologies} : Create a new pathologies.
     *
     * @param pathologies the pathologies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pathologies, or with status {@code 400 (Bad Request)} if the pathologies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pathologies")
    public ResponseEntity<Pathologies> createPathologies(@RequestBody Pathologies pathologies) throws URISyntaxException {
        log.debug("REST request to save Pathologies : {}", pathologies);
        if (pathologies.getId() != null) {
            throw new BadRequestAlertException("A new pathologies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pathologies result = pathologiesRepository.save(pathologies);
        return ResponseEntity.created(new URI("/api/pathologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pathologies} : Updates an existing pathologies.
     *
     * @param pathologies the pathologies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pathologies,
     * or with status {@code 400 (Bad Request)} if the pathologies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pathologies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pathologies")
    public ResponseEntity<Pathologies> updatePathologies(@RequestBody Pathologies pathologies) throws URISyntaxException {
        log.debug("REST request to update Pathologies : {}", pathologies);
        if (pathologies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pathologies result = pathologiesRepository.save(pathologies);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pathologies.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pathologies} : get all the pathologies.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pathologies in body.
     */
    @GetMapping("/pathologies")
    public List<Pathologies> getAllPathologies() {
        log.debug("REST request to get all Pathologies");
        return pathologiesRepository.findAll();
    }

    /**
     * {@code GET  /pathologies/:id} : get the "id" pathologies.
     *
     * @param id the id of the pathologies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pathologies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pathologies/{id}")
    public ResponseEntity<Pathologies> getPathologies(@PathVariable Long id) {
        log.debug("REST request to get Pathologies : {}", id);
        Optional<Pathologies> pathologies = pathologiesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pathologies);
    }

    /**
     * {@code DELETE  /pathologies/:id} : delete the "id" pathologies.
     *
     * @param id the id of the pathologies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pathologies/{id}")
    public ResponseEntity<Void> deletePathologies(@PathVariable Long id) {
        log.debug("REST request to delete Pathologies : {}", id);
        pathologiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
