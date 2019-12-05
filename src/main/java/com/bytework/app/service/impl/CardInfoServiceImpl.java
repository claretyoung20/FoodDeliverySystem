package com.bytework.app.service.impl;

import com.bytework.app.service.CardInfoService;
import com.bytework.app.domain.CardInfo;
import com.bytework.app.repository.CardInfoRepository;
import com.bytework.app.service.dto.CardInfoDTO;
import com.bytework.app.service.mapper.CardInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CardInfo}.
 */
@Service
@Transactional
public class CardInfoServiceImpl implements CardInfoService {

    private final Logger log = LoggerFactory.getLogger(CardInfoServiceImpl.class);

    private final CardInfoRepository cardInfoRepository;

    private final CardInfoMapper cardInfoMapper;

    public CardInfoServiceImpl(CardInfoRepository cardInfoRepository, CardInfoMapper cardInfoMapper) {
        this.cardInfoRepository = cardInfoRepository;
        this.cardInfoMapper = cardInfoMapper;
    }

    /**
     * Save a cardInfo.
     *
     * @param cardInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CardInfoDTO save(CardInfoDTO cardInfoDTO) {
        log.debug("Request to save CardInfo : {}", cardInfoDTO);
        CardInfo cardInfo = cardInfoMapper.toEntity(cardInfoDTO);
        cardInfo = cardInfoRepository.save(cardInfo);
        return cardInfoMapper.toDto(cardInfo);
    }

    /**
     * Get all the cardInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CardInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CardInfos");
        return cardInfoRepository.findAll(pageable)
            .map(cardInfoMapper::toDto);
    }


    /**
     * Get one cardInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CardInfoDTO> findOne(Long id) {
        log.debug("Request to get CardInfo : {}", id);
        return cardInfoRepository.findById(id)
            .map(cardInfoMapper::toDto);
    }

    /**
     * Delete the cardInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CardInfo : {}", id);
        cardInfoRepository.deleteById(id);
    }
}
