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

public class GetResultDetails extends AsyncTask<String, Void, String> {
    private static final String URL = "https://api.themoviedb.org/3/";
    private static final String KEY = "/credits?api_key=ea9e2a5a6ab2b2321c066e0632186430&language=fr";
    private static final String KEYPERSONNE = "?api_key=ea9e2a5a6ab2b2321c066e0632186430&language=fr";
    private ICallBack callback;


    public void setCallback(ICallBack callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String value = params[0];
            URL url=null;

            if (value.equals("0")){
                value = "movie";
                url = new URL(URL+value+"/"+params[1]+KEY);
            }
            else if (value.equals("1")){
                value = "tv";
                url = new URL(URL+value+"/"+params[1]+KEY);
            }
            else if (value.equals("2")){
                value = "person";
                url = new URL(URL+value+"/"+params[1]+KEYPERSONNE);
            }

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


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            callback.parseDataDetail(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ICallBack{
        void parseDataDetail(String string) throws JSONException;
    }
}
