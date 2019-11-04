package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Mortuary;
import com.cenfotec.elderlysmart.repository.MortuaryRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Mortuary}.
 */
@RestController
@RequestMapping("/api")
public class MortuaryResource {

    private final Logger log = LoggerFactory.getLogger(MortuaryResource.class);

    private static final String ENTITY_NAME = "mortuary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MortuaryRepository mortuaryRepository;

    public MortuaryResource(MortuaryRepository mortuaryRepository) {
        this.mortuaryRepository = mortuaryRepository;
    }

    /**
     * {@code POST  /mortuaries} : Create a new mortuary.
     *
     * @param mortuary the mortuary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mortuary, or with status {@code 400 (Bad Request)} if the mortuary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mortuaries")
    public ResponseEntity<Mortuary> createMortuary(@RequestBody Mortuary mortuary) throws URISyntaxException {
        log.debug("REST request to save Mortuary : {}", mortuary);
        if (mortuary.getId() != null) {
            throw new BadRequestAlertException("A new mortuary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mortuary result = mortuaryRepository.save(mortuary);
        return ResponseEntity.created(new URI("/api/mortuaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mortuaries} : Updates an existing mortuary.
     *
     * @param mortuary the mortuary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mortuary,
     * or with status {@code 400 (Bad Request)} if the mortuary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mortuary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mortuaries")
    public ResponseEntity<Mortuary> updateMortuary(@RequestBody Mortuary mortuary) throws URISyntaxException {
        log.debug("REST request to update Mortuary : {}", mortuary);
        if (mortuary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mortuary result = mortuaryRepository.save(mortuary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mortuary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mortuaries} : get all the mortuaries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mortuaries in body.
     */
    @GetMapping("/mortuaries")
    public List<Mortuary> getAllMortuaries() {
        log.debug("REST request to get all Mortuaries");
        return mortuaryRepository.findAll();
    }

    /**
     * {@code GET  /mortuaries/:id} : get the "id" mortuary.
     *
     * @param id the id of the mortuary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mortuary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mortuaries/{id}")
    public ResponseEntity<Mortuary> getMortuary(@PathVariable Long id) {
        log.debug("REST request to get Mortuary : {}", id);
        Optional<Mortuary> mortuary = mortuaryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mortuary);
    }

    /**
     * {@code DELETE  /mortuaries/:id} : delete the "id" mortuary.
     *
     * @param id the id of the mortuary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mortuaries/{id}")
    public ResponseEntity<Void> deleteMortuary(@PathVariable Long id) {
        log.debug("REST request to delete Mortuary : {}", id);
        mortuaryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
