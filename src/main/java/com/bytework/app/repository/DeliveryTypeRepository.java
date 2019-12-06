package com.bytework.app.repository;
import com.bytework.app.domain.DeliveryType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

}
