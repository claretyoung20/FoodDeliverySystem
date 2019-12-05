package com.bytework.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CardInfoMapperTest {

    private CardInfoMapper cardInfoMapper;

    @BeforeEach
    public void setUp() {
        cardInfoMapper = new CardInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cardInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cardInfoMapper.fromId(null)).isNull();
    }
}
