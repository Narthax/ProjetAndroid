package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoric.projet.adapter.CustomAdapter;
import com.example.yoric.projet.asynctask.GetResult;
import com.example.yoric.projet.model.ListeFilm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetResult.ICallBack, View.OnClickListener {

    private TextView tv_main;
    private Button bt_rechercher;
    private EditText et_recherche;
    private Spinner spinner;
    private List<ListeFilm> listeFilms = new ArrayList<ListeFilm>();


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



        listeFilms.add(new ListeFilm());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        CustomAdapter adapterListe = new CustomAdapter(listeFilms,MainActivity.this);
        recyclerView.setAdapter(adapterListe);
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
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main.setText(string);

        //Voir yorick pour comprendre pk faire
        //variable listeFilms a utiliser

/*
        JSONObject object = new JSONObject(string);
        Log.i("aloooo   ",object.toString());
        Gson gson = new Gson();
        java.lang.reflect.Type collectionType = new TypeToken<List<ListeFilm>>() {}.getType();
        listeFilms = gson.fromJson(object.toString(), collectionType);
 */

        if (spinner.getSelectedItemPosition()==0) {         //Pour un seul film
            JSONObject object = new JSONObject(string);
            Gson gson = new Gson();
            ListeFilm listeFilm = gson.fromJson(object.toString(), ListeFilm.class);

        Log.i("TEST          ffff",listeFilms.get(0).getDirector());

        Toast.makeText(this, listeFilms.get(0).getShowTitle()+ "", Toast.LENGTH_LONG).show();
            Log.i("TITRE", listeFilm.getShowTitle());
        }else {                                             //Pour une liste de film
            Type listType = new TypeToken<ArrayList<ListeFilm>>() {
            }.getType();
            ArrayList<ListeFilm> list = new Gson().fromJson(string, listType);

            for (ListeFilm film : list) {
                Log.i("TITRE", film.getShowTitle());
            }
        }




        //Gson fait
        //Suite mettre dans liste view
    }
}
