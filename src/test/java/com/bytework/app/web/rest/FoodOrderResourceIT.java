package com.bytework.app.web.rest;

import com.bytework.app.ByteworkApp;
import com.bytework.app.domain.FoodOrder;
import com.bytework.app.domain.User;
import com.bytework.app.domain.PaymentMethod;
import com.bytework.app.repository.FoodOrderRepository;
import com.bytework.app.service.FoodOrderService;
import com.bytework.app.service.dto.FoodOrderDTO;
import com.bytework.app.service.mapper.FoodOrderMapper;
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
 * Integration tests for the {@link FoodOrderResource} REST controller.
 */
@SpringBootTest(classes = ByteworkApp.class)
public class FoodOrderResourceIT {

    private static final Double DEFAULT_BASE_TOTAL = 1D;
    private static final Double UPDATED_BASE_TOTAL = 2D;

    private static final Double DEFAULT_FINAL_TOTAL = 1D;
    private static final Double UPDATED_FINAL_TOTAL = 2D;

    private static final Long DEFAULT_VENDOR_ID = 1L;
    private static final Long UPDATED_VENDOR_ID = 2L;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Autowired
    private FoodOrderMapper foodOrderMapper;

    @Autowired
    private FoodOrderService foodOrderService;

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

    private MockMvc restFoodOrderMockMvc;

    private FoodOrder foodOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderService);
        this.restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
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
    public static FoodOrder createEntity(EntityManager em) {
        FoodOrder foodOrder = new FoodOrder()
            .baseTotal(DEFAULT_BASE_TOTAL)
            .finalTotal(DEFAULT_FINAL_TOTAL)
            .vendorId(DEFAULT_VENDOR_ID)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        foodOrder.setUser(user);
        // Add required entity
        PaymentMethod paymentMethod;
        if (TestUtil.findAll(em, PaymentMethod.class).isEmpty()) {
            paymentMethod = PaymentMethodResourceIT.createEntity(em);
            em.persist(paymentMethod);
            em.flush();
        } else {
            paymentMethod = TestUtil.findAll(em, PaymentMethod.class).get(0);
        }
        foodOrder.setPaymentMethod(paymentMethod);
        return foodOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodOrder createUpdatedEntity(EntityManager em) {
        FoodOrder foodOrder = new FoodOrder()
            .baseTotal(UPDATED_BASE_TOTAL)
            .finalTotal(UPDATED_FINAL_TOTAL)
            .vendorId(UPDATED_VENDOR_ID)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        foodOrder.setUser(user);
        // Add required entity
        PaymentMethod paymentMethod;
        if (TestUtil.findAll(em, PaymentMethod.class).isEmpty()) {
            paymentMethod = PaymentMethodResourceIT.createUpdatedEntity(em);
            em.persist(paymentMethod);
            em.flush();
        } else {
            paymentMethod = TestUtil.findAll(em, PaymentMethod.class).get(0);
        }
        foodOrder.setPaymentMethod(paymentMethod);
        return foodOrder;
    }

    @BeforeEach
    public void initTest() {
        foodOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodOrder() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate + 1);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getBaseTotal()).isEqualTo(DEFAULT_BASE_TOTAL);
        assertThat(testFoodOrder.getFinalTotal()).isEqualTo(DEFAULT_FINAL_TOTAL);
        assertThat(testFoodOrder.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testFoodOrder.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFoodOrder.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createFoodOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder with an existing ID
        foodOrder.setId(1L);
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBaseTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setBaseTotal(null);

        // Create the FoodOrder, which fails.
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinalTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setFinalTotal(null);

        // Create the FoodOrder, which fails.
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setVendorId(null);

        // Create the FoodOrder, which fails.
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodOrders() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get all the foodOrderList
        restFoodOrderMockMvc.perform(get("/api/food-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].baseTotal").value(hasItem(DEFAULT_BASE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].finalTotal").value(hasItem(DEFAULT_FINAL_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", foodOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodOrder.getId().intValue()))
            .andExpect(jsonPath("$.baseTotal").value(DEFAULT_BASE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.finalTotal").value(DEFAULT_FINAL_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodOrder() throws Exception {
        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Update the foodOrder
        FoodOrder updatedFoodOrder = foodOrderRepository.findById(foodOrder.getId()).get();
        // Disconnect from session so that the updates on updatedFoodOrder are not directly saved in db
        em.detach(updatedFoodOrder);
        updatedFoodOrder
            .baseTotal(UPDATED_BASE_TOTAL)
            .finalTotal(UPDATED_FINAL_TOTAL)
            .vendorId(UPDATED_VENDOR_ID)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(updatedFoodOrder);

        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isOk());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getBaseTotal()).isEqualTo(UPDATED_BASE_TOTAL);
        assertThat(testFoodOrder.getFinalTotal()).isEqualTo(UPDATED_FINAL_TOTAL);
        assertThat(testFoodOrder.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testFoodOrder.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFoodOrder.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodOrder() throws Exception {
        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Create the FoodOrder
        FoodOrderDTO foodOrderDTO = foodOrderMapper.toDto(foodOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeDelete = foodOrderRepository.findAll().size();

        // Delete the foodOrder
        restFoodOrderMockMvc.perform(delete("/api/food-orders/{id}", foodOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
