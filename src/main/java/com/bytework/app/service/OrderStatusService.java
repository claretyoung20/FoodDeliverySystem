package com.bytework.app.service;

import com.bytework.app.service.dto.OrderStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.OrderStatus}.
 */
public interface OrderStatusService {

    /**
     * Save a orderStatus.
     *
     * @param orderStatusDTO the entity to save.
     * @return the persisted entity.
     */
    OrderStatusDTO save(OrderStatusDTO orderStatusDTO);

    /**
     * Get all the orderStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orderStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderStatusDTO> findOne(Long id);

    /**
     * Delete the "id" orderStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
