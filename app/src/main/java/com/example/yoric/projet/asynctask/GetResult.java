package com.example.yoric.projet.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
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

        try{

            URL url = new URL("http://netflixroulette.net/api/api.php?director=Quentin%20Tarantino");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                //PROBLEME URL + connection correcte mais il ne recupere pas les données du site
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e){
                Log.e("ERROR", e.getMessage());
            } finally{
                urlConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("ERROR_BACKGROUND", e.getMessage());
        }
        
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        callback.parseData(s);
    }

    public interface ICallBack{
        void parseData(String string);
    }
}
