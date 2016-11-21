package us.guihouse.autobank.http;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.guihouse.autobank.bean.FinantialStatement;
import us.guihouse.autobank.bean.InterestRate;
import us.guihouse.autobank.bean.Payment;
import us.guihouse.autobank.bean.Purchase;
import us.guihouse.autobank.bean.Reversal;
import us.guihouse.autobank.bean.auxiliar.GenericFinantialStatements;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class FinantialStatementsRequest extends AuthenticatedRequest<GenericFinantialStatements> {

    private Long billId;

    public FinantialStatementsRequest(Long billId) {
        this.billId = billId;
    }

    @Override
    protected CharSequence getUploadData() {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("billId", this.billId);

        return getGson().toJson(jsonObj);
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.FINANTIAL_STATEMENTS_URL);
    }

    @Override
    protected GenericFinantialStatements convertResponse(String responseBody) {
        JsonObject jsonObj = this.getGson().fromJson(responseBody, JsonObject.class);
        GenericFinantialStatements genericFinantialStatements = new GenericFinantialStatements();

        ArrayList<Purchase> purchases = getGson().fromJson(jsonObj.getAsJsonArray("purchases"), new TypeToken<List<Purchase>>(){}.getType());
        genericFinantialStatements.setPurchases(purchases);

        ArrayList<InterestRate> interestRates = getGson().fromJson(jsonObj.getAsJsonArray("interestRates"), new TypeToken<List<InterestRate>>(){}.getType());
        genericFinantialStatements.setInterestRates(interestRates);

        ArrayList<Payment> payments = getGson().fromJson(jsonObj.getAsJsonArray("payments"), new TypeToken<List<Payment>>(){}.getType());
        genericFinantialStatements.setPayments(payments);

        ArrayList<Reversal> reversals = getGson().fromJson(jsonObj.getAsJsonArray("reversals"), new TypeToken<List<Reversal>>(){}.getType());
        genericFinantialStatements.setReversals(reversals);

        return genericFinantialStatements;
    }
}
