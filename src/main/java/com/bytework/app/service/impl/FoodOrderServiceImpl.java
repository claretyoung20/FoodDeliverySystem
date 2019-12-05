package com.bytework.app.service.impl;

import com.bytework.app.service.FoodOrderService;
import com.bytework.app.domain.FoodOrder;
import com.bytework.app.repository.FoodOrderRepository;
import com.bytework.app.service.dto.FoodOrderDTO;
import com.bytework.app.service.mapper.FoodOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FoodOrder}.
 */
@Service
@Transactional
public class FoodOrderServiceImpl implements FoodOrderService {

    private final Logger log = LoggerFactory.getLogger(FoodOrderServiceImpl.class);

    private final FoodOrderRepository foodOrderRepository;

    private final FoodOrderMapper foodOrderMapper;

    public FoodOrderServiceImpl(FoodOrderRepository foodOrderRepository, FoodOrderMapper foodOrderMapper) {
        this.foodOrderRepository = foodOrderRepository;
        this.foodOrderMapper = foodOrderMapper;
    }

    /**
     * Save a foodOrder.
     *
     * @param foodOrderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FoodOrderDTO save(FoodOrderDTO foodOrderDTO) {
        log.debug("Request to save FoodOrder : {}", foodOrderDTO);
        FoodOrder foodOrder = foodOrderMapper.toEntity(foodOrderDTO);
        foodOrder = foodOrderRepository.save(foodOrder);
        return foodOrderMapper.toDto(foodOrder);
    }

    /**
     * Get all the foodOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FoodOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FoodOrders");
        return foodOrderRepository.findAll(pageable)
            .map(foodOrderMapper::toDto);
    }


    /**
     * Get one foodOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FoodOrderDTO> findOne(Long id) {
        log.debug("Request to get FoodOrder : {}", id);
        return foodOrderRepository.findById(id)
            .map(foodOrderMapper::toDto);
    }

    /**
     * Delete the foodOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodOrder : {}", id);
        foodOrderRepository.deleteById(id);
    }
}
