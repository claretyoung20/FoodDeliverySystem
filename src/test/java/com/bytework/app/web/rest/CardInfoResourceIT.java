package com.bytework.app.web.rest;

import com.bytework.app.ByteworkApp;
import com.bytework.app.domain.CardInfo;
import com.bytework.app.domain.User;
import com.bytework.app.repository.CardInfoRepository;
import com.bytework.app.service.CardInfoService;
import com.bytework.app.service.dto.CardInfoDTO;
import com.bytework.app.service.mapper.CardInfoMapper;
import com.bytework.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.bytework.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CardInfoResource} REST controller.
 */
@SpringBootTest(classes = ByteworkApp.class)
public class CardInfoResourceIT {

    private static final String DEFAULT_NAME_ON_CARD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ON_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_CVV = "AAAAAAAAAA";
    private static final String UPDATED_CVV = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRE_DATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CardInfoRepository cardInfoRepository;

    @Autowired
    private CardInfoMapper cardInfoMapper;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCardInfoMockMvc;

    private CardInfo cardInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CardInfoResource cardInfoResource = new CardInfoResource(cardInfoService);
        this.restCardInfoMockMvc = MockMvcBuilders.standaloneSetup(cardInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardInfo createEntity(EntityManager em) {
        CardInfo cardInfo = new CardInfo()
            .nameOnCard(DEFAULT_NAME_ON_CARD)
            .cvv(DEFAULT_CVV)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expireDate(DEFAULT_EXPIRE_DATE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cardInfo.setUser(user);
        return cardInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CardInfo createUpdatedEntity(EntityManager em) {
        CardInfo cardInfo = new CardInfo()
            .nameOnCard(UPDATED_NAME_ON_CARD)
            .cvv(UPDATED_CVV)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expireDate(UPDATED_EXPIRE_DATE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        cardInfo.setUser(user);
        return cardInfo;
    }

    @BeforeEach
    public void initTest() {
        cardInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCardInfo() throws Exception {
        int databaseSizeBeforeCreate = cardInfoRepository.findAll().size();

        // Create the CardInfo
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);
        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CardInfo in the database
        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CardInfo testCardInfo = cardInfoList.get(cardInfoList.size() - 1);
        assertThat(testCardInfo.getNameOnCard()).isEqualTo(DEFAULT_NAME_ON_CARD);
        assertThat(testCardInfo.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testCardInfo.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testCardInfo.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testCardInfo.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCardInfo.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createCardInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardInfoRepository.findAll().size();

        // Create the CardInfo with an existing ID
        cardInfo.setId(1L);
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardInfo in the database
        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameOnCardIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardInfoRepository.findAll().size();
        // set the field null
        cardInfo.setNameOnCard(null);

        // Create the CardInfo, which fails.
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvvIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardInfoRepository.findAll().size();
        // set the field null
        cardInfo.setCvv(null);

        // Create the CardInfo, which fails.
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardInfoRepository.findAll().size();
        // set the field null
        cardInfo.setCardNumber(null);

        // Create the CardInfo, which fails.
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpireDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardInfoRepository.findAll().size();
        // set the field null
        cardInfo.setExpireDate(null);

        // Create the CardInfo, which fails.
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        restCardInfoMockMvc.perform(post("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCardInfos() throws Exception {
        // Initialize the database
        cardInfoRepository.saveAndFlush(cardInfo);

        // Get all the cardInfoList
        restCardInfoMockMvc.perform(get("/api/card-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOnCard").value(hasItem(DEFAULT_NAME_ON_CARD)))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].expireDate").value(hasItem(DEFAULT_EXPIRE_DATE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getCardInfo() throws Exception {
        // Initialize the database
        cardInfoRepository.saveAndFlush(cardInfo);

        // Get the cardInfo
        restCardInfoMockMvc.perform(get("/api/card-infos/{id}", cardInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cardInfo.getId().intValue()))
            .andExpect(jsonPath("$.nameOnCard").value(DEFAULT_NAME_ON_CARD))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER))
            .andExpect(jsonPath("$.expireDate").value(DEFAULT_EXPIRE_DATE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCardInfo() throws Exception {
        // Get the cardInfo
        restCardInfoMockMvc.perform(get("/api/card-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardInfo() throws Exception {
        // Initialize the database
        cardInfoRepository.saveAndFlush(cardInfo);

        int databaseSizeBeforeUpdate = cardInfoRepository.findAll().size();

        // Update the cardInfo
        CardInfo updatedCardInfo = cardInfoRepository.findById(cardInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCardInfo are not directly saved in db
        em.detach(updatedCardInfo);
        updatedCardInfo
            .nameOnCard(UPDATED_NAME_ON_CARD)
            .cvv(UPDATED_CVV)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expireDate(UPDATED_EXPIRE_DATE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(updatedCardInfo);

        restCardInfoMockMvc.perform(put("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CardInfo in the database
        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeUpdate);
        CardInfo testCardInfo = cardInfoList.get(cardInfoList.size() - 1);
        assertThat(testCardInfo.getNameOnCard()).isEqualTo(UPDATED_NAME_ON_CARD);
        assertThat(testCardInfo.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testCardInfo.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testCardInfo.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testCardInfo.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCardInfo.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingCardInfo() throws Exception {
        int databaseSizeBeforeUpdate = cardInfoRepository.findAll().size();

        // Create the CardInfo
        CardInfoDTO cardInfoDTO = cardInfoMapper.toDto(cardInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardInfoMockMvc.perform(put("/api/card-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CardInfo in the database
        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCardInfo() throws Exception {
        // Initialize the database
        cardInfoRepository.saveAndFlush(cardInfo);

        int databaseSizeBeforeDelete = cardInfoRepository.findAll().size();

        // Delete the cardInfo
        restCardInfoMockMvc.perform(delete("/api/card-infos/{id}", cardInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CardInfo> cardInfoList = cardInfoRepository.findAll();
        assertThat(cardInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
