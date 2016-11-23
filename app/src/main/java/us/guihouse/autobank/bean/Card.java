package us.guihouse.autobank.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by valmir.massoni on 19/10/2016.
 */
public class Card implements Serializable {
    private Long id;

    private BigDecimal limit;
    private BigDecimal interestRate;
    private Integer closingDay;
    private Boolean isActive;
    private BigDecimal availableValue;
    private BigDecimal usedValue;

    public Long getId() {
        return id;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Integer getClosingDay() {
        return closingDay;
    }

    public Boolean isActive() {
        return isActive;
    }

    public BigDecimal getAvailableValue() {
        return availableValue;
    }

    public BigDecimal getUsedValue() {
        return usedValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setClosingDay(Integer closingDay) {
        this.closingDay = closingDay;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setAvailableValue(BigDecimal availableValue) {
        this.availableValue = availableValue;
    }

    public void setUsedValue(BigDecimal usedValue) {
        this.usedValue = usedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return id.equals(card.id);

    }

    @Override
    public int hashCode() {
        int result = 31 * id.hashCode();
        return result;
    }
}
