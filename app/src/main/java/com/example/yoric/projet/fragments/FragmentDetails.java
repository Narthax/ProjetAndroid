package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;


import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterKnownFor;
import com.example.yoric.projet.adapter.CustomAdapterPersonnePetit;
import com.example.yoric.projet.asynctask.GetResult;
import com.example.yoric.projet.asynctask.GetResultDetails;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.KnownFor;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.PersonneDetail;
import com.example.yoric.projet.model.Serie;
import com.example.yoric.projet.utils.EnumGenre;
import com.example.yoric.projet.utils.RecyclerItemClickListener;
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

public class FragmentDetails extends Fragment implements FragmentList.ListCallBack, GetResultDetails.ICallBack, GetResult.ICallBack{
    private TextView tv_titre;
    private ImageView iv_Image;
    private TextView tv_date;
    private TextView tv_genre;
    private Button bt_bandeAnnonce;
    private TextView tv_note;
    private TextView tv_description;
    private RecyclerView recyclerView;
    private TextView tv_list;



    private Film film;
    private Serie serie;
    private Personne personne;
    private PersonneDetail personneDetail;
    private boolean boucleType=true;
    private Long idPersonneBoucle;

    private List<Personne> personnes = new ArrayList<Personne>();
    private List<KnownFor> knownFors = new ArrayList<KnownFor>();;

    private String type="";



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

    private BoucleCallBack boucleCallBack;
    public void setBoucleCallBack(BoucleCallBack boucle){
        this.boucleCallBack = boucle;
    }
    public interface BoucleCallBack{
        void goToBoucle();
    }




    public void setListPersonne(List<Personne> list) {
        personnes = list;
        recyclerView.setAdapter(getAdapterPersonnePetit());
        getAdapterPersonnePetit().updateAffichagePersonnePetit(list);
    }
    public void setListKnownFors(List<KnownFor> list) {
        knownFors = list;
        recyclerView.setAdapter(getAdapterKnownFor());
        getAdapterKnownFor().updateAffichageKnownFor(list);
    }



    private CustomAdapterPersonnePetit customAdapterPersonne = null;
    public CustomAdapterPersonnePetit getAdapterPersonnePetit(){
        if(customAdapterPersonne==null){
            customAdapterPersonne = new CustomAdapterPersonnePetit(personnes,this.getContext());
        }
        return customAdapterPersonne;
    }

    private CustomAdapterKnownFor customAdapterKnownFor = null;
    public CustomAdapterKnownFor getAdapterKnownFor(){
        if(customAdapterKnownFor==null){
            customAdapterKnownFor = new CustomAdapterKnownFor(knownFors,this.getContext());
        }
        return customAdapterKnownFor;
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
        tv_list = (TextView) v.findViewById(R.id.tv_details_list);

        getAdapterPersonnePetit();
        getAdapterKnownFor();

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_details_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {

                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    if(boucleType){
                        String test = knownFors.get(position).getMediaType();
                        if(test.equals("movie")){
                            afficherBoucle(knownFors.get(position),"0");
                            Log.i("test",knownFors.get(position).getTitle());
                        }
                        else {
                            afficherBoucle(knownFors.get(position),"1");
                        }
                    }
                    else {
                        afficherBoucle(personnes.get(position),"2");
                        personne = personnes.get(position);
                    }

                }
                else {
                    Toast.makeText(getActivity(), "Pas de connexion internet !", Toast.LENGTH_LONG).show();
                }
           }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        boucleCallBack.goToBoucle();

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
    private void afficherBoucle(Object o, String objStr){
        boucleCallBack.goToBoucle();
        GetResultDetails task = new GetResultDetails();
        task.setCallback(this);

        switch (objStr){
            case "0":
                KnownFor kFilm = (KnownFor) o;

                film = new Film(kFilm.getAdult(),kFilm.getBackdropPath(),kFilm.getGenreIds(),kFilm.getId(),kFilm.getOriginalLanguage(),kFilm.getOriginalTitle(),kFilm.getOverview(),kFilm.getPopularity(),kFilm.getPosterPath(),kFilm.getReleaseDate(),kFilm.getTitle(),kFilm.getVideo(),kFilm.getVoteAverage(),kFilm.getVoteCount());
                this.type = "0";

                task.execute(
                        "0",
                        film.getId()+""
                );
                break;
            case "1":
                KnownFor kSerie = (KnownFor) o;

                serie = new Serie(kSerie.getBackdropPath(),kSerie.getFirstAirDate(),kSerie.getGenreIds(),kSerie.getId(),kSerie.getName(),kSerie.getOriginCountry(),kSerie.getOriginalLanguage(),kSerie.getOriginalName(),kSerie.getOverview(),kSerie.getPopularity(),kSerie.getPosterPath(),kSerie.getVoteAverage(),kSerie.getVoteCount());
                this.type = "1";

                task.execute(
                        "1",
                        serie.getId()+""
                );
                break;
            case "2":
                personne = (Personne) o;
                this.type = "2";

                //Récupère l'ID pour faire une comparaison pour récupérer la liste de film pour laquelle la personne est connue
                idPersonneBoucle = personne.getId();

                //On doit lancer une lecture pour récupérer la liste de film pour laquelle la personne est connue
                GetResult get = new GetResult();
                get.setCallback(this);
                get.execute(
                        personne.getName(),
                        "2"
                );


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
                bt_bandeAnnonce.setText("Search Trailer");
                tv_list.setText("Actors : ");
                tv_titre.setText(film.getTitle());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+film.getPosterPath()).into(iv_Image);
                tv_date.setText(film.getReleaseDate());
                tv_genre.setText(EnumGenre.genresString(film.getGenreIds()));
                bt_bandeAnnonce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String date="";
                        if(!film.getReleaseDate().equals("")){
                            date = film.getReleaseDate().substring(0,4);
                        }

                        String url = YOUTUBE+film.getTitle()+"+"+date+"+trailer";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                tv_note.setText("Note : "+(double)Math.round(film.getVoteAverage() * 100) / 100d+" / 10");
                tv_description.setText(film.getOverview());

                break;

            case "1":
                bt_bandeAnnonce.setText("Search Trailer");
                tv_list.setText("Actors : ");
                tv_titre.setText(serie.getName());
                Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+serie.getPosterPath()).into(iv_Image);
                tv_date.setText(serie.getFirstAirDate());
                tv_genre.setText(EnumGenre.genresString(serie.getGenreIds()));
                bt_bandeAnnonce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String date="";
                        if(!serie.getFirstAirDate().equals("")){
                            date = serie.getFirstAirDate().substring(0,4);
                        }

