package com.bytework.app.repository;
import com.bytework.app.domain.CardInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CardInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    @Query("select cardInfo from CardInfo cardInfo where cardInfo.user.login = ?#{principal.username}")
    List<CardInfo> findByUserIsCurrentUser();

}
