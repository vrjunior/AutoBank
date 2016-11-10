package us.guihouse.autobank.http;

import java.net.MalformedURLException;
import java.net.URL;

import us.guihouse.autobank.bean.GenericBills;
import us.guihouse.autobank.bean.Login;
import us.guihouse.autobank.bean.Session;

/**
 * Created by aluno on 11/10/16.
 */

public class LoginRequest extends BasePostRequest<Session> {
    private Login login;

    public LoginRequest(Login login) {
        this.login = login;
    }

    @Override
    protected String getUploadData() {
        return this.getGson().toJson(login);
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.LOGIN_URL);
    }

    @Override
    protected Session convertResponse(String responseBody) {
        return this.getGson().fromJson(responseBody, Session.class);
    }
}
