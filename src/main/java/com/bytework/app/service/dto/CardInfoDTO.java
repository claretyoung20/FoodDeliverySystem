package com.bytework.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytework.app.domain.CardInfo} entity.
 */
public class CardInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nameOnCard;

    @NotNull
    private String cvv;

    @NotNull
    private String cardNumber;

    @NotNull
    private String expireDate;

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

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
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

        CardInfoDTO cardInfoDTO = (CardInfoDTO) o;
        if (cardInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cardInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CardInfoDTO{" +
            "id=" + getId() +
            ", nameOnCard='" + getNameOnCard() + "'" +
            ", cvv='" + getCvv() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
