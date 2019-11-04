package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.CaseFile;
import com.cenfotec.elderlysmart.repository.CaseFileRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.CaseFile}.
 */
@RestController
@RequestMapping("/api")
public class CaseFileResource {

    private final Logger log = LoggerFactory.getLogger(CaseFileResource.class);

    private static final String ENTITY_NAME = "caseFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaseFileRepository caseFileRepository;

    public CaseFileResource(CaseFileRepository caseFileRepository) {
        this.caseFileRepository = caseFileRepository;
    }

    /**
     * {@code POST  /case-files} : Create a new caseFile.
     *
     * @param caseFile the caseFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caseFile, or with status {@code 400 (Bad Request)} if the caseFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/case-files")
    public ResponseEntity<CaseFile> createCaseFile(@RequestBody CaseFile caseFile) throws URISyntaxException {
        log.debug("REST request to save CaseFile : {}", caseFile);
        if (caseFile.getId() != null) {
            throw new BadRequestAlertException("A new caseFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaseFile result = caseFileRepository.save(caseFile);
        return ResponseEntity.created(new URI("/api/case-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /case-files} : Updates an existing caseFile.
     *
     * @param caseFile the caseFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseFile,
     * or with status {@code 400 (Bad Request)} if the caseFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caseFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/case-files")
    public ResponseEntity<CaseFile> updateCaseFile(@RequestBody CaseFile caseFile) throws URISyntaxException {
        log.debug("REST request to update CaseFile : {}", caseFile);
        if (caseFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaseFile result = caseFileRepository.save(caseFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /case-files} : get all the caseFiles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caseFiles in body.
     */
    @GetMapping("/case-files")
    public List<CaseFile> getAllCaseFiles() {
        log.debug("REST request to get all CaseFiles");
        return caseFileRepository.findAll();
    }

    /**
     * {@code GET  /case-files/:id} : get the "id" caseFile.
     *
     * @param id the id of the caseFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caseFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/case-files/{id}")
    public ResponseEntity<CaseFile> getCaseFile(@PathVariable Long id) {
        log.debug("REST request to get CaseFile : {}", id);
        Optional<CaseFile> caseFile = caseFileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(caseFile);
    }

    /**
     * {@code DELETE  /case-files/:id} : delete the "id" caseFile.
     *
     * @param id the id of the caseFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/case-files/{id}")
    public ResponseEntity<Void> deleteCaseFile(@PathVariable Long id) {
        log.debug("REST request to delete CaseFile : {}", id);
        caseFileRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
