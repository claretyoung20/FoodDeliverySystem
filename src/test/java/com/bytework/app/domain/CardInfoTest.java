package com.bytework.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class CardInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardInfo.class);
        CardInfo cardInfo1 = new CardInfo();
        cardInfo1.setId(1L);
        CardInfo cardInfo2 = new CardInfo();
        cardInfo2.setId(cardInfo1.getId());
        assertThat(cardInfo1).isEqualTo(cardInfo2);
        cardInfo2.setId(2L);
        assertThat(cardInfo1).isNotEqualTo(cardInfo2);
        cardInfo1.setId(null);
        assertThat(cardInfo1).isNotEqualTo(cardInfo2);
    }
}
