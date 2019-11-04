package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.InventoryProvider;
import com.cenfotec.elderlysmart.repository.InventoryProviderRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.InventoryProvider}.
 */
@RestController
@RequestMapping("/api")
public class InventoryProviderResource {

    private final Logger log = LoggerFactory.getLogger(InventoryProviderResource.class);

    private static final String ENTITY_NAME = "inventoryProvider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryProviderRepository inventoryProviderRepository;

    public InventoryProviderResource(InventoryProviderRepository inventoryProviderRepository) {
        this.inventoryProviderRepository = inventoryProviderRepository;
    }

    /**
     * {@code POST  /inventory-providers} : Create a new inventoryProvider.
     *
     * @param inventoryProvider the inventoryProvider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryProvider, or with status {@code 400 (Bad Request)} if the inventoryProvider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-providers")
    public ResponseEntity<InventoryProvider> createInventoryProvider(@RequestBody InventoryProvider inventoryProvider) throws URISyntaxException {
        log.debug("REST request to save InventoryProvider : {}", inventoryProvider);
        if (inventoryProvider.getId() != null) {
            throw new BadRequestAlertException("A new inventoryProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryProvider result = inventoryProviderRepository.save(inventoryProvider);
        return ResponseEntity.created(new URI("/api/inventory-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-providers} : Updates an existing inventoryProvider.
     *
     * @param inventoryProvider the inventoryProvider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryProvider,
     * or with status {@code 400 (Bad Request)} if the inventoryProvider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryProvider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-providers")
    public ResponseEntity<InventoryProvider> updateInventoryProvider(@RequestBody InventoryProvider inventoryProvider) throws URISyntaxException {
        log.debug("REST request to update InventoryProvider : {}", inventoryProvider);
        if (inventoryProvider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryProvider result = inventoryProviderRepository.save(inventoryProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryProvider.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-providers} : get all the inventoryProviders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryProviders in body.
     */
    @GetMapping("/inventory-providers")
    public List<InventoryProvider> getAllInventoryProviders(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all InventoryProviders");
        return inventoryProviderRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /inventory-providers/:id} : get the "id" inventoryProvider.
     *
     * @param id the id of the inventoryProvider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryProvider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-providers/{id}")
    public ResponseEntity<InventoryProvider> getInventoryProvider(@PathVariable Long id) {
        log.debug("REST request to get InventoryProvider : {}", id);
        Optional<InventoryProvider> inventoryProvider = inventoryProviderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(inventoryProvider);
    }

    /**
     * {@code DELETE  /inventory-providers/:id} : delete the "id" inventoryProvider.
     *
     * @param id the id of the inventoryProvider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-providers/{id}")
    public ResponseEntity<Void> deleteInventoryProvider(@PathVariable Long id) {
        log.debug("REST request to delete InventoryProvider : {}", id);
        inventoryProviderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
