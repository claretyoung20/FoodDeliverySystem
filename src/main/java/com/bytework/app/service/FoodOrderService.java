package com.bytework.app.service;

import com.bytework.app.service.dto.FoodOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.FoodOrder}.
 */
public interface FoodOrderService {

    /**
     * Save a foodOrder.
     *
     * @param foodOrderDTO the entity to save.
     * @return the persisted entity.
     */
    FoodOrderDTO save(FoodOrderDTO foodOrderDTO);

    /**
     * Get all the foodOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoodOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" foodOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoodOrderDTO> findOne(Long id);

    /**
     * Delete the "id" foodOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
