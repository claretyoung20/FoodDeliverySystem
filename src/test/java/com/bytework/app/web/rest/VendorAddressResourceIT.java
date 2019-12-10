package com.bytework.app.web.rest;

import com.bytework.app.ByteworkApp;
import com.bytework.app.domain.VendorAddress;
import com.bytework.app.domain.User;
import com.bytework.app.repository.VendorAddressRepository;
import com.bytework.app.service.VendorAddressService;
import com.bytework.app.service.dto.VendorAddressDTO;
import com.bytework.app.service.mapper.VendorAddressMapper;
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
 * Integration tests for the {@link VendorAddressResource} REST controller.
 */
@SpringBootTest(classes = ByteworkApp.class)
public class VendorAddressResourceIT {

    private static final String DEFAULT_FULL_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_FULL_ADRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_V_LAT = 1D;
    private static final Double UPDATED_V_LAT = 2D;

    private static final Double DEFAULT_V_LNG = 1D;
    private static final Double UPDATED_V_LNG = 2D;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VendorAddressRepository vendorAddressRepository;

    @Autowired
    private VendorAddressMapper vendorAddressMapper;

    @Autowired
    private VendorAddressService vendorAddressService;

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

    private MockMvc restVendorAddressMockMvc;

    private VendorAddress vendorAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorAddressResource vendorAddressResource = new VendorAddressResource(vendorAddressService);
        this.restVendorAddressMockMvc = MockMvcBuilders.standaloneSetup(vendorAddressResource)
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
    public static VendorAddress createEntity(EntityManager em) {
        VendorAddress vendorAddress = new VendorAddress()
            .fullAdress(DEFAULT_FULL_ADRESS)
            .vLat(DEFAULT_V_LAT)
            .vLng(DEFAULT_V_LNG)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vendorAddress.setUser(user);
        return vendorAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendorAddress createUpdatedEntity(EntityManager em) {
        VendorAddress vendorAddress = new VendorAddress()
            .fullAdress(UPDATED_FULL_ADRESS)
            .vLat(UPDATED_V_LAT)
            .vLng(UPDATED_V_LNG)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        vendorAddress.setUser(user);
        return vendorAddress;
    }

    @BeforeEach
    public void initTest() {
        vendorAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendorAddress() throws Exception {
        int databaseSizeBeforeCreate = vendorAddressRepository.findAll().size();

        // Create the VendorAddress
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);
        restVendorAddressMockMvc.perform(post("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the VendorAddress in the database
        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeCreate + 1);
        VendorAddress testVendorAddress = vendorAddressList.get(vendorAddressList.size() - 1);
        assertThat(testVendorAddress.getFullAdress()).isEqualTo(DEFAULT_FULL_ADRESS);
        assertThat(testVendorAddress.getvLat()).isEqualTo(DEFAULT_V_LAT);
        assertThat(testVendorAddress.getvLng()).isEqualTo(DEFAULT_V_LNG);
        assertThat(testVendorAddress.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testVendorAddress.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createVendorAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorAddressRepository.findAll().size();

        // Create the VendorAddress with an existing ID
        vendorAddress.setId(1L);
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorAddressMockMvc.perform(post("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorAddress in the database
        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullAdressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorAddressRepository.findAll().size();
        // set the field null
        vendorAddress.setFullAdress(null);

        // Create the VendorAddress, which fails.
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);

        restVendorAddressMockMvc.perform(post("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isBadRequest());

        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkvLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorAddressRepository.findAll().size();
        // set the field null
        vendorAddress.setvLat(null);

        // Create the VendorAddress, which fails.
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);

        restVendorAddressMockMvc.perform(post("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isBadRequest());

        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkvLngIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorAddressRepository.findAll().size();
        // set the field null
        vendorAddress.setvLng(null);

        // Create the VendorAddress, which fails.
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);

        restVendorAddressMockMvc.perform(post("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isBadRequest());

        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendorAddresses() throws Exception {
        // Initialize the database
        vendorAddressRepository.saveAndFlush(vendorAddress);

        // Get all the vendorAddressList
        restVendorAddressMockMvc.perform(get("/api/vendor-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullAdress").value(hasItem(DEFAULT_FULL_ADRESS)))
            .andExpect(jsonPath("$.[*].vLat").value(hasItem(DEFAULT_V_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].vLng").value(hasItem(DEFAULT_V_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getVendorAddress() throws Exception {
        // Initialize the database
        vendorAddressRepository.saveAndFlush(vendorAddress);

        // Get the vendorAddress
        restVendorAddressMockMvc.perform(get("/api/vendor-addresses/{id}", vendorAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendorAddress.getId().intValue()))
            .andExpect(jsonPath("$.fullAdress").value(DEFAULT_FULL_ADRESS))
            .andExpect(jsonPath("$.vLat").value(DEFAULT_V_LAT.doubleValue()))
            .andExpect(jsonPath("$.vLng").value(DEFAULT_V_LNG.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendorAddress() throws Exception {
        // Get the vendorAddress
        restVendorAddressMockMvc.perform(get("/api/vendor-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendorAddress() throws Exception {
        // Initialize the database
        vendorAddressRepository.saveAndFlush(vendorAddress);

        int databaseSizeBeforeUpdate = vendorAddressRepository.findAll().size();

        // Update the vendorAddress
        VendorAddress updatedVendorAddress = vendorAddressRepository.findById(vendorAddress.getId()).get();
        // Disconnect from session so that the updates on updatedVendorAddress are not directly saved in db
        em.detach(updatedVendorAddress);
        updatedVendorAddress
            .fullAdress(UPDATED_FULL_ADRESS)
            .vLat(UPDATED_V_LAT)
            .vLng(UPDATED_V_LNG)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(updatedVendorAddress);

        restVendorAddressMockMvc.perform(put("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isOk());

        // Validate the VendorAddress in the database
        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeUpdate);
        VendorAddress testVendorAddress = vendorAddressList.get(vendorAddressList.size() - 1);
        assertThat(testVendorAddress.getFullAdress()).isEqualTo(UPDATED_FULL_ADRESS);
        assertThat(testVendorAddress.getvLat()).isEqualTo(UPDATED_V_LAT);
        assertThat(testVendorAddress.getvLng()).isEqualTo(UPDATED_V_LNG);
        assertThat(testVendorAddress.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testVendorAddress.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingVendorAddress() throws Exception {
        int databaseSizeBeforeUpdate = vendorAddressRepository.findAll().size();

        // Create the VendorAddress
        VendorAddressDTO vendorAddressDTO = vendorAddressMapper.toDto(vendorAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorAddressMockMvc.perform(put("/api/vendor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorAddress in the database
        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVendorAddress() throws Exception {
        // Initialize the database
        vendorAddressRepository.saveAndFlush(vendorAddress);

        int databaseSizeBeforeDelete = vendorAddressRepository.findAll().size();

        // Delete the vendorAddress
        restVendorAddressMockMvc.perform(delete("/api/vendor-addresses/{id}", vendorAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VendorAddress> vendorAddressList = vendorAddressRepository.findAll();
        assertThat(vendorAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
