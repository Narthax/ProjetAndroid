package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yoric.projet.MainActivity;
import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;
import com.squareup.picasso.Picasso;

/**
 * Created by Kista on 22-10-17.
 */

public class FragmentDetails extends Fragment implements FragmentList.ListCallBack{
    private TextView tv_titre;
    private ImageView iv_Image;
    private TextView tv_date;
    private TextView tv_genre;
    private TextView tv_bandeAnnonce;
    private RatingBar rb_rating;
    private TextView tv_description;
    private RecyclerView list;



    private Film film;
    private Serie serie;
    private Personne personne;
    private String type="0";



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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_details,container,false);

        tv_titre = (TextView) v.findViewById(R.id.tv_details_titre);
        iv_Image = (ImageView) v.findViewById(R.id.iv_details_image);
        tv_date = (TextView) v.findViewById(R.id.tv_details_date);
        tv_genre = (TextView) v.findViewById(R.id.tv_details_genre);
        tv_bandeAnnonce = (TextView) v.findViewById(R.id.tv_details_bandeAnnonce);
        rb_rating = (RatingBar) v.findViewById(R.id.rb_details_ratinbar);
        tv_description = (TextView) v.findViewById(R.id.tv_details_description);

        initialiseDetails();
        return v;
    }



    @Override
    public void afficher(Object o, String objStr) {
        detailsCallBack.afficherDetails();

        switch (objStr){
            case "0":
                film = (Film) o;
                Log.i("testAAA",film.getTitle());
                this.type = "0";
                ;break;
            case "1":
                serie = (Serie) o;
                Log.i("testBBB",serie.getName());
                this.type = "1";
                ;break;
            case "2":
                personne = (Personne) o;
                Log.i("testCCC",personne.getName());
                this.type = "2";
                ;break;
        }
    }
    private void initialiseDetails(){
        switch (type){
            case "0":
                tv_titre.setText(film.getTitle());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+film.getPosterPath()).into(iv_Image);
                tv_date.setText(film.getReleaseDate());
                tv_genre.setText(film.getGenreIds().toString());
                tv_bandeAnnonce.setText(film.getVideo()+"");
                rb_rating.setRating((Float.parseFloat(film.getVoteAverage().toString()))/2);
                tv_description.setText(film.getOverview());
                ;break;
            case "1":
                tv_titre.setText(serie.getName());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+serie.getPosterPath()).into(iv_Image);
                tv_date.setText(serie.getFirstAirDate());
                tv_genre.setText(serie.getGenreIds().toString());
                tv_bandeAnnonce.setText(serie.getOriginCountry()+"");       //remplacer lien yt
                rb_rating.setRating((Float.parseFloat(serie.getVoteAverage().toString()))/2);
                tv_description.setText(serie.getOverview());
                ;break;
            case "2":
                ;break;
        }
    }
}
