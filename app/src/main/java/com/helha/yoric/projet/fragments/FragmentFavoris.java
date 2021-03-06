package com.helha.yoric.projet.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.helha.yoric.projet.MainActivity;
import com.helha.yoric.projet.R;
import com.helha.yoric.projet.adapter.CustomAdapterKnownFor;
import com.helha.yoric.projet.model.Film;
import com.helha.yoric.projet.model.KnownFor;
import com.helha.yoric.projet.model.Serie;
import com.helha.yoric.projet.utils.RecyclerItemClickListener;
import com.helha.yoric.projet.utils.Serialisation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kista on 31-10-17.
 */

public class FragmentFavoris extends Fragment {
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private TextView textViewTitre;

    private List<KnownFor> listFavoris = new ArrayList<>();
    public boolean addItemInFavoris(KnownFor knownFor){
        if(!listFavoris.contains(knownFor)){
            listFavoris.add(knownFor);
            getAdapterKnownFor().notifyDataSetChanged();
            Serialisation.writeJson(((MainActivity)getActivity()).getUser().getName()+".json",Serialisation.writeKnownFor(listFavoris),getActivity());
            return  true;
        }
        return false;
    }


    private static FragmentFavoris fragmentFavoris = null;
    public static FragmentFavoris getInstance() {
        if (fragmentFavoris == null) {
            fragmentFavoris = new FragmentFavoris();
        }
        return fragmentFavoris;
    }



    private CustomAdapterKnownFor customAdapterKnownFor = null;
    public CustomAdapterKnownFor getAdapterKnownFor(){
        if(customAdapterKnownFor==null){
            customAdapterKnownFor = new CustomAdapterKnownFor(listFavoris,this.getContext());
        }
        return customAdapterKnownFor;
    }

    private FragmentFavoris.FavorisCallBack favorisCallBack;
    public void setFavorisCallBack(FragmentFavoris.FavorisCallBack list){
        this.favorisCallBack = list;
    }
    public interface FavorisCallBack{
        void afficher(Object o, String type);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_favoris,container,false);

        imageButton = (ImageButton) v.findViewById(R.id.ib_favoris_info);
        textViewTitre = (TextView) v.findViewById(R.id.tv_favoris_titre);
        textViewTitre.setPaintFlags(textViewTitre.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        imageButton.setBackgroundColor(Color.parseColor("#e9e9e9"));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),getString(R.string.advice),Toast.LENGTH_LONG).show();
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_favoris_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        getAdapterKnownFor();
        recyclerView.setAdapter(getAdapterKnownFor());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                KnownFor k = listFavoris.get(position);
                if(k.getMediaType().equals("movie")){
                    Film film = new Film(k);
                    favorisCallBack.afficher(film,"0");
                }
                else {
                    if(k.getMediaType().equals("tv")) {
                        Serie serie = new Serie(k);
                        favorisCallBack.afficher(serie, "1");
                    }
                }
                ((MainActivity)getActivity()).hideFavoris();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                listFavoris.remove(position);
                getAdapterKnownFor().notifyDataSetChanged();
                Log.i("JSONNNNNNNNNNN",((MainActivity)getActivity()).getUser().getName());
                Serialisation.writeJson(((MainActivity)getActivity()).getUser().getName()+".json",Serialisation.writeKnownFor(listFavoris),getActivity());
            }
        }));

        return v;
    }

    public void initialisationFavoris(List<KnownFor>list){
        listFavoris.clear();
        if(list!=null){
            for (KnownFor knownFor : list){
                listFavoris.add(knownFor);
            }
        }
        getAdapterKnownFor().notifyDataSetChanged();
    }
}
