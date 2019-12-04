package com.bytework.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "price", nullable = false)
    private Double price;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("menus")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Menu dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Menu dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDescription() {
        return description;
    }

    public Menu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Menu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Menu price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public Menu image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Menu imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsAvailable() {
        return isAvailable;
    }

    public Menu isAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public User getUser() {
        return user;
    }

    public Menu user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", isAvailable='" + isIsAvailable() + "'" +
            "}";
    }
}
