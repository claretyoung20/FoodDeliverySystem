package com.bytework.app.web.rest;

import com.bytework.app.ByteworkApp;
import com.bytework.app.domain.OrderStatus;
import com.bytework.app.repository.OrderStatusRepository;
import com.bytework.app.service.OrderStatusService;
import com.bytework.app.service.dto.OrderStatusDTO;
import com.bytework.app.service.mapper.OrderStatusMapper;
import com.bytework.app.web.rest.errors.ExceptionTranslator;
import com.bytework.app.service.dto.OrderStatusCriteria;
import com.bytework.app.service.OrderStatusQueryService;

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
 * Integration tests for the {@link OrderStatusResource} REST controller.
 */
@SpringBootTest(classes = ByteworkApp.class)
public class OrderStatusResourceIT {

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderStatusQueryService orderStatusQueryService;

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

    private MockMvc restOrderStatusMockMvc;

    private OrderStatus orderStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderStatusResource orderStatusResource = new OrderStatusResource(orderStatusService, orderStatusQueryService);
        this.restOrderStatusMockMvc = MockMvcBuilders.standaloneSetup(orderStatusResource)
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
    public static OrderStatus createEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus()
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .status(DEFAULT_STATUS);
        return orderStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatus createUpdatedEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus()
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .status(UPDATED_STATUS);
        return orderStatus;
    }

    @BeforeEach
    public void initTest() {
        orderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testOrderStatus.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testOrderStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOrderStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // Create the OrderStatus with an existing ID
        orderStatus.setId(1L);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderStatusRepository.findAll().size();
        // set the field null
        orderStatus.setStatus(null);

        // Create the OrderStatus, which fails.
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isBadRequest());

        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderStatuses() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", orderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderStatus.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getOrderStatusesByIdFiltering() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        Long id = orderStatus.getId();

        defaultOrderStatusShouldBeFound("id.equals=" + id);
        defaultOrderStatusShouldNotBeFound("id.notEquals=" + id);

        defaultOrderStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderStatusesByDateCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateCreated equals to DEFAULT_DATE_CREATED
        defaultOrderStatusShouldBeFound("dateCreated.equals=" + DEFAULT_DATE_CREATED);

        // Get all the orderStatusList where dateCreated equals to UPDATED_DATE_CREATED
        defaultOrderStatusShouldNotBeFound("dateCreated.equals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateCreated not equals to DEFAULT_DATE_CREATED
        defaultOrderStatusShouldNotBeFound("dateCreated.notEquals=" + DEFAULT_DATE_CREATED);

        // Get all the orderStatusList where dateCreated not equals to UPDATED_DATE_CREATED
        defaultOrderStatusShouldBeFound("dateCreated.notEquals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateCreated in DEFAULT_DATE_CREATED or UPDATED_DATE_CREATED
        defaultOrderStatusShouldBeFound("dateCreated.in=" + DEFAULT_DATE_CREATED + "," + UPDATED_DATE_CREATED);

        // Get all the orderStatusList where dateCreated equals to UPDATED_DATE_CREATED
        defaultOrderStatusShouldNotBeFound("dateCreated.in=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateCreated is not null
        defaultOrderStatusShouldBeFound("dateCreated.specified=true");

        // Get all the orderStatusList where dateCreated is null
        defaultOrderStatusShouldNotBeFound("dateCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateUpdated equals to DEFAULT_DATE_UPDATED
        defaultOrderStatusShouldBeFound("dateUpdated.equals=" + DEFAULT_DATE_UPDATED);

        // Get all the orderStatusList where dateUpdated equals to UPDATED_DATE_UPDATED
        defaultOrderStatusShouldNotBeFound("dateUpdated.equals=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateUpdated not equals to DEFAULT_DATE_UPDATED
        defaultOrderStatusShouldNotBeFound("dateUpdated.notEquals=" + DEFAULT_DATE_UPDATED);

        // Get all the orderStatusList where dateUpdated not equals to UPDATED_DATE_UPDATED
        defaultOrderStatusShouldBeFound("dateUpdated.notEquals=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateUpdated in DEFAULT_DATE_UPDATED or UPDATED_DATE_UPDATED
        defaultOrderStatusShouldBeFound("dateUpdated.in=" + DEFAULT_DATE_UPDATED + "," + UPDATED_DATE_UPDATED);

        // Get all the orderStatusList where dateUpdated equals to UPDATED_DATE_UPDATED
        defaultOrderStatusShouldNotBeFound("dateUpdated.in=" + UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByDateUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where dateUpdated is not null
        defaultOrderStatusShouldBeFound("dateUpdated.specified=true");

        // Get all the orderStatusList where dateUpdated is null
        defaultOrderStatusShouldNotBeFound("dateUpdated.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status equals to DEFAULT_STATUS
        defaultOrderStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the orderStatusList where status equals to UPDATED_STATUS
        defaultOrderStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status not equals to DEFAULT_STATUS
        defaultOrderStatusShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the orderStatusList where status not equals to UPDATED_STATUS
        defaultOrderStatusShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrderStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the orderStatusList where status equals to UPDATED_STATUS
        defaultOrderStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status is not null
        defaultOrderStatusShouldBeFound("status.specified=true");

        // Get all the orderStatusList where status is null
        defaultOrderStatusShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status contains DEFAULT_STATUS
        defaultOrderStatusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the orderStatusList where status contains UPDATED_STATUS
        defaultOrderStatusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where status does not contain DEFAULT_STATUS
        defaultOrderStatusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the orderStatusList where status does not contain UPDATED_STATUS
        defaultOrderStatusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderStatusShouldBeFound(String filter) throws Exception {
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restOrderStatusMockMvc.perform(get("/api/order-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderStatusShouldNotBeFound(String filter) throws Exception {
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderStatusMockMvc.perform(get("/api/order-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrderStatus() throws Exception {
        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus
        OrderStatus updatedOrderStatus = orderStatusRepository.findById(orderStatus.getId()).get();
        // Disconnect from session so that the updates on updatedOrderStatus are not directly saved in db
        em.detach(updatedOrderStatus);
        updatedOrderStatus
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .status(UPDATED_STATUS);
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(updatedOrderStatus);

        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testOrderStatus.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testOrderStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Create the OrderStatus
        OrderStatusDTO orderStatusDTO = orderStatusMapper.toDto(orderStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        int databaseSizeBeforeDelete = orderStatusRepository.findAll().size();

        // Delete the orderStatus
        restOrderStatusMockMvc.perform(delete("/api/order-statuses/{id}", orderStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