                        String url =YOUTUBE+film.getTitle()+"+"+date+"+trailer";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                tv_note.setText("Note : "+(double)Math.round(serie.getVoteAverage() * 100) / 100d+" / 10");
                tv_description.setText(serie.getOverview());

                break;

            case "2":
                bt_bandeAnnonce.setText("More informations");
                tv_list.setText("Known for : ");

                tv_titre.setText(personneDetail.getName());

                if(personneDetail.getProfilePath()==null){
                    iv_Image.setImageResource(R.drawable.noimage);
                }
                else {
                    Picasso.with(this.getContext()).load("https://image.tmdb.org/t/p/original"+personneDetail.getProfilePath()).into(iv_Image);
                }

                String death=" , ";
                String birth="---Birthday not found---";
                if(personneDetail.getDeathday()!=null){
                    death += personneDetail.getDeathday();
                }
                else {
                    death="";
                }
                if(personneDetail.getBirthday()!=null){
                    birth = personneDetail.getBirthday();
                }
                tv_date.setText(birth+death);


                String genre="Man";
                if(personneDetail.getGender().equals(1L)){
                    genre = "Woman";
                }
                tv_genre.setText(genre);


                tv_note.setText("Popularity : "+(double)Math.round(personneDetail.getPopularity() * 100) / 100d+" / 10");


                if(personneDetail.getBiography().equals("")){
                    tv_description.setText("\n---Biography not found---");
                }
                else {
                    tv_description.setText(personneDetail.getBiography());
                }

                bt_bandeAnnonce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url="";
                        if(personneDetail.getHomepage()==null || personneDetail.getHomepage().equals("")) {
                            url = "https://www.google.be/search?&q=" + personneDetail.getName() + "+wikipedia";
                        }
                        else {
                            url = personneDetail.getHomepage();
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                break;
        }
    }




    @Override
    public void parseData(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        Type listType = new TypeToken<ArrayList<Personne>>() {}.getType();
        personnes = new Gson().fromJson(jsonObject.getJSONArray("results").toString(), listType);

        for(int i=0;i<personnes.size();i++){
            if(personnes.get(i).getId().equals(idPersonneBoucle)){
                Personne p = personnes.get(i);
                personne = new Personne(p.getAdult(),p.getId(),p.getKnownFor(),p.getName(),p.getPopularity(),p.getProfilePath());
            }
        }
    }
    @Override
    public void parseDataDetail(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        Type listType;

        if(type.equals("2")){
            personneDetail = new Gson().fromJson(jsonObject.toString(), PersonneDetail.class);
            this.setListKnownFors(personne.getKnownFor());
            initialiseDetails();
            boucleType =true;
        }
        else {
            listType = new TypeToken<ArrayList<Personne>>() {}.getType();
            personnes = new Gson().fromJson(jsonObject.getJSONArray("cast").toString(), listType);
            this.setListPersonne(personnes);
            initialiseDetails();
            boucleType =false;
        }
    }
}
