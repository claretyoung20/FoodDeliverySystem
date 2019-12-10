package com.bytework.app.service.impl;

import com.bytework.app.service.VendorAddressService;
import com.bytework.app.domain.VendorAddress;
import com.bytework.app.repository.VendorAddressRepository;
import com.bytework.app.service.dto.VendorAddressDTO;
import com.bytework.app.service.mapper.VendorAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VendorAddress}.
 */
@Service
@Transactional
public class VendorAddressServiceImpl implements VendorAddressService {

    private final Logger log = LoggerFactory.getLogger(VendorAddressServiceImpl.class);

    private final VendorAddressRepository vendorAddressRepository;

    private final VendorAddressMapper vendorAddressMapper;

    public VendorAddressServiceImpl(VendorAddressRepository vendorAddressRepository, VendorAddressMapper vendorAddressMapper) {
        this.vendorAddressRepository = vendorAddressRepository;
        this.vendorAddressMapper = vendorAddressMapper;
    }

    /**
     * Save a vendorAddress.
     *
     * @param vendorAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VendorAddressDTO save(VendorAddressDTO vendorAddressDTO) {
        log.debug("Request to save VendorAddress : {}", vendorAddressDTO);
        VendorAddress vendorAddress = vendorAddressMapper.toEntity(vendorAddressDTO);
        vendorAddress = vendorAddressRepository.save(vendorAddress);
        return vendorAddressMapper.toDto(vendorAddress);
    }

    /**
     * Get all the vendorAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VendorAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VendorAddresses");
        return vendorAddressRepository.findAll(pageable)
            .map(vendorAddressMapper::toDto);
    }


    /**
     * Get one vendorAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VendorAddressDTO> findOne(Long id) {
        log.debug("Request to get VendorAddress : {}", id);
        return vendorAddressRepository.findById(id)
            .map(vendorAddressMapper::toDto);
    }

    /**
     * Delete the vendorAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VendorAddress : {}", id);
        vendorAddressRepository.deleteById(id);
    }

    @Override
    public VendorAddressDTO findByUserId(long id) {
        log.debug("Request to get VendorAddress by user id : {}", id);
        return vendorAddressMapper.toDto(vendorAddressRepository.findByUserId(id));
    }

    @Override
    public Page<VendorAddressDTO> findAllByUserId(long id, Pageable pageable) {
        log.debug("Request to get all vendor by user id: {}", id);
        return vendorAddressRepository.findAllByUserId(id, pageable)
            .map(vendorAddressMapper::toDto);
    }
}
