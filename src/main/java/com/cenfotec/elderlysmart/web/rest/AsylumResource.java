package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.Asylum;
import com.cenfotec.elderlysmart.repository.AsylumRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.Asylum}.
 */
@RestController
@RequestMapping("/api")
public class AsylumResource {

    private final Logger log = LoggerFactory.getLogger(AsylumResource.class);

    private static final String ENTITY_NAME = "asylum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsylumRepository asylumRepository;

    public AsylumResource(AsylumRepository asylumRepository) {
        this.asylumRepository = asylumRepository;
    }

    /**
     * {@code POST  /asylums} : Create a new asylum.
     *
     * @param asylum the asylum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asylum, or with status {@code 400 (Bad Request)} if the asylum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asylums")
    public ResponseEntity<Asylum> createAsylum(@RequestBody Asylum asylum) throws URISyntaxException {
        log.debug("REST request to save Asylum : {}", asylum);
        if (asylum.getId() != null) {
            throw new BadRequestAlertException("A new asylum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Asylum result = asylumRepository.save(asylum);
        return ResponseEntity.created(new URI("/api/asylums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asylums} : Updates an existing asylum.
     *
     * @param asylum the asylum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asylum,
     * or with status {@code 400 (Bad Request)} if the asylum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asylum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asylums")
    public ResponseEntity<Asylum> updateAsylum(@RequestBody Asylum asylum) throws URISyntaxException {
        log.debug("REST request to update Asylum : {}", asylum);
        if (asylum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Asylum result = asylumRepository.save(asylum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asylum.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asylums} : get all the asylums.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asylums in body.
     */
    @GetMapping("/asylums")
    public List<Asylum> getAllAsylums(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Asylums");
        return asylumRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /asylums/:id} : get the "id" asylum.
     *
     * @param id the id of the asylum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asylum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asylums/{id}")
    public ResponseEntity<Asylum> getAsylum(@PathVariable Long id) {
        log.debug("REST request to get Asylum : {}", id);
        Optional<Asylum> asylum = asylumRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(asylum);
    }

    /**
     * {@code DELETE  /asylums/:id} : delete the "id" asylum.
     *
     * @param id the id of the asylum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asylums/{id}")
    public ResponseEntity<Void> deleteAsylum(@PathVariable Long id) {
        log.debug("REST request to delete Asylum : {}", id);
        asylumRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
