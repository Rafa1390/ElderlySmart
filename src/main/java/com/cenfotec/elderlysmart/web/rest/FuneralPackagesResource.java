package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.FuneralPackages;
import com.cenfotec.elderlysmart.repository.FuneralPackagesRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.FuneralPackages}.
 */
@RestController
@RequestMapping("/api")
public class FuneralPackagesResource {

    private final Logger log = LoggerFactory.getLogger(FuneralPackagesResource.class);

    private static final String ENTITY_NAME = "funeralPackages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuneralPackagesRepository funeralPackagesRepository;

    public FuneralPackagesResource(FuneralPackagesRepository funeralPackagesRepository) {
        this.funeralPackagesRepository = funeralPackagesRepository;
    }

    /**
     * {@code POST  /funeral-packages} : Create a new funeralPackages.
     *
     * @param funeralPackages the funeralPackages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funeralPackages, or with status {@code 400 (Bad Request)} if the funeralPackages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funeral-packages")
    public ResponseEntity<FuneralPackages> createFuneralPackages(@RequestBody FuneralPackages funeralPackages) throws URISyntaxException {
        log.debug("REST request to save FuneralPackages : {}", funeralPackages);
        if (funeralPackages.getId() != null) {
            throw new BadRequestAlertException("A new funeralPackages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuneralPackages result = funeralPackagesRepository.save(funeralPackages);
        return ResponseEntity.created(new URI("/api/funeral-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funeral-packages} : Updates an existing funeralPackages.
     *
     * @param funeralPackages the funeralPackages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funeralPackages,
     * or with status {@code 400 (Bad Request)} if the funeralPackages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funeralPackages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funeral-packages")
    public ResponseEntity<FuneralPackages> updateFuneralPackages(@RequestBody FuneralPackages funeralPackages) throws URISyntaxException {
        log.debug("REST request to update FuneralPackages : {}", funeralPackages);
        if (funeralPackages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FuneralPackages result = funeralPackagesRepository.save(funeralPackages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funeralPackages.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /funeral-packages} : get all the funeralPackages.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funeralPackages in body.
     */
    @GetMapping("/funeral-packages")
    public List<FuneralPackages> getAllFuneralPackages() {
        log.debug("REST request to get all FuneralPackages");
        return funeralPackagesRepository.findAll();
    }

    /**
     * {@code GET  /funeral-packages/:id} : get the "id" funeralPackages.
     *
     * @param id the id of the funeralPackages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funeralPackages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funeral-packages/{id}")
    public ResponseEntity<FuneralPackages> getFuneralPackages(@PathVariable Long id) {
        log.debug("REST request to get FuneralPackages : {}", id);
        Optional<FuneralPackages> funeralPackages = funeralPackagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funeralPackages);
    }

    /**
     * {@code DELETE  /funeral-packages/:id} : delete the "id" funeralPackages.
     *
     * @param id the id of the funeralPackages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funeral-packages/{id}")
    public ResponseEntity<Void> deleteFuneralPackages(@PathVariable Long id) {
        log.debug("REST request to delete FuneralPackages : {}", id);
        funeralPackagesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
