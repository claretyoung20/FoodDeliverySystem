package com.bytework.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class FoodOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodOrder.class);
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(1L);
        FoodOrder foodOrder2 = new FoodOrder();
        foodOrder2.setId(foodOrder1.getId());
        assertThat(foodOrder1).isEqualTo(foodOrder2);
        foodOrder2.setId(2L);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
        foodOrder1.setId(null);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
    }
}
