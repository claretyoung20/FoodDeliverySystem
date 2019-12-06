package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.DeliveryTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryType} and its DTO {@link DeliveryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeliveryTypeMapper extends EntityMapper<DeliveryTypeDTO, DeliveryType> {



    default DeliveryType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryType deliveryType = new DeliveryType();
        deliveryType.setId(id);
        return deliveryType;
    }
}
