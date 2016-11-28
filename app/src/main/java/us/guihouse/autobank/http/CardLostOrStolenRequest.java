package us.guihouse.autobank.http;

import java.net.MalformedURLException;
import java.net.URL;

import us.guihouse.autobank.bean.CardLostOrStolen;

/**
 * Created by aluno on 28/11/16.
 */

public class CardLostOrStolenRequest extends AuthenticatedRequest<Boolean> {
    private CardLostOrStolen cardLostOrStolen;

    public CardLostOrStolenRequest(CardLostOrStolen cardLostOrStolen) {
        this.cardLostOrStolen = cardLostOrStolen;
    }

    @Override
    protected CharSequence getUploadData() {
        return getGson().toJson(cardLostOrStolen);
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.CARD_LOST_OR_STOLEN);
    }

    @Override
    protected Boolean convertResponse(String responseBody) {
        return getGson().fromJson(responseBody, Boolean.class);
    }
}
