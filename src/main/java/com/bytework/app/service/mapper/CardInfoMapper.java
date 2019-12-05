package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.CardInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CardInfo} and its DTO {@link CardInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CardInfoMapper extends EntityMapper<CardInfoDTO, CardInfo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CardInfoDTO toDto(CardInfo cardInfo);

    @Mapping(source = "userId", target = "user")
    CardInfo toEntity(CardInfoDTO cardInfoDTO);

    default CardInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CardInfo cardInfo = new CardInfo();
        cardInfo.setId(id);
        return cardInfo;
    }
}
