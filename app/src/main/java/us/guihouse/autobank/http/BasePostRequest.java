package us.guihouse.autobank.http;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public abstract class BasePostRequest<T> {
    private static final String TAG = "REQUEST_HTTP";
    private Gson gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();

    public static class RequestFail extends Exception {}
    public static class NoAuthentication extends Exception {}
    public static class NoConnection extends RequestFail {}

    protected abstract CharSequence getUploadData();
    protected abstract URL getUrl() throws MalformedURLException;
    protected abstract T convertResponse(String responseBody);

    protected void addHeaders(URLConnection urlConnection) {
        urlConnection.addRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Pragma", "no-cache");
        urlConnection.setRequestProperty("Cache-Control", "no-cache");
    }

    protected Gson getGson() {
        return this.gson;
    }

    public T executeRequest() throws RequestFail, NoAuthentication {
        if (!isInternetAvailable()) {
            throw new NoConnection();
        }

        HttpURLConnection urlConnection = null;

        try {
            URL requestUrl = getUrl();
            Log.d(TAG, "Sending 'POST' request to " + requestUrl.toString());

            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setDefaultUseCaches(false);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");

            this.addHeaders(urlConnection);
            this.uploadData(new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream())));

            int responseCode = urlConnection.getResponseCode();
            Log.d(TAG, "Response Code: " + responseCode);

            if (responseCode < 200 || responseCode > 299) {
                throw new RequestFail();
            }
            if( responseCode == 401) {
                throw new NoAuthentication();
            }

            String data = downloadData(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())));
            return (T) convertResponse(data);
        } catch (IOException e) {
            Log.e("ERRO", e.getMessage(), e);
            throw new RequestFail();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private void uploadData(BufferedWriter writter) throws IOException {
        CharSequence data = getUploadData();

        if (data == null) {
            return;
        }

        try {
            writter.append(data);
            writter.flush();
        } finally {
            writter.close();
        }
    }

    private String downloadData(BufferedReader in) throws IOException {
        StringBuffer response = new StringBuffer();
        String inputLine;

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } finally {
            in.close();
        }

        return response.toString();
    }

    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (IOException e) {
            Log.e("ERRO", e.getMessage(), e);
            return false;
        }

    }
}