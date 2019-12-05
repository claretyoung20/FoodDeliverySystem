package com.bytework.app.web.rest;

import com.bytework.app.service.OrderStatusService;
import com.bytework.app.web.rest.errors.BadRequestAlertException;
import com.bytework.app.service.dto.OrderStatusDTO;
import com.bytework.app.service.dto.OrderStatusCriteria;
import com.bytework.app.service.OrderStatusQueryService;

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
 * REST controller for managing {@link com.bytework.app.domain.OrderStatus}.
 */
@RestController
@RequestMapping("/api")
public class OrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(OrderStatusResource.class);

    private static final String ENTITY_NAME = "orderStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderStatusService orderStatusService;

    private final OrderStatusQueryService orderStatusQueryService;

    public OrderStatusResource(OrderStatusService orderStatusService, OrderStatusQueryService orderStatusQueryService) {
        this.orderStatusService = orderStatusService;
        this.orderStatusQueryService = orderStatusQueryService;
    }

    /**
     * {@code POST  /order-statuses} : Create a new orderStatus.
     *
     * @param orderStatusDTO the orderStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderStatusDTO, or with status {@code 400 (Bad Request)} if the orderStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-statuses")
    public ResponseEntity<OrderStatusDTO> createOrderStatus(@Valid @RequestBody OrderStatusDTO orderStatusDTO) throws URISyntaxException {
        log.debug("REST request to save OrderStatus : {}", orderStatusDTO);
        if (orderStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderStatusDTO result = orderStatusService.save(orderStatusDTO);
        return ResponseEntity.created(new URI("/api/order-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-statuses} : Updates an existing orderStatus.
     *
     * @param orderStatusDTO the orderStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderStatusDTO,
     * or with status {@code 400 (Bad Request)} if the orderStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-statuses")
    public ResponseEntity<OrderStatusDTO> updateOrderStatus(@Valid @RequestBody OrderStatusDTO orderStatusDTO) throws URISyntaxException {
        log.debug("REST request to update OrderStatus : {}", orderStatusDTO);
        if (orderStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderStatusDTO result = orderStatusService.save(orderStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-statuses} : get all the orderStatuses.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderStatuses in body.
     */
    @GetMapping("/order-statuses")
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses(OrderStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderStatuses by criteria: {}", criteria);
        Page<OrderStatusDTO> page = orderStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /order-statuses/count} : count all the orderStatuses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/order-statuses/count")
    public ResponseEntity<Long> countOrderStatuses(OrderStatusCriteria criteria) {
        log.debug("REST request to count OrderStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-statuses/:id} : get the "id" orderStatus.
     *
     * @param id the id of the orderStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-statuses/{id}")
    public ResponseEntity<OrderStatusDTO> getOrderStatus(@PathVariable Long id) {
        log.debug("REST request to get OrderStatus : {}", id);
        Optional<OrderStatusDTO> orderStatusDTO = orderStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderStatusDTO);
    }

    /**
     * {@code DELETE  /order-statuses/:id} : delete the "id" orderStatus.
     *
     * @param id the id of the orderStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-statuses/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        log.debug("REST request to delete OrderStatus : {}", id);
        orderStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
