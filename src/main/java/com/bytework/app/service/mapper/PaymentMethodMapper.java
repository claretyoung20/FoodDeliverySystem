package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.PaymentMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentMethod} and its DTO {@link PaymentMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMethodMapper extends EntityMapper<PaymentMethodDTO, PaymentMethod> {



    default PaymentMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(id);
        return paymentMethod;
    }
}
