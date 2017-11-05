package com.helha.yoric.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helha.yoric.projet.R;
import com.helha.yoric.projet.model.Personne;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simon on 26-09-17.
 */

public class CustomAdapterPersonnePetit extends RecyclerView.Adapter<CustomAdapterPersonnePetit.MyViewHolder> {
    private List<Personne> list;
    private Context context;

    public void updateAffichagePersonnePetit(List<Personne> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public CustomAdapterPersonnePetit(List<Personne> list, Context con){
        this.list = list;
        this.context=con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_list_personne_petit,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Personne p = list.get(position);

        if(p.getProfilePath()==null){
            holder.ivImage.setImageResource(R.drawable.noimage);
        }
        else {
            Picasso.with(this.context).load("https://image.tmdb.org/t/p/original"+p.getProfilePath()).resize(150,200).into(holder.ivImage);
        }
        holder.tvNom.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImage;
        public TextView tvNom;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_listPersonne_imagePetit);
            tvNom = (TextView) itemView.findViewById(R.id.tv_listPersonne_nomPetit);
        }
    }
}

