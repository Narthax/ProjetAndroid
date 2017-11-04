package com.helha.yoric.projet.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.helha.yoric.projet.MainActivity;
import com.helha.yoric.projet.R;
import com.helha.yoric.projet.asynctask.GetResult;
import com.helha.yoric.projet.model.Film;
import com.helha.yoric.projet.model.Personne;
import com.helha.yoric.projet.model.Serie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        et_recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                ((MainActivity) getActivity()).hideSoftKeyboard(getActivity(), getView());
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    if(i == EditorInfo.IME_ACTION_DONE){
                        lancerGetResult();
                        handled = true;
                    }

                } else {
                    Toast.makeText(getActivity(), "Pas de connexion internet", Toast.LENGTH_LONG).show();
                }
                return handled;
            }
        });
        bt_film = (Button) v.findViewById(R.id.bt_film);
        bt_serie = (Button) v.findViewById(R.id.bt_serie);
        bt_personne = (Button) v.findViewById(R.id.bt_personne);
        bt_recherche = (Button) v.findViewById(R.id.bt_rechercher);

        bt_film.setOnClickListener(this);
        bt_serie.setOnClickListener(this);
        bt_personne.setOnClickListener(this);
        changeSelectedButton(bt_film,bt_serie,bt_personne);
        bt_recherche.setOnClickListener(this);
        bt_recherche.setBackgroundColor(Color.parseColor("#b5b5b5"));

        return v;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).hideSoftKeyboard(getActivity(), v);

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            switch (v.getId()) {
                case R.id.bt_film: changeSelectedButton(bt_film,bt_serie,bt_personne);lancerGetResult();break;
                case R.id.bt_serie: changeSelectedButton(bt_serie,bt_film,bt_personne);lancerGetResult();break;
                case R.id.bt_personne: changeSelectedButton(bt_personne,bt_serie,bt_film);lancerGetResult();break;
                case R.id.bt_rechercher:lancerGetResult();break;
            }

        } else {
            Toast.makeText(getActivity(), "Pas de connexion internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void parseData(String string) throws JSONException {
        Log.i("JSON : ",string+"");

        if(string.equals("error")){
            Toast.makeText(getActivity(), "Une erreur est survenue lors de votre recherche", Toast.LENGTH_LONG).show();
            fragmentList.clearAllListes();
            return;
        }

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
        GradientDrawable bgSelect = (GradientDrawable)select.getBackground();
        GradientDrawable bgB2 = (GradientDrawable)b2.getBackground();
        GradientDrawable bgB3 = (GradientDrawable)b3.getBackground();

        bgSelect.setColor(Color.parseColor("#af000c"));
        setTypeRecherche(select.getId());
        bgB2.setColor(Color.parseColor("#b5b5b5"));
        bgB3.setColor(Color.parseColor("#b5b5b5"));
    }
    private void setTypeRecherche(int i){
        switch (i){
            case R.id.bt_film : typeRecherche="0";break;
            case R.id.bt_serie : typeRecherche="1";break;
            case R.id.bt_personne : typeRecherche="2";break;
        }
    }


    private void lancerGetResult(){
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

    public void setTextToNull(){
        et_recherche.setText("");
    }
}
