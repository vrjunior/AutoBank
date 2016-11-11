package us.guihouse.autobank.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by valmir.massoni on 10/11/2016.
 */

public class GenericBills {
    List<OpenBill> openBills;
    List<ClosedBill> closedBills;

    public List<OpenBill> getOpenBills() {
        return openBills;
    }

    public List<ClosedBill> getClosedBills() {
        return closedBills;
    }

    public void setOpenBills(List<OpenBill> openBills) {
        this.openBills = openBills;
    }

    public void setClosedBills(List<ClosedBill> closedBills) {
        this.closedBills = closedBills;
    }

    public List<Object> getGenericList() {
        List<Object> generic = new ArrayList<Object>();
        generic.addAll(openBills);
        generic.addAll(closedBills);

        return generic;
    }
}
