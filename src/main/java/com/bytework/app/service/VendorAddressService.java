package com.bytework.app.service;

import com.bytework.app.service.dto.VendorAddressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.VendorAddress}.
 */
public interface VendorAddressService {

    /**
     * Save a vendorAddress.
     *
     * @param vendorAddressDTO the entity to save.
     * @return the persisted entity.
     */
    VendorAddressDTO save(VendorAddressDTO vendorAddressDTO);

    /**
     * Get all the vendorAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendorAddressDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vendorAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendorAddressDTO> findOne(Long id);

    /**
     * Delete the "id" vendorAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    VendorAddressDTO findByUserId(long id);

    Page<VendorAddressDTO> findAllByUserId(long id, Pageable pageable);
}
