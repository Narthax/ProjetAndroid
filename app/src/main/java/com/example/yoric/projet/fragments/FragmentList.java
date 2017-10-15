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
import com.example.yoric.projet.adapter.CustomAdapter;
import com.example.yoric.projet.model.ListeFilm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kista on 15-10-17.
 */

public class FragmentList extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapter adapterListe;

    private static FragmentList fragmentList=null;
    public static FragmentList getInstance(){
        if(fragmentList==null){
            fragmentList = new FragmentList();
        }
        return fragmentList;
    }



    private ListCallBack listCallBack;
    public void setListCallBack(ListCallBack list){
        this.listCallBack = list;
    }
    public interface ListCallBack{
        ListeFilm infoFilm(ListeFilm film);
    }



    private List<ListeFilm> listeFragment = new ArrayList<>();
    public void envoyerListe(List<ListeFilm> list) {
        this.listeFragment = list;
        update();
    }
    private void update(){
        adapterListe.setList(listeFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_liste_films,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListe = new CustomAdapter(listeFragment,this.getContext());
        recyclerView.setAdapter(adapterListe);

        return v;
    }
}
