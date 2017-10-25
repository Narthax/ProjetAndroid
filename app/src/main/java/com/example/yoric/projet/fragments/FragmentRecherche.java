package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yoric.projet.R;
import com.example.yoric.projet.asynctask.GetResult;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.KnownFor;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yorick on 22-10-17.
 */

public class FragmentRecherche extends Fragment implements GetResult.ICallBack, View.OnClickListener {

    private EditText et_recherche;
    private Button bt_film;
    private Button bt_serie;
    private Button bt_personne;
    private Button bt_recherche;

    private String typeRecherche="0";
    private List<Film> films = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private List<Personne> personnes = new ArrayList<>();
    private List<KnownFor> knownFors = new ArrayList<>();

    private static FragmentRecherche fragmentRecherche = null;
    private FragmentList fragmentList = FragmentList.getInstance();

    public static FragmentRecherche getInstance() {
        if (fragmentRecherche == null) {
            fragmentRecherche = new FragmentRecherche();
        }
        return fragmentRecherche;
    }

    private RechercheCallBack rechercheCallBack;
    public void setRechercheCallBack(RechercheCallBack recherche) {
        this.rechercheCallBack = recherche;
    }
    public interface RechercheCallBack {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_recherche,container,false);

        et_recherche = (EditText) v.findViewById(R.id.et_recherche);
        bt_film = (Button) v.findViewById(R.id.bt_film);
        bt_serie = (Button) v.findViewById(R.id.bt_serie);
        bt_personne = (Button) v.findViewById(R.id.bt_personne);
        bt_recherche = (Button) v.findViewById(R.id.bt_rechercher);

        bt_film.setOnClickListener(this);
        bt_serie.setOnClickListener(this);
        bt_personne.setOnClickListener(this);
        changeSelectedButton(bt_film,bt_serie,bt_personne);
        bt_recherche.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_film: changeSelectedButton(bt_film,bt_serie,bt_personne);lancerGetResult();break;
            case R.id.bt_serie: changeSelectedButton(bt_serie,bt_film,bt_personne);lancerGetResult();break;
            case R.id.bt_personne: changeSelectedButton(bt_personne,bt_serie,bt_film);lancerGetResult();break;

            case R.id.bt_rechercher:lancerGetResult();break;
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


    public void lancerGetResult(){
        GetResult task = new GetResult();
        task.setCallback(this);

        if(et_recherche.getText().toString().isEmpty() || et_recherche.getText().toString()==null){

        }
        else {
            task.execute(
                    et_recherche.getText().toString(),
                    typeRecherche
            );
        }
    }
}
