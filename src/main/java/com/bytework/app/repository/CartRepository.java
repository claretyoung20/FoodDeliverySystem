package com.bytework.app.repository;
import com.bytework.app.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select cart from Cart cart where cart.user.login = ?#{principal.username}")
    List<Cart> findByUserIsCurrentUser();

    Page<Cart> findAllByUserId(Long id, Pageable pageable);

}
