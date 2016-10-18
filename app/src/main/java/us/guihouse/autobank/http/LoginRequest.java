package us.guihouse.autobank.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import us.guihouse.autobank.bean.Login;
import us.guihouse.autobank.bean.Session;

/**
 * Created by aluno on 11/10/16.
 */

public class LoginRequest extends BasePostRequest<Session> {
    private Login login;
    private Gson gson;

    public LoginRequest(Login login) {
        this.login = login;
        this.gson = new Gson();
    }

    @Override
    protected String getUploadData() {
        return gson.toJson(login);
    }

    @Override
    protected URL getUrl() throws MalformedURLException {
        return new URL(Constants.LOGIN_URL);
    }

    @Override
    protected Session convertResponse(String responseBody) {
        return gson.fromJson(responseBody, Session.class);
    }
}
