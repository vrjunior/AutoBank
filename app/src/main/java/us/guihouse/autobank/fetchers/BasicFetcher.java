package us.guihouse.autobank.fetchers;

import android.os.AsyncTask;
import android.util.Log;

import us.guihouse.autobank.http.BasePostRequest;

/**
 * Created by aluno on 14/10/16.
 */

public class BasicFetcher<REQUEST extends BasePostRequest<OUT>, OUT>
        extends AsyncTask<REQUEST, Void, OUT> {

    private static final String TAG = "BasicFetcher";
    private Exception exception;
    private FetchCallback<OUT> callback;

    public interface FetchCallback<OUT> {
        void onSuccess(OUT data);
        void onNoConnection();
        void onError();
        void onNoAuthentication();
    }

    public BasicFetcher(FetchCallback<OUT> callback) {
        this.callback = callback;
    }

    @Override
    protected OUT doInBackground(REQUEST... params) {
        try {
            return params[0].executeRequest();
        } catch (BasePostRequest.RequestFail requestFail) {
            exception = requestFail;
            Log.d(TAG, exception.getMessage(), exception);
        } catch (BasePostRequest.NoAuthentication noAuthentication) {
            exception = noAuthentication;
        }

        return null;
    }

    @Override
    protected void onPostExecute(OUT out) {
        super.onPostExecute(out);
        if (exception == null) {
            callback.onSuccess(out);
            return;
        }

        if (exception instanceof BasePostRequest.NoConnection) {
            callback.onNoConnection();
            return;
        }

        if(exception instanceof BasePostRequest.NoAuthentication ){
            callback.onNoAuthentication();
            return;
        }

        callback.onError();
    }
}
