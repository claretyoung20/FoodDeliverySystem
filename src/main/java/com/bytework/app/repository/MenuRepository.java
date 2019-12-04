package com.bytework.app.repository;
import com.bytework.app.domain.Menu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select menu from Menu menu where menu.user.login = ?#{principal.username}")
    List<Menu> findByUserIsCurrentUser();

}
