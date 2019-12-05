package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.FoodOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodOrder} and its DTO {@link FoodOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderStatusMapper.class, PaymentMethodMapper.class})
public interface FoodOrderMapper extends EntityMapper<FoodOrderDTO, FoodOrder> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "orderStatus.id", target = "orderStatusId")
    @Mapping(source = "orderStatus.status", target = "orderStatusStatus")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "paymentMethod.method", target = "paymentMethodMethod")
    FoodOrderDTO toDto(FoodOrder foodOrder);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "orderStatusId", target = "orderStatus")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    FoodOrder toEntity(FoodOrderDTO foodOrderDTO);

    default FoodOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setId(id);
        return foodOrder;
    }
}
