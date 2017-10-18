package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterSerie;
import com.example.yoric.projet.model.Serie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kista on 15-10-17.
 */

public class FragmentListSerie extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapterSerie adapterListeSerie;

    private static FragmentListSerie fragmentListSerie =null;
    public static FragmentListSerie getInstance(){
        if(fragmentListSerie ==null){
            fragmentListSerie = new FragmentListSerie();
        }
        return fragmentListSerie;
    }



    private ListCallBack listCallBack;
    public void setListCallBack(ListCallBack list){
        this.listCallBack = list;
    }
    public interface ListCallBack{

    }



    private List<Serie> listeFragmentSerie = new ArrayList<>();
    public void envoyerListe(List<Serie> list) {
        listeFragmentSerie = list;
        update();
    }
    public void update(){
        adapterListeSerie.setList(listeFragmentSerie);
    }


    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_liste_series,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_layout_series);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListeSerie = new CustomAdapterSerie(listeFragmentSerie,this.getContext());
        recyclerView.setAdapter(adapterListeSerie);

        return v;
    }

}
