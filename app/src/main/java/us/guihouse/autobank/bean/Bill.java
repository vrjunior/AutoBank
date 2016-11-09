package us.guihouse.autobank.bean;

import java.util.Date;

/**
 * Created by aluno on 18/10/16.
 */

public abstract class Bill {
    private Long id;
    private int month;
    private int year;
    private Date paymentDeadline;

    public Long getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }
}
