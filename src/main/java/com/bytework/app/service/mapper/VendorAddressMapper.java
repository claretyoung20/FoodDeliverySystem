package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.VendorAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VendorAddress} and its DTO {@link VendorAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface VendorAddressMapper extends EntityMapper<VendorAddressDTO, VendorAddress> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    VendorAddressDTO toDto(VendorAddress vendorAddress);

    @Mapping(source = "userId", target = "user")
    VendorAddress toEntity(VendorAddressDTO vendorAddressDTO);

    default VendorAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        VendorAddress vendorAddress = new VendorAddress();
        vendorAddress.setId(id);
        return vendorAddress;
    }
}
