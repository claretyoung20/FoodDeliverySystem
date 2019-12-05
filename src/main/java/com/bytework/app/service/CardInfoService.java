package com.bytework.app.service;

import com.bytework.app.service.dto.CardInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytework.app.domain.CardInfo}.
 */
public interface CardInfoService {

    /**
     * Save a cardInfo.
     *
     * @param cardInfoDTO the entity to save.
     * @return the persisted entity.
     */
    CardInfoDTO save(CardInfoDTO cardInfoDTO);

    /**
     * Get all the cardInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CardInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cardInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CardInfoDTO> findOne(Long id);

    /**
     * Delete the "id" cardInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
