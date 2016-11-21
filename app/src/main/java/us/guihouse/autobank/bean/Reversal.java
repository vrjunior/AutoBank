package us.guihouse.autobank.bean;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class Reversal extends FinantialStatement {
    private BigDecimal reversalValue;

    public BigDecimal getReversalValue() {
        return reversalValue;
    }

    public void setReversalValue(BigDecimal reversalValue) {
        this.reversalValue = reversalValue;
    }
}
