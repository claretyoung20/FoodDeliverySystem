package com.bytework.app.service.impl;

import com.bytework.app.service.DeliveryTypeService;
import com.bytework.app.domain.DeliveryType;
import com.bytework.app.repository.DeliveryTypeRepository;
import com.bytework.app.service.dto.DeliveryTypeDTO;
import com.bytework.app.service.mapper.DeliveryTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeliveryType}.
 */
@Service
@Transactional
public class DeliveryTypeServiceImpl implements DeliveryTypeService {

    private final Logger log = LoggerFactory.getLogger(DeliveryTypeServiceImpl.class);

    private final DeliveryTypeRepository deliveryTypeRepository;

    private final DeliveryTypeMapper deliveryTypeMapper;

    public DeliveryTypeServiceImpl(DeliveryTypeRepository deliveryTypeRepository, DeliveryTypeMapper deliveryTypeMapper) {
        this.deliveryTypeRepository = deliveryTypeRepository;
        this.deliveryTypeMapper = deliveryTypeMapper;
    }

    /**
     * Save a deliveryType.
     *
     * @param deliveryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeliveryTypeDTO save(DeliveryTypeDTO deliveryTypeDTO) {
        log.debug("Request to save DeliveryType : {}", deliveryTypeDTO);
        DeliveryType deliveryType = deliveryTypeMapper.toEntity(deliveryTypeDTO);
        deliveryType = deliveryTypeRepository.save(deliveryType);
        return deliveryTypeMapper.toDto(deliveryType);
    }

    /**
     * Get all the deliveryTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryTypes");
        return deliveryTypeRepository.findAll(pageable)
            .map(deliveryTypeMapper::toDto);
    }


    /**
     * Get one deliveryType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryTypeDTO> findOne(Long id) {
        log.debug("Request to get DeliveryType : {}", id);
        return deliveryTypeRepository.findById(id)
            .map(deliveryTypeMapper::toDto);
    }

    /**
     * Delete the deliveryType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryType : {}", id);
        deliveryTypeRepository.deleteById(id);
    }
}
