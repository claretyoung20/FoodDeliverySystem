package com.bytework.app.web.rest;

import com.bytework.app.service.FoodOrderService;
import com.bytework.app.web.rest.errors.BadRequestAlertException;
import com.bytework.app.service.dto.FoodOrderDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bytework.app.domain.FoodOrder}.
 */
@RestController
@RequestMapping("/api")
public class FoodOrderResource {

    private final Logger log = LoggerFactory.getLogger(FoodOrderResource.class);

    private static final String ENTITY_NAME = "foodOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodOrderService foodOrderService;

    public FoodOrderResource(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    /**
     * {@code POST  /food-orders} : Create a new foodOrder.
     *
     * @param foodOrderDTO the foodOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodOrderDTO, or with status {@code 400 (Bad Request)} if the foodOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-orders")
    public ResponseEntity<FoodOrderDTO> createFoodOrder(@Valid @RequestBody FoodOrderDTO foodOrderDTO) throws URISyntaxException {
        log.debug("REST request to save FoodOrder : {}", foodOrderDTO);
        if (foodOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodOrderDTO result = foodOrderService.save(foodOrderDTO);
        return ResponseEntity.created(new URI("/api/food-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-orders} : Updates an existing foodOrder.
     *
     * @param foodOrderDTO the foodOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodOrderDTO,
     * or with status {@code 400 (Bad Request)} if the foodOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-orders")
    public ResponseEntity<FoodOrderDTO> updateFoodOrder(@Valid @RequestBody FoodOrderDTO foodOrderDTO) throws URISyntaxException {
        log.debug("REST request to update FoodOrder : {}", foodOrderDTO);
        if (foodOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodOrderDTO result = foodOrderService.save(foodOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /food-orders} : get all the foodOrders.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodOrders in body.
     */
    @GetMapping("/food-orders")
    public ResponseEntity<List<FoodOrderDTO>> getAllFoodOrders(Pageable pageable) {
        log.debug("REST request to get a page of FoodOrders");
        Page<FoodOrderDTO> page = foodOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /food-orders/:id} : get the "id" foodOrder.
     *
     * @param id the id of the foodOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-orders/{id}")
    public ResponseEntity<FoodOrderDTO> getFoodOrder(@PathVariable Long id) {
        log.debug("REST request to get FoodOrder : {}", id);
        Optional<FoodOrderDTO> foodOrderDTO = foodOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodOrderDTO);
    }

    /**
     * {@code DELETE  /food-orders/:id} : delete the "id" foodOrder.
     *
     * @param id the id of the foodOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-orders/{id}")
    public ResponseEntity<Void> deleteFoodOrder(@PathVariable Long id) {
        log.debug("REST request to delete FoodOrder : {}", id);
        foodOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
