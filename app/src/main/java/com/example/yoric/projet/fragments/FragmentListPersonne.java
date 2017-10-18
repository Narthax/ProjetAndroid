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
import com.example.yoric.projet.adapter.CustomAdapterPersonne;
import com.example.yoric.projet.model.Personne;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kista on 15-10-17.
 */

public class FragmentListPersonne extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapterPersonne adapterListePersonne;

    private static FragmentListPersonne fragmentListPersonne =null;
    public static FragmentListPersonne getInstance(){
        if(fragmentListPersonne ==null){
            fragmentListPersonne = new FragmentListPersonne();
        }
        return fragmentListPersonne;
    }



    private ListCallBack listCallBack;
    public void setListCallBack(ListCallBack list){
        this.listCallBack = list;
    }
    public interface ListCallBack{

    }



    private List<Personne> listeFragmentPersonne = new ArrayList<>();
    public void envoyerListe(List<Personne> list) {
        listeFragmentPersonne = list;
        update();
    }
    private void update(){
        adapterListePersonne.setList(listeFragmentPersonne);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_liste_personnes,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.list_layout_personnes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListePersonne = new CustomAdapterPersonne(listeFragmentPersonne,this.getContext());
        recyclerView.setAdapter(adapterListePersonne);

        return v;
    }
}
