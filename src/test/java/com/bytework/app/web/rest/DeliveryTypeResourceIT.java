package com.bytework.app.web.rest;

import com.bytework.app.ByteworkApp;
import com.bytework.app.domain.DeliveryType;
import com.bytework.app.repository.DeliveryTypeRepository;
import com.bytework.app.service.DeliveryTypeService;
import com.bytework.app.service.dto.DeliveryTypeDTO;
import com.bytework.app.service.mapper.DeliveryTypeMapper;
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
 * Integration tests for the {@link DeliveryTypeResource} REST controller.
 */
@SpringBootTest(classes = ByteworkApp.class)
public class DeliveryTypeResourceIT {

    private static final String DEFAULT_D_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_D_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    private DeliveryTypeMapper deliveryTypeMapper;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

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

    private MockMvc restDeliveryTypeMockMvc;

    private DeliveryType deliveryType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryTypeResource deliveryTypeResource = new DeliveryTypeResource(deliveryTypeService);
        this.restDeliveryTypeMockMvc = MockMvcBuilders.standaloneSetup(deliveryTypeResource)
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
    public static DeliveryType createEntity(EntityManager em) {
        DeliveryType deliveryType = new DeliveryType()
            .dType(DEFAULT_D_TYPE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return deliveryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryType createUpdatedEntity(EntityManager em) {
        DeliveryType deliveryType = new DeliveryType()
            .dType(UPDATED_D_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return deliveryType;
    }

    @BeforeEach
    public void initTest() {
        deliveryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryType() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getdType()).isEqualTo(DEFAULT_D_TYPE);
        assertThat(testDeliveryType.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDeliveryType.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createDeliveryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType with an existing ID
        deliveryType.setId(1L);
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkdTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryTypeRepository.findAll().size();
        // set the field null
        deliveryType.setdType(null);

        // Create the DeliveryType, which fails.
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);

        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliveryTypes() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get all the deliveryTypeList
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].dType").value(hasItem(DEFAULT_D_TYPE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", deliveryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryType.getId().intValue()))
            .andExpect(jsonPath("$.dType").value(DEFAULT_D_TYPE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryType() throws Exception {
        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Update the deliveryType
        DeliveryType updatedDeliveryType = deliveryTypeRepository.findById(deliveryType.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryType are not directly saved in db
        em.detach(updatedDeliveryType);
        updatedDeliveryType
            .dType(UPDATED_D_TYPE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(updatedDeliveryType);

        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getdType()).isEqualTo(UPDATED_D_TYPE);
        assertThat(testDeliveryType.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDeliveryType.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryType() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeDelete = deliveryTypeRepository.findAll().size();

        // Delete the deliveryType
        restDeliveryTypeMockMvc.perform(delete("/api/delivery-types/{id}", deliveryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
