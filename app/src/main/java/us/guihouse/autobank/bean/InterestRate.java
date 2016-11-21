package us.guihouse.autobank.bean;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class InterestRate extends FinantialStatement {
    private BigDecimal interestRateValue;

    public BigDecimal getInterestRateValue() {
        return this.interestRateValue;
    }

    public void setInterestRateValue(BigDecimal interestRateValue) {
        this.interestRateValue = interestRateValue;
    }
}
