package com.bytework.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CardInfo.
 */
@Entity
@Table(name = "card_info")
public class CardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_on_card", nullable = false)
    private String nameOnCard;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @NotNull
    @Column(name = "expire_date", nullable = false)
    private String expireDate;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cardInfos")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public CardInfo nameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
        return this;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCvv() {
        return cvv;
    }

    public CardInfo cvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CardInfo cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public CardInfo expireDate(String expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public CardInfo dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public CardInfo dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public User getUser() {
        return user;
    }

    public CardInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardInfo)) {
            return false;
        }
        return id != null && id.equals(((CardInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
            "id=" + getId() +
            ", nameOnCard='" + getNameOnCard() + "'" +
            ", cvv='" + getCvv() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
