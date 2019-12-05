package com.bytework.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FoodOrder.
 */
@Entity
@Table(name = "food_order")
public class FoodOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "base_total", nullable = false)
    private Double baseTotal;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "final_total", nullable = false)
    private Double finalTotal;

    @NotNull
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("foodOrders")
    private User user;

    @ManyToOne
    private OrderStatus orderStatus;

    @ManyToOne(optional = false)
    @NotNull
    private PaymentMethod paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public FoodOrder baseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
        return this;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getFinalTotal() {
        return finalTotal;
    }

    public FoodOrder finalTotal(Double finalTotal) {
        this.finalTotal = finalTotal;
        return this;
    }

    public void setFinalTotal(Double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public FoodOrder vendorId(Long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public FoodOrder dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public FoodOrder dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public User getUser() {
        return user;
    }

    public FoodOrder user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public FoodOrder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public FoodOrder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @PrePersist
    public void onPrePersist() {
        dateCreated = Instant.now();
        dateUpdated = Instant.now();
    }

    @PreUpdate
    void onPreUpdate() {
        dateUpdated = Instant.now();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodOrder)) {
            return false;
        }
        return id != null && id.equals(((FoodOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
            "id=" + getId() +
            ", baseTotal=" + getBaseTotal() +
            ", finalTotal=" + getFinalTotal() +
            ", vendorId=" + getVendorId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
