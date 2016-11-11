package us.guihouse.autobank.http;

import java.net.MalformedURLException;
import java.net.URL;

import us.guihouse.autobank.bean.Card;

/**
 * Created by aluno on 11/11/16.
 */

public class CardRequest extends AuthenticatedRequest<Card> {
    @Override
    protected CharSequence getUploadData() {
        return null;
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.CARD_URL);
    }

    @Override
    protected Card convertResponse(String responseBody) {
        return getGson().fromJson(responseBody, Card.class);
    }
}
