package us.guihouse.autobank.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.guihouse.autobank.bean.ClosedBill;
import us.guihouse.autobank.bean.auxiliar.GenericBills;
import us.guihouse.autobank.bean.OpenBill;

/**
 * Created by aluno on 18/10/16.
 */

public class ListBillsRequest extends AuthenticatedRequest<GenericBills> {

    @Override
    protected CharSequence getUploadData() {
        return null;
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.BILLS_URL);
    }

    @Override
    protected GenericBills convertResponse(String responseBody) {
        GenericBills genericBills = new GenericBills();
        JsonObject jsonObj = this.getGson().fromJson(responseBody, JsonObject.class);


        JsonArray openBillsArray = jsonObj.getAsJsonArray("openBills");
        Type type = new TypeToken<List<OpenBill>>(){}.getType();
        List<OpenBill> openBills =  getGson().fromJson(openBillsArray, type); //WHY ?
        genericBills.setOpenBills(openBills);


        ArrayList<ClosedBill> closedBills = getGson().fromJson(jsonObj.getAsJsonArray("closedBills"), new TypeToken<List<ClosedBill>>(){}.getType());
        genericBills.setClosedBills(closedBills);

        return genericBills;
    }
}
