package com.bytework.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytework.app.web.rest.TestUtil;

public class VendorAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorAddress.class);
        VendorAddress vendorAddress1 = new VendorAddress();
        vendorAddress1.setId(1L);
        VendorAddress vendorAddress2 = new VendorAddress();
        vendorAddress2.setId(vendorAddress1.getId());
        assertThat(vendorAddress1).isEqualTo(vendorAddress2);
        vendorAddress2.setId(2L);
        assertThat(vendorAddress1).isNotEqualTo(vendorAddress2);
        vendorAddress1.setId(null);
        assertThat(vendorAddress1).isNotEqualTo(vendorAddress2);
    }
}
