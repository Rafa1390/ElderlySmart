package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Family;
import com.cenfotec.elderlysmart.repository.FamilyRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Family}.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {

    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);

    private static final String ENTITY_NAME = "family";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyRepository familyRepository;

    public FamilyResource(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    /**
     * {@code POST  /families} : Create a new family.
     *
     * @param family the family to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new family, or with status {@code 400 (Bad Request)} if the family has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/families")
    public ResponseEntity<Family> createFamily(@RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to save Family : {}", family);
        if (family.getId() != null) {
            throw new BadRequestAlertException("A new family cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Family result = familyRepository.save(family);
        return ResponseEntity.created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /families} : Updates an existing family.
     *
     * @param family the family to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated family,
     * or with status {@code 400 (Bad Request)} if the family is not valid,
     * or with status {@code 500 (Internal Server Error)} if the family couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/families")
    public ResponseEntity<Family> updateFamily(@RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to update Family : {}", family);
        if (family.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Family result = familyRepository.save(family);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, family.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /families} : get all the families.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of families in body.
     */
    @GetMapping("/families")
    public List<Family> getAllFamilies() {
        log.debug("REST request to get all Families");
        return familyRepository.findAll();
    }

    /**
     * {@code GET  /families/:id} : get the "id" family.
     *
     * @param id the id of the family to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the family, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/families/{id}")
    public ResponseEntity<Family> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Optional<Family> family = familyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(family);
    }

    /**
     * {@code DELETE  /families/:id} : delete the "id" family.
     *
     * @param id the id of the family to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/families/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);
        familyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
