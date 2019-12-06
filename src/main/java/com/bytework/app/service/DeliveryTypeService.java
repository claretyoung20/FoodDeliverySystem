package com.bytework.app.service;

import com.bytework.app.service.dto.DeliveryTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.DeliveryType}.
 */
public interface DeliveryTypeService {

    /**
     * Save a deliveryType.
     *
     * @param deliveryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DeliveryTypeDTO save(DeliveryTypeDTO deliveryTypeDTO);

    /**
     * Get all the deliveryTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeliveryTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deliveryType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeliveryTypeDTO> findOne(Long id);

    /**
     * Delete the "id" deliveryType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
