package us.guihouse.autobank.fetchers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import us.guihouse.autobank.LoginActivity;
import us.guihouse.autobank.R;
import us.guihouse.autobank.http.AuthenticatedRequest;

import static us.guihouse.autobank.http.Constants.SHARED_PREFS_FILE;
import static us.guihouse.autobank.http.Constants.SHARED_PREFS_TOKEN;

/**
 * Created by aluno on 18/10/16.
 */

public class AuthorizedFetcher<REQUEST extends AuthenticatedRequest<OUT>, OUT>
        extends BasicFetcher<REQUEST, OUT> {

    private Context context;
    private String token;

    public static abstract class AuthorizedFetchCallback<OUT> implements BasicFetcher.FetchCallback<OUT> {
        private Context context;

        @Override
        public void onNoAuthentication() {
            Toast.makeText(context, R.string.expired_session, Toast.LENGTH_LONG).show();

            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }

        private void setContext(Context context) {
            this.context = context;
        }
    }

    public AuthorizedFetcher(Context context, AuthorizedFetchCallback<OUT> callback) {
        super(callback);
        callback.setContext(context);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        token = prefs.getString(SHARED_PREFS_TOKEN, "");
    }

    @Override
    protected OUT doInBackground(REQUEST... params) {
        REQUEST request = params[0];
        request.setAuthorization(token);
        return super.doInBackground(request);
    }
}
