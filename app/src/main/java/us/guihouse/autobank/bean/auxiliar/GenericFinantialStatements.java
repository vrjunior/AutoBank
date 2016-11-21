package us.guihouse.autobank.bean.auxiliar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import us.guihouse.autobank.bean.FinantialStatement;
import us.guihouse.autobank.bean.InterestRate;
import us.guihouse.autobank.bean.Payment;
import us.guihouse.autobank.bean.Purchase;
import us.guihouse.autobank.bean.Reversal;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class GenericFinantialStatements {
    private ArrayList<Purchase> purchases;
    private ArrayList<InterestRate> interestRates;
    private ArrayList<Payment> payments;
    private ArrayList<Reversal> reversals;

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public ArrayList<InterestRate> getInterestRates() {
        return interestRates;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<Reversal> getReversals() {
        return reversals;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public void setInterestRates(ArrayList<InterestRate> interestRates) {
        this.interestRates = interestRates;
    }

    public void setReversals(ArrayList<Reversal> reversals) {
        this.reversals = reversals;
    }

    public ArrayList<Object> getGenericObjectList() {
        ArrayList<Object> arrayObjects = new ArrayList<>();
        arrayObjects.addAll(this.getPurchases());
        arrayObjects.addAll(this.getInterestRates());
        arrayObjects.addAll(this.getPayments());
        arrayObjects.addAll(this.getReversals());

        Collections.sort(arrayObjects, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return (((FinantialStatement)o2).getProcessDate().compareTo(((FinantialStatement)o1).getProcessDate()));
            }
        });

        return arrayObjects;
    }
}
