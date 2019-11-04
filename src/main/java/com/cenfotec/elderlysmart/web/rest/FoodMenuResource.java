package com.cenfotec.elderlysmart.web.rest;

import com.cenfotec.elderlysmart.domain.FoodMenu;
import com.cenfotec.elderlysmart.repository.FoodMenuRepository;
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
 * REST controller for managing {@link com.cenfotec.elderlysmart.domain.FoodMenu}.
 */
@RestController
@RequestMapping("/api")
public class FoodMenuResource {

    private final Logger log = LoggerFactory.getLogger(FoodMenuResource.class);

    private static final String ENTITY_NAME = "foodMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodMenuRepository foodMenuRepository;

    public FoodMenuResource(FoodMenuRepository foodMenuRepository) {
        this.foodMenuRepository = foodMenuRepository;
    }

    /**
     * {@code POST  /food-menus} : Create a new foodMenu.
     *
     * @param foodMenu the foodMenu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodMenu, or with status {@code 400 (Bad Request)} if the foodMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-menus")
    public ResponseEntity<FoodMenu> createFoodMenu(@RequestBody FoodMenu foodMenu) throws URISyntaxException {
        log.debug("REST request to save FoodMenu : {}", foodMenu);
        if (foodMenu.getId() != null) {
            throw new BadRequestAlertException("A new foodMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodMenu result = foodMenuRepository.save(foodMenu);
        return ResponseEntity.created(new URI("/api/food-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-menus} : Updates an existing foodMenu.
     *
     * @param foodMenu the foodMenu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodMenu,
     * or with status {@code 400 (Bad Request)} if the foodMenu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodMenu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-menus")
    public ResponseEntity<FoodMenu> updateFoodMenu(@RequestBody FoodMenu foodMenu) throws URISyntaxException {
        log.debug("REST request to update FoodMenu : {}", foodMenu);
        if (foodMenu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodMenu result = foodMenuRepository.save(foodMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodMenu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /food-menus} : get all the foodMenus.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodMenus in body.
     */
    @GetMapping("/food-menus")
    public List<FoodMenu> getAllFoodMenus() {
        log.debug("REST request to get all FoodMenus");
        return foodMenuRepository.findAll();
    }

    /**
     * {@code GET  /food-menus/:id} : get the "id" foodMenu.
     *
     * @param id the id of the foodMenu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodMenu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-menus/{id}")
    public ResponseEntity<FoodMenu> getFoodMenu(@PathVariable Long id) {
        log.debug("REST request to get FoodMenu : {}", id);
        Optional<FoodMenu> foodMenu = foodMenuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foodMenu);
    }

    /**
     * {@code DELETE  /food-menus/:id} : delete the "id" foodMenu.
     *
     * @param id the id of the foodMenu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-menus/{id}")
    public ResponseEntity<Void> deleteFoodMenu(@PathVariable Long id) {
        log.debug("REST request to delete FoodMenu : {}", id);
        foodMenuRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
