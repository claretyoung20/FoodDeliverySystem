package com.bytework.app.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A VendorAddress.
 */
@Entity
@Table(name = "vendor_address")
public class VendorAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "full_adress", nullable = false)
    private String fullAdress;

    @NotNull
    @Column(name = "v_lat", nullable = false, unique = true)
    private Double vLat;

    @NotNull
    @Column(name = "v_lng", nullable = false, unique = true)
    private Double vLng;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public VendorAddress fullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
        return this;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public Double getvLat() {
        return vLat;
    }

    public VendorAddress vLat(Double vLat) {
        this.vLat = vLat;
        return this;
    }

    public void setvLat(Double vLat) {
        this.vLat = vLat;
    }

    public Double getvLng() {
        return vLng;
    }

    public VendorAddress vLng(Double vLng) {
        this.vLng = vLng;
        return this;
    }

    public void setvLng(Double vLng) {
        this.vLng = vLng;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public VendorAddress dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public VendorAddress dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public User getUser() {
        return user;
    }

    public VendorAddress user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    public void onPrePersist() {
        dateCreated = Instant.now();
        dateUpdated = Instant.now();
    }

    @PreUpdate
    void onPreUpdate() {
        dateUpdated = Instant.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorAddress)) {
            return false;
        }
        return id != null && id.equals(((VendorAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VendorAddress{" +
            "id=" + getId() +
            ", fullAdress='" + getFullAdress() + "'" +
            ", vLat=" + getvLat() +
            ", vLng=" + getvLng() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
