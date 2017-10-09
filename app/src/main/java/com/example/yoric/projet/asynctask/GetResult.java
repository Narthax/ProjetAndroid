package com.example.yoric.projet.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yoric on 07-10-17.
 */

public class GetResult extends AsyncTask<String, Void, String> {

    private static final String URL = "http://netflixroulette.net/api/api.php?title=";
    private ICallBack callback;


    public void setCallback(ICallBack callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        String txt = params[0];

        try{
            //URL url = new URL(URL+params[0]);
            URL url = new URL("http://netflixroulette.net/api/api.php?actor=Nicolas%20Cage");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.i("CO",connection.toString());

            connection.setRequestMethod("GET");
            connection.connect();
            Log.i("MSG", "cc");

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";

            while((line = reader.readLine()) != null){
                builder.append(line);
            }

            reader.close();
            connection.disconnect();
            Log.i("MSG2",builder.toString());
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            callback.parseData(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ICallBack{
        void parseData(String string) throws JSONException;
    }
}
