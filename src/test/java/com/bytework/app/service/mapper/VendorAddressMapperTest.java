package com.bytework.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VendorAddressMapperTest {

    private VendorAddressMapper vendorAddressMapper;

    @BeforeEach
    public void setUp() {
        vendorAddressMapper = new VendorAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(vendorAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vendorAddressMapper.fromId(null)).isNull();
    }
}
