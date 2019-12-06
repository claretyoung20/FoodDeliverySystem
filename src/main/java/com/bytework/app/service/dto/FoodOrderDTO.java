package com.bytework.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytework.app.domain.FoodOrder} entity.
 */
public class FoodOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Double baseTotal;

    @NotNull
    @DecimalMin(value = "1")
    private Double finalTotal;

    @NotNull
    private Long vendorId;

    private Instant dateCreated;

    private Instant dateUpdated;


    private Long userId;

    private String userLogin;

    private Long orderStatusId;

    private String orderStatusStatus;

    private Long paymentMethodId;

    private String paymentMethodMethod;

    private Long deliveryTypeId;

    private String deliveryTypeDType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(Double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusStatus() {
        return orderStatusStatus;
    }

    public void setOrderStatusStatus(String orderStatusStatus) {
        this.orderStatusStatus = orderStatusStatus;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodMethod() {
        return paymentMethodMethod;
    }

    public void setPaymentMethodMethod(String paymentMethodMethod) {
        this.paymentMethodMethod = paymentMethodMethod;
    }

    public Long getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(Long deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    public String getDeliveryTypeDType() {
        return deliveryTypeDType;
    }

    public void setDeliveryTypeDType(String deliveryTypeDType) {
        this.deliveryTypeDType = deliveryTypeDType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoodOrderDTO foodOrderDTO = (FoodOrderDTO) o;
        if (foodOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodOrderDTO{" +
            "id=" + getId() +
            ", baseTotal=" + getBaseTotal() +
            ", finalTotal=" + getFinalTotal() +
            ", vendorId=" + getVendorId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", orderStatus=" + getOrderStatusId() +
            ", orderStatus='" + getOrderStatusStatus() + "'" +
            ", paymentMethod=" + getPaymentMethodId() +
            ", paymentMethod='" + getPaymentMethodMethod() + "'" +
            ", deliveryType=" + getDeliveryTypeId() +
            ", deliveryType='" + getDeliveryTypeDType() + "'" +
            "}";
    }
}
