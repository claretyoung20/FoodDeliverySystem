package com.bytework.app.web.rest;

import com.bytework.app.service.CalculateLocation;
import com.bytework.app.service.VendorAddressService;
import com.bytework.app.web.rest.errors.BadRequestAlertException;
import com.bytework.app.service.dto.VendorAddressDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.bytework.app.domain.VendorAddress}.
 */
@RestController
@RequestMapping("/api")
public class VendorAddressResource {

    private final Logger log = LoggerFactory.getLogger(VendorAddressResource.class);

    private static final String ENTITY_NAME = "vendorAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendorAddressService vendorAddressService;

    @Autowired
    private CalculateLocation calculateLocation;

    public VendorAddressResource(VendorAddressService vendorAddressService) {
        this.vendorAddressService = vendorAddressService;
    }

    /**
     * {@code POST  /vendor-addresses} : Create a new vendorAddress.
     *
     * @param vendorAddressDTO the vendorAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendorAddressDTO, or with status {@code 400 (Bad Request)} if the vendorAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendor-addresses")
    public ResponseEntity<VendorAddressDTO> createVendorAddress(@Valid @RequestBody VendorAddressDTO vendorAddressDTO) throws URISyntaxException {
        log.debug("REST request to save VendorAddress : {}", vendorAddressDTO);
        if (vendorAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendorAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendorAddressDTO result = vendorAddressService.save(vendorAddressDTO);
        return ResponseEntity.created(new URI("/api/vendor-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendor-addresses} : Updates an existing vendorAddress.
     *
     * @param vendorAddressDTO the vendorAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorAddressDTO,
     * or with status {@code 400 (Bad Request)} if the vendorAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendorAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendor-addresses")
    public ResponseEntity<VendorAddressDTO> updateVendorAddress(@Valid @RequestBody VendorAddressDTO vendorAddressDTO) throws URISyntaxException {
        log.debug("REST request to update VendorAddress : {}", vendorAddressDTO);
        if (vendorAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendorAddressDTO result = vendorAddressService.save(vendorAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vendorAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vendor-addresses} : get all the vendorAddresses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendorAddresses in body.
     */
    @GetMapping("/vendor-addresses")
    public ResponseEntity<List<VendorAddressDTO>> getAllVendorAddresses(Pageable pageable) {
        log.debug("REST request to get a page of VendorAddresses");
        Page<VendorAddressDTO> page = vendorAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vendor-addresses/:id} : get the "id" vendorAddress.
     *
     * @param id the id of the vendorAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendorAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendor-addresses/{id}")
    public ResponseEntity<VendorAddressDTO> getVendorAddress(@PathVariable Long id) {
        log.debug("REST request to get VendorAddress : {}", id);
        Optional<VendorAddressDTO> vendorAddressDTO = vendorAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendorAddressDTO);
    }

    /**
     * {@code DELETE  /vendor-addresses/:id} : delete the "id" vendorAddress.
     *
     * @param id the id of the vendorAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendor-addresses/{id}")
    public ResponseEntity<Void> deleteVendorAddress(@PathVariable Long id) {
        log.debug("REST request to delete VendorAddress : {}", id);
        vendorAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/vendor-addresses/shippingFee/{id}")
    public ResponseEntity<Double> getShippingFee(@PathVariable Long id) {
        VendorAddressDTO  vendorAddressDTO = vendorAddressService.findByUserId(id);

        this.calculateLocation.setVendorLat(vendorAddressDTO.getvLat());
        this.calculateLocation.setVendorLong(vendorAddressDTO.getvLng());

        double shippingFee = this.calculateLocation.distanceInMeter();
        return ResponseEntity.ok().body(shippingFee);
    }

    @GetMapping("/vendor-addresses/vendor/{id}")
    public ResponseEntity<List<VendorAddressDTO>> getByAllUserId(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get vendor by user id: {}", id);
        Page<VendorAddressDTO> page = vendorAddressService.findAllByUserId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
