package us.guihouse.autobank.bean;

import java.math.BigDecimal;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class Purchase extends FinantialStatement {
    private BigDecimal purchaseValue;
    private String establishmentName;
    private String categoryName;
    private int installmentsAmount;
    private BigDecimal installmentValue;
    private int installmentSequential;

    public BigDecimal getPurchaseValue() {
        return purchaseValue;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getInstallmentsAmount() {
        return installmentsAmount;
    }

    public BigDecimal getInstallmentValue() {
        return installmentValue;
    }

    public int getInstallmentSequential() {
        return installmentSequential;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setInstallmentsAmount(int installmentsAmount) {
        this.installmentsAmount = installmentsAmount;
    }

    public void setInstallmentValue(BigDecimal installmentValue) {
        this.installmentValue = installmentValue;
    }

    public void setInstallmentSequential(int installmentSequential) {
        this.installmentSequential = installmentSequential;
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        this.purchaseValue = purchaseValue;
    }
}
