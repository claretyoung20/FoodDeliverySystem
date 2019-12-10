package com.bytework.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytework.app.domain.VendorAddress} entity.
 */
public class VendorAddressDTO implements Serializable {

    private Long id;

    @NotNull
    private String fullAdress;

    @NotNull
    private Double vLat;

    @NotNull
    private Double vLng;

    private Instant dateCreated;

    private Instant dateUpdated;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public Double getvLat() {
        return vLat;
    }

    public void setvLat(Double vLat) {
        this.vLat = vLat;
    }

    public Double getvLng() {
        return vLng;
    }

    public void setvLng(Double vLng) {
        this.vLng = vLng;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendorAddressDTO vendorAddressDTO = (VendorAddressDTO) o;
        if (vendorAddressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendorAddressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendorAddressDTO{" +
            "id=" + getId() +
            ", fullAdress='" + getFullAdress() + "'" +
            ", vLat=" + getvLat() +
            ", vLng=" + getvLng() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
