package us.guihouse.autobank.http;

import java.net.URLConnection;

/**
 * Created by aluno on 18/10/16.
 */

public abstract class AuthenticatedRequest<T> extends BasePostRequest<T> {
    private String authorization;

    @Override
    protected void addHeaders(URLConnection urlConnection) {
        super.addHeaders(urlConnection);
        urlConnection.addRequestProperty("Authorization", getAuthorization());
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
