package com.bytework.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FoodOrderMapperTest {

    private FoodOrderMapper foodOrderMapper;

    @BeforeEach
    public void setUp() {
        foodOrderMapper = new FoodOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(foodOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(foodOrderMapper.fromId(null)).isNull();
    }
}
