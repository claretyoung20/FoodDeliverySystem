package com.bytework.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderStatusMapperTest {

    private OrderStatusMapper orderStatusMapper;

    @BeforeEach
    public void setUp() {
        orderStatusMapper = new OrderStatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(orderStatusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderStatusMapper.fromId(null)).isNull();
    }
}
