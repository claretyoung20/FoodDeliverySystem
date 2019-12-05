package com.bytework.app.web.rest;

import com.bytework.app.service.CardInfoService;
import com.bytework.app.web.rest.errors.BadRequestAlertException;
import com.bytework.app.service.dto.CardInfoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bytework.app.domain.CardInfo}.
 */
@RestController
@RequestMapping("/api")
public class CardInfoResource {

    private final Logger log = LoggerFactory.getLogger(CardInfoResource.class);

    private static final String ENTITY_NAME = "cardInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardInfoService cardInfoService;

    public CardInfoResource(CardInfoService cardInfoService) {
        this.cardInfoService = cardInfoService;
    }

    /**
     * {@code POST  /card-infos} : Create a new cardInfo.
     *
     * @param cardInfoDTO the cardInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardInfoDTO, or with status {@code 400 (Bad Request)} if the cardInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/card-infos")
    public ResponseEntity<CardInfoDTO> createCardInfo(@Valid @RequestBody CardInfoDTO cardInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CardInfo : {}", cardInfoDTO);
        if (cardInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cardInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardInfoDTO result = cardInfoService.save(cardInfoDTO);
        return ResponseEntity.created(new URI("/api/card-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /card-infos} : Updates an existing cardInfo.
     *
     * @param cardInfoDTO the cardInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardInfoDTO,
     * or with status {@code 400 (Bad Request)} if the cardInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/card-infos")
    public ResponseEntity<CardInfoDTO> updateCardInfo(@Valid @RequestBody CardInfoDTO cardInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CardInfo : {}", cardInfoDTO);
        if (cardInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CardInfoDTO result = cardInfoService.save(cardInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cardInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /card-infos} : get all the cardInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardInfos in body.
     */
    @GetMapping("/card-infos")
    public ResponseEntity<List<CardInfoDTO>> getAllCardInfos(Pageable pageable) {
        log.debug("REST request to get a page of CardInfos");
        Page<CardInfoDTO> page = cardInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /card-infos/:id} : get the "id" cardInfo.
     *
     * @param id the id of the cardInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/card-infos/{id}")
    public ResponseEntity<CardInfoDTO> getCardInfo(@PathVariable Long id) {
        log.debug("REST request to get CardInfo : {}", id);
        Optional<CardInfoDTO> cardInfoDTO = cardInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardInfoDTO);
    }

    /**
     * {@code DELETE  /card-infos/:id} : delete the "id" cardInfo.
     *
     * @param id the id of the cardInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/card-infos/{id}")
    public ResponseEntity<Void> deleteCardInfo(@PathVariable Long id) {
        log.debug("REST request to delete CardInfo : {}", id);
        cardInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
