package com.bytework.app.service;

import com.bytework.app.service.dto.PaymentMethodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.PaymentMethod}.
 */
public interface PaymentMethodService {

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethodDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentMethodDTO save(PaymentMethodDTO paymentMethodDTO);

    /**
     * Get all the paymentMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentMethodDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentMethodDTO> findOne(Long id);

    /**
     * Delete the "id" paymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
