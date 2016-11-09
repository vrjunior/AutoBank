package us.guihouse.autobank.bean;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 09/11/2016.
 */

public class OpenBill extends Bill{
    private BigDecimal partialValue;

    public BigDecimal getPartialValue() {
        return partialValue;
    }

    public void setPartialValue(BigDecimal partialValue) {
        this.partialValue = partialValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OpenBill openBill = (OpenBill) o;

        return partialValue.equals(openBill.partialValue);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + partialValue.hashCode();
        return result;
    }
}
