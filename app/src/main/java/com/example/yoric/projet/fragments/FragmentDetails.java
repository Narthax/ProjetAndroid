package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yoric.projet.MainActivity;
import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;

/**
 * Created by Kista on 22-10-17.
 */

public class FragmentDetails extends Fragment implements FragmentList.ListCallBack{
    private TextView tv_titre;
    private TextView tv_date;
    private TextView tv_genre;
    private TextView tv_bandeAnnonce;
    private RatingBar rb_rating;
    private TextView tv_description;
    private RecyclerView list;


    private Film film;
    private Serie serie;
    private Personne personne;


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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_details,container,false);

        tv_titre = (TextView) v.findViewById(R.id.tv_details_titre);
        tv_date = (TextView) v.findViewById(R.id.tv_details_date);
        tv_genre = (TextView) v.findViewById(R.id.tv_details_genre);
        tv_bandeAnnonce = (TextView) v.findViewById(R.id.tv_details_bandeAnnonce);
        rb_rating = (RatingBar) v.findViewById(R.id.rb_details_ratinbar);
        tv_description = (TextView) v.findViewById(R.id.tv_details_description);

        return v;
    }


    @Override
    public void afficher(Object o, String type) {
        switch (type){
            case "0":
                film = (Film) o;
                Log.i("testAAA",film.getTitle());
                ;break;
            case "1":
                serie = (Serie) o;
                Log.i("testBBB",serie.getName());
                ;break;
            case "2":
                personne = (Personne) o;
                Log.i("testCCC",personne.getName());
                ;break;
        }
        initialise(type);
    }

    private void initialise(String type){
        switch (type){
            case "0":
                tv_titre.setText(film.getTitle());
                tv_date.setText(film.getReleaseDate());
                tv_genre.setText(film.getGenreIds().toString());
                tv_bandeAnnonce.setText(film.getVideo()+"");
                rb_rating.setRating((Float.parseFloat(film.getVoteAverage().toString()))/2);
                tv_description.setText(film.getOverview());
                ;break;
            case "1":
                ;break;
            case "2":
                ;break;
        }
    }
}
