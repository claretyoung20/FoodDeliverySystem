package com.bytework.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class CardInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardInfoDTO.class);
        CardInfoDTO cardInfoDTO1 = new CardInfoDTO();
        cardInfoDTO1.setId(1L);
        CardInfoDTO cardInfoDTO2 = new CardInfoDTO();
        assertThat(cardInfoDTO1).isNotEqualTo(cardInfoDTO2);
        cardInfoDTO2.setId(cardInfoDTO1.getId());
        assertThat(cardInfoDTO1).isEqualTo(cardInfoDTO2);
        cardInfoDTO2.setId(2L);
        assertThat(cardInfoDTO1).isNotEqualTo(cardInfoDTO2);
        cardInfoDTO1.setId(null);
        assertThat(cardInfoDTO1).isNotEqualTo(cardInfoDTO2);
    }
}
