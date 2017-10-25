package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterPersonnePetit;
import com.example.yoric.projet.asynctask.GetResultDetails;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.KnownFor;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;
import com.example.yoric.projet.utils.EnumGenre;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kista on 22-10-17.
 */

public class FragmentDetails extends Fragment implements FragmentList.ListCallBack, GetResultDetails.ICallBack{
    private TextView tv_titre;
    private ImageView iv_Image;
    private TextView tv_date;
    private TextView tv_genre;
    private Button bt_bandeAnnonce;
    private TextView tv_note;
    private TextView tv_description;
    private RecyclerView recyclerView;



    private Film film;
    private Serie serie;
    private Personne personne;
    private List<Personne> personnes = new ArrayList<Personne>();
    private List<KnownFor> knownFors = new ArrayList<KnownFor>();;
    private String type="0";

    private static final String YOUTUBE="https://www.youtube.com/results?search_query=";


    private static FragmentDetails fragmentDetails = null;
    public static FragmentDetails getInstance() {
        if (fragmentDetails == null) {
            fragmentDetails = new FragmentDetails();
        }
        return fragmentDetails;
    }



    private DetailsCallBack detailsCallBack;
    public void setDetailsCallBack(DetailsCallBack details){
        this.detailsCallBack = details;
    }
    public interface DetailsCallBack{
        void afficherDetails();
    }


    public void setListPersonne(List<Personne> list) {
        personnes = list;
        recyclerView.setAdapter(getAdapterListePersonnePetit());
        getAdapterListePersonnePetit().updateAffichage(list);
        type = "0";
    }

    private CustomAdapterPersonnePetit customAdapterPersonne = null;
    public CustomAdapterPersonnePetit getAdapterListePersonnePetit(){
        if(customAdapterPersonne==null){
            customAdapterPersonne = new CustomAdapterPersonnePetit(personnes,this.getContext());
        }
        return customAdapterPersonne;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_details,container,false);

        tv_titre = (TextView) v.findViewById(R.id.tv_details_titre);
        iv_Image = (ImageView) v.findViewById(R.id.iv_details_image);
        tv_date = (TextView) v.findViewById(R.id.tv_details_date);
        tv_genre = (TextView) v.findViewById(R.id.tv_details_genre);
        bt_bandeAnnonce = (Button) v.findViewById(R.id.bt_details_bandeAnnonce);
        tv_note = (TextView) v.findViewById(R.id.bt_details_Note);
        tv_description = (TextView) v.findViewById(R.id.tv_details_description);

        getAdapterListePersonnePetit();

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_details_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        initialiseDetails();
        return v;
    }



    @Override
    public void afficher(Object o, String objStr) {
        detailsCallBack.afficherDetails();
        GetResultDetails task = new GetResultDetails();
        task.setCallback(this);

        switch (objStr){
            case "0":
                film = (Film) o;
                this.type = "0";

                task.execute(
                        "0",
                        film.getId()+""
                );
                break;
            case "1":
                serie = (Serie) o;
                this.type = "1";

                task.execute(
                        "1",
                        serie.getId()+""
                );
                break;
            case "2":
                personne = (Personne) o;
                this.type = "2";
                task.execute(
                        "2",
                        personne.getId()+""
                );
                break;
        }
    }
    private void initialiseDetails(){
        switch (type){
            case "0":
                tv_titre.setText(film.getTitle());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+film.getPosterPath()).into(iv_Image);
                tv_date.setText(film.getReleaseDate());
                tv_genre.setText(EnumGenre.genresString(film.getGenreIds()));
                bt_bandeAnnonce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String date="";
                        if(!film.getReleaseDate().toString().equals("")){
                            date = film.getReleaseDate().substring(0,4);
                        }

                        String url = YOUTUBE+film.getTitle()+"+"+date+"+trailer";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                tv_note.setText("Note : "+film.getVoteAverage()+" / 10");
                tv_description.setText(film.getOverview());
                ;break;

            case "1":
                tv_titre.setText(serie.getName());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+serie.getPosterPath()).into(iv_Image);
                tv_date.setText(serie.getFirstAirDate());
                tv_genre.setText(EnumGenre.genresString(serie.getGenreIds()));
                bt_bandeAnnonce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String date="";
                        if(!serie.getFirstAirDate().toString().equals("")){
                            date = serie.getFirstAirDate().substring(0,4);
                        }

                        String url =YOUTUBE+film.getTitle()+"+"+date+"+trailer";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                tv_note.setText("Note : "+serie.getVoteAverage()+" / 10");
                tv_description.setText(serie.getOverview());
                ;break;

            case "2":
                tv_titre.setText(personne.getName());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+personne.getProfilePath()).into(iv_Image);
                //tv_date.setText(personne.getBirthday());
                //v_description.setText(personne.getBiography());

                break;
        }
    }


    @Override
    public void parseData(String string) throws JSONException {
        Log.i("JSON : ",string);
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(string);
        Type listType;

        if(type.equals("2")){

        }
        else {
            listType = new TypeToken<ArrayList<Personne>>() {}.getType();
            personnes = new Gson().fromJson(jsonObject.getJSONArray("cast").toString(), listType);
            this.setListPersonne(personnes);
        }
    }

}
