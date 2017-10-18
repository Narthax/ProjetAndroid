package com.example.yoric.projet;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.example.yoric.projet.asynctask.GetResult;
import com.example.yoric.projet.fragments.FragmentList;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetResult.ICallBack, View.OnClickListener, FragmentList.ListCallBack{

    private Button bt_rechercher;
    private EditText et_recherche;

    private Button bt_film;
    private Button bt_serie;
    private Button bt_personne;
    private String typeRecherche="0";

    private Button bt_home;

    private FragmentList fragmentList = FragmentList.getInstance();


    private List<Film> films = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private List<Personne> personnes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_recherche= (EditText) findViewById(R.id.et_recherche);

        bt_rechercher = (Button) findViewById(R.id.bt_rechercher);
        bt_rechercher.setOnClickListener(this);

        bt_film = (Button) findViewById(R.id.bt_film);
        bt_film.setOnClickListener(this);
        bt_serie = (Button) findViewById(R.id.bt_serie);
        bt_serie.setOnClickListener(this);
        bt_personne = (Button) findViewById(R.id.bt_personne);
        bt_personne.setOnClickListener(this);
        changeSelectedButton(bt_film,bt_serie,bt_personne);

        bt_home = (Button) findViewById(R.id.bt_home);
        bt_home.setOnClickListener(this);


        //On crée le fragment film par défaut
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentList.setListCallBack(this);
        transaction.add(R.id.fl_main_fragment_list, fragmentList);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_home:     fragmentList.setListeFilm(films);break;

            case R.id.bt_film: changeSelectedButton(bt_film,bt_serie,bt_personne);break;
            case R.id.bt_serie: changeSelectedButton(bt_serie,bt_film,bt_personne);break;
            case R.id.bt_personne: changeSelectedButton(bt_personne,bt_serie,bt_film);break;

            case R.id.bt_rechercher:
                GetResult task = new GetResult();
                task.setCallback(this);
                Log.i("test",et_recherche.getText().toString());
                task.execute(
                        et_recherche.getText().toString(),
                        typeRecherche
                );
                break;
        }
    }

    @Override
    public void parseData(String string) throws JSONException {
        Log.i("JSON : ",string);
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(string);
        Type listType;

        switch (typeRecherche){
            case "0" :
                listType = new TypeToken<ArrayList<Film>>() {}.getType();
                films = new Gson().fromJson(jsonObject.getJSONArray("results").toString(), listType);
                fragmentList.setListeFilm(films);
                break;

            case "1" :
                listType = new TypeToken<ArrayList<Serie>>() {}.getType();
                series = new Gson().fromJson(jsonObject.getJSONArray("results").toString(), listType);;
                fragmentList.setListeSerie(series);
                break;

            case "2" :
                listType = new TypeToken<ArrayList<Personne>>() {}.getType();
                personnes = new Gson().fromJson(jsonObject.getJSONArray("results").toString(), listType);
                fragmentList.setListePersonne(personnes);
                break;
        }
    }


    private void changeSelectedButton(Button select, Button b2, Button b3){
        select.setBackgroundColor(Color.GREEN);
        setTypeRecherche(select.getId());
        b2.setBackgroundColor(Color.WHITE);
        b3.setBackgroundColor(Color.WHITE);
    }
    private void setTypeRecherche(int i){
        switch (i){
            case R.id.bt_film : typeRecherche="0";break;
            case R.id.bt_serie : typeRecherche="1";break;
            case R.id.bt_personne : typeRecherche="2";break;
        }
    }
}
