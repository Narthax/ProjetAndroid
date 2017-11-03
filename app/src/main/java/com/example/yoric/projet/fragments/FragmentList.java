package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yoric.projet.MainActivity;
import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;
import com.example.yoric.projet.adapter.CustomAdapterPersonne;
import com.example.yoric.projet.adapter.CustomAdapterSerie;
import com.example.yoric.projet.model.Film;
import com.example.yoric.projet.model.KnownFor;
import com.example.yoric.projet.model.Personne;
import com.example.yoric.projet.model.Serie;
import com.example.yoric.projet.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kista on 15-10-17.
 */

public class FragmentList extends Fragment {
    private RecyclerView recyclerView;
    private String type = "0";
    private FragmentFavoris fragmentFavoris = FragmentFavoris.getInstance();


    private static FragmentList fragmentList =null;
    public static FragmentList getInstance(){
        if(fragmentList ==null){
            fragmentList = new FragmentList();
        }
        return fragmentList;
    }



    private ListCallBack listCallBack;
    public void setListCallBack(ListCallBack list){
        this.listCallBack = list;
    }
    public interface ListCallBack{
        void afficher(Object o, String type);
    }



    private List<Film> listeFragmentFilm = new ArrayList<>();
    private List<Serie> listeFragmentSerie = new ArrayList<>();
    private List<Personne> listeFragmentPersonne = new ArrayList<>();

    public void setListeFilm(List<Film> list) {
        listeFragmentFilm = list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(getAdapterListeFilm());
        getAdapterListeFilm().updateAffichage(list);
        type = "0";
    }
    public void setListeSerie(List<Serie> list) {
        listeFragmentSerie = list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(getAdapterListeSerie());
        getAdapterListeSerie().updateAffichage(list);
        type = "1";
    }
    public void setListePersonne(List<Personne> list) {
        listeFragmentPersonne = list;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(getAdapterListePersonne());
        getAdapterListePersonne().updateAffichage(list);
        type = "2";
    }



    private CustomAdapterFilm customAdapterFilm = null;
    private CustomAdapterSerie customAdapterSerie = null;
    private CustomAdapterPersonne customAdapterPersonne = null;

    public CustomAdapterFilm getAdapterListeFilm(){
        if(customAdapterFilm==null){
            customAdapterFilm = new CustomAdapterFilm(listeFragmentFilm,this.getContext());
        }
        return customAdapterFilm;
    }
    public CustomAdapterSerie getAdapterListeSerie(){
        if(customAdapterSerie==null){
            customAdapterSerie = new CustomAdapterSerie(listeFragmentSerie,this.getContext());
        }
        return customAdapterSerie;
    }
    public CustomAdapterPersonne getAdapterListePersonne(){
        if(customAdapterPersonne==null){
            customAdapterPersonne = new CustomAdapterPersonne(listeFragmentPersonne,this.getContext());
        }
        return customAdapterPersonne;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_liste,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_list_layout);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    switch (type){
                        case "0": listCallBack.afficher(listeFragmentFilm.get(position),type);
                            break;
                        case "1": listCallBack.afficher(listeFragmentSerie.get(position),type);
                            break;
                        case "2": listCallBack.afficher(listeFragmentPersonne.get(position),type);
                            break;
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Pas de connexion internet !", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(((MainActivity) getActivity()).getUser() != null) {
                    KnownFor filmOuSerie;
                    boolean isFavoris;
                    String typeChar = "";
                    if (!type.equals("2")) {
                        if (type.equals("0")) {
                            filmOuSerie = new KnownFor(listeFragmentFilm.get(position));
                            typeChar = filmOuSerie.getTitle();
                        }
                        else {
                            filmOuSerie = new KnownFor(listeFragmentSerie.get(position));
                            typeChar = filmOuSerie.getName();
                        }

                        isFavoris = fragmentFavoris.addItemInFavoris(filmOuSerie);
                        if (isFavoris == true) {
                            Toast.makeText(getActivity(), "\""+typeChar + "\" a été ajouté dans vos favoris", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "\""+typeChar + "\" est déjà dans vos favoris", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Vous ne pouvez pas ajouter des personnes dans vos favoris", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Vous devez être connecté pour ajouter des films/séries dans vos favoris", Toast.LENGTH_LONG).show();
                }
            }
        }));

        //On crée les adapters, sinon bug au premier affichage
        getAdapterListeFilm();
        getAdapterListeSerie();
        getAdapterListePersonne();

        return v;
    }

    public void clearAllListes(){
        listeFragmentFilm.clear();
        listeFragmentSerie.clear();
        listeFragmentPersonne.clear();

        getAdapterListeFilm().notifyDataSetChanged();
        getAdapterListeSerie().notifyDataSetChanged();
        getAdapterListePersonne().notifyDataSetChanged();
    }
}