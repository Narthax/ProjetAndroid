package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;



import com.example.yoric.projet.asynctask.GetResult;
//import com.example.yoric.projet.fragments.FragmentList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetResult.ICallBack, View.OnClickListener/*, FragmentList.ListCallBack*/{

    private Button bt_rechercher;
    private EditText et_recherche;
    private Spinner spinner;

 // private List<ListeFilm> listeFilms = new ArrayList<ListeFilm>();
   // private FragmentList fragmentList = FragmentList.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rechercher = (Button) findViewById(R.id.bt_rechercher);
        bt_rechercher.setOnClickListener(this);
        //Liste déroulante
        spinner = (Spinner) findViewById(R.id.s_choix);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.s_choix, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



/*
        //Fragment contenant la liste de film après la recherche
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fragmentList.setListCallBack(this);
        transaction.add(R.id.fl_main_fragment_list,fragmentList);
        transaction.commit();*/

    }

    @Override
    public void onClick(View v) {
        et_recherche= (EditText) findViewById(R.id.et_recherche);

        GetResult task = new GetResult();
        task.setCallback(this);

        task.execute(
                et_recherche.getText().toString(),
                spinner.getSelectedItemPosition()+""
        );
    }

    @Override
    public void parseData(String string) throws JSONException {
        Log.i("STRING_GET", string);
/*
        if (string.substring(0,1).equals("{")) {         //Pour un seul film
            JSONObject object = new JSONObject(string);
            Gson gson = new Gson();
            ListeFilm film = gson.fromJson(object.toString(), ListeFilm.class);
            listeFilms.add(film);
        }
        else if (string.substring(0,1).equals("[")){                                             //Pour une liste de film
            Type listType = new TypeToken<ArrayList<ListeFilm>>() {
            }.getType();
            listeFilms = new Gson().fromJson(string, listType);
        }

        fragmentList.envoyerListe(listeFilms);*/
    }
/*
    @Override
    public ListeFilm infoFilm(ListeFilm film) {
        return null;
    }*/
}
