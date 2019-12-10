package com.bytework.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class VendorAddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorAddressDTO.class);
        VendorAddressDTO vendorAddressDTO1 = new VendorAddressDTO();
        vendorAddressDTO1.setId(1L);
        VendorAddressDTO vendorAddressDTO2 = new VendorAddressDTO();
        assertThat(vendorAddressDTO1).isNotEqualTo(vendorAddressDTO2);
        vendorAddressDTO2.setId(vendorAddressDTO1.getId());
        assertThat(vendorAddressDTO1).isEqualTo(vendorAddressDTO2);
        vendorAddressDTO2.setId(2L);
        assertThat(vendorAddressDTO1).isNotEqualTo(vendorAddressDTO2);
        vendorAddressDTO1.setId(null);
        assertThat(vendorAddressDTO1).isNotEqualTo(vendorAddressDTO2);
    }
}
