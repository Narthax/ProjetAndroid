package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;
import com.example.yoric.projet.adapter.CustomAdapterPersonne;
import com.example.yoric.projet.adapter.CustomAdapterSerie;
import com.example.yoric.projet.model.Film;
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
    private CustomAdapterFilm adapterListeFilm;
    private String type = "0";

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
        
    }



    private List<Film> listeFragmentFilm = new ArrayList<>();
    private List<Serie> listeFragmentSerie = new ArrayList<>();
    private List<Personne> listeFragmentPersonne = new ArrayList<>();

    public void setListeFilm(List<Film> list) {
        listeFragmentFilm = list;
        recyclerView.setAdapter(getAdapterListeFilm());
        getAdapterListeFilm().updateAffichage(list);
        type = "0";
    }
    public void setListeSerie(List<Serie> list) {
        listeFragmentSerie = list;
        recyclerView.setAdapter(getAdapterListeSerie());
        getAdapterListeSerie().updateAffichage(list);
        type = "1";
    }
    public void setListePersonne(List<Personne> list) {
        listeFragmentPersonne = list;
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

        recyclerView = (RecyclerView) v.findViewById(R.id.list_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                switch (type){
                    case "0" : Film f = listeFragmentFilm.get(position);break;
                    case "1" : Serie s = listeFragmentSerie.get(position);break;
                    case "2" : Personne p = listeFragmentPersonne.get(position);break;
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //On crée les adapters, sinon bug au premier affichage
        getAdapterListeFilm();
        getAdapterListeSerie();
        getAdapterListePersonne();

        return v;
    }
}
