package com.bytework.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class OrderStatusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderStatusDTO.class);
        OrderStatusDTO orderStatusDTO1 = new OrderStatusDTO();
        orderStatusDTO1.setId(1L);
        OrderStatusDTO orderStatusDTO2 = new OrderStatusDTO();
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
        orderStatusDTO2.setId(orderStatusDTO1.getId());
        assertThat(orderStatusDTO1).isEqualTo(orderStatusDTO2);
        orderStatusDTO2.setId(2L);
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
        orderStatusDTO1.setId(null);
        assertThat(orderStatusDTO1).isNotEqualTo(orderStatusDTO2);
    }
}
