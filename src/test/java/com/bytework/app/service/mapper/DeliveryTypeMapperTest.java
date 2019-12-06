package com.bytework.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DeliveryTypeMapperTest {

    private DeliveryTypeMapper deliveryTypeMapper;

    @BeforeEach
    public void setUp() {
        deliveryTypeMapper = new DeliveryTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(deliveryTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deliveryTypeMapper.fromId(null)).isNull();
    }
}
