package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;
import com.example.yoric.projet.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kista on 15-10-17.
 */

public class FragmentListFilm extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapterFilm adapterListeFilm;

    private static FragmentListFilm fragmentListFilm =null;
    public static FragmentListFilm getInstance(){
        if(fragmentListFilm ==null){
            fragmentListFilm = new FragmentListFilm();
        }
        return fragmentListFilm;
    }



    private ListCallBack listCallBack;
    public void setListCallBack(ListCallBack list){
        this.listCallBack = list;
    }
    public interface ListCallBack{

    }



    private List<Film> listeFragmentFilm = new ArrayList<>();
    public void envoyerListe(List<Film> list) {
        listeFragmentFilm = list;
        update();
    }
    private void update(){
        adapterListeFilm.setList(listeFragmentFilm);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_liste_films,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_layout_films);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListeFilm = new CustomAdapterFilm(listeFragmentFilm,this.getContext());
        recyclerView.setAdapter(adapterListeFilm);

        return v;
    }
}
