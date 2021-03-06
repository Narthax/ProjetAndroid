package com.helha.yoric.projet.asynctask;

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

    private static final String URL = "https://api.themoviedb.org/3/search/";
    private static final String KEY = "ea9e2a5a6ab2b2321c066e0632186430&language=fr";
    private ICallBack callback;


    public void setCallback(ICallBack callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        String value = params[1];

        if (value.equals("0")){
            value = "movie";
        }
        else if (value.equals("1")){
            value = "tv";
        }
        else if (value.equals("2")){
            value = "person";
        }

        try{
            URL url = new URL((URL+value+"?query="+params[0]).replaceAll(" ","+")+"&api_key="+KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.i("URL", connection.toString());

            connection.setRequestMethod("GET");
            connection.connect();

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";

            while((line = reader.readLine()) != null){
                builder.append(line);
            }

            reader.close();
            connection.disconnect();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
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
