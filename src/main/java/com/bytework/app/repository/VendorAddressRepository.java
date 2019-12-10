package com.bytework.app.repository;
import com.bytework.app.domain.VendorAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VendorAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorAddressRepository extends JpaRepository<VendorAddress, Long> {

    VendorAddress findByUserId(long id);

    Page<VendorAddress> findAllByUserId(Long id, Pageable pageable);
}
