package com.example.yoric.projet.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.yoric.projet.utils.Utils;
import com.google.gson.Gson;

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

    private static final String URL = "https://netflixroulette.net/api/api.php?";
    private ICallBack callback;


    public void setCallback(ICallBack callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        String value = params[1];

        if (value.equals("0")){
            value = "title";
        }else if (value.equals("1")){
            value = "actor";
        }else if (value.equals("2")){
            value = "director";
        }

       /* try{

            URL url = new URL((URL+value+"="+params[0]).replaceAll(" ","%20"));
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


        return null;*/
        return Utils.Intent.TAG_REALISATEUR;
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
