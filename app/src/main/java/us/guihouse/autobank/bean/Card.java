package us.guihouse.autobank.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class Card implements Serializable {
    private Long id;
    private String cardNumber;

    private Date emission;
    private Date expiration;

    private BigDecimal limit;
    private BigDecimal interestRate;
    private Integer closingDay;
    private Boolean isActive;
    private BigDecimal availableValue;
    private BigDecimal usedValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getEmission() {
        return emission;
    }

    public void setEmission(Date emission) {
        this.emission = emission;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getClosingDay() {
        return closingDay;
    }

    public void setClosingDay(Integer closingDay) {
        this.closingDay = closingDay;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BigDecimal getAvailableValue() {
        return availableValue;
    }

    public void setAvailableValue(BigDecimal availableValue) {
        this.availableValue = availableValue;
    }

    public BigDecimal getUsedValue() {
        return usedValue;
    }

    public void setUsedValue(BigDecimal usedValue) {
        this.usedValue = usedValue;
    }

    public BigDecimal getCurrentMaximum() {
        if (availableValue.compareTo(limit) > 0) {
            return availableValue;
        }

        return limit;
    }
}
