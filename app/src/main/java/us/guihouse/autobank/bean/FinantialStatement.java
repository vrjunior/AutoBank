package us.guihouse.autobank.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public abstract class FinantialStatement implements Serializable {
    private Long id;
    private Date processDate;

    public Long getId() {
        return id;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}
