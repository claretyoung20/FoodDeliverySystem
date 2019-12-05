package com.bytework.app.repository;
import com.bytework.app.domain.FoodOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FoodOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    @Query("select foodOrder from FoodOrder foodOrder where foodOrder.user.login = ?#{principal.username}")
    List<FoodOrder> findByUserIsCurrentUser();

}
