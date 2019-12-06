package com.bytework.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class DeliveryTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryTypeDTO.class);
        DeliveryTypeDTO deliveryTypeDTO1 = new DeliveryTypeDTO();
        deliveryTypeDTO1.setId(1L);
        DeliveryTypeDTO deliveryTypeDTO2 = new DeliveryTypeDTO();
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO2.setId(deliveryTypeDTO1.getId());
        assertThat(deliveryTypeDTO1).isEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO2.setId(2L);
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO1.setId(null);
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
    }
}
