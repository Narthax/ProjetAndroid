package com.example.yoric.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoric.projet.R;
import com.example.yoric.projet.model.KnownFor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 26-09-17.
 */

public class CustomAdapterKnownFor extends RecyclerView.Adapter<CustomAdapterKnownFor.MyViewHolder> {
    private List<KnownFor> list;
    private Context context;

    public void updateAffichage(List<KnownFor> list){
        List<KnownFor> var  = new ArrayList<>();
        var.addAll(list);

        this.list.clear();
        this.list = var;
        this.notifyDataSetChanged();
    }

    public CustomAdapterKnownFor(List<KnownFor> list, Context con){
        this.list = list;
        this.context=con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_known_for,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KnownFor k = list.get(position);

        String date="";
        if(!k.getReleaseDate().toString().equals("")){
            date = k.getReleaseDate().substring(0,4);
        }

        if(k.getPosterPath()==null){
            holder.ivImage.setImageResource(R.drawable.noimage);
        }
        else {
            Picasso.with(this.context).load("https://image.tmdb.org/t/p/original"+k.getPosterPath()).into(holder.ivImage);
        }
        holder.tvTitre.setText(k.getTitle()+" ("+date+")");

        String type="";
        if(k.getMediaType()=="movie"){
            type = "Film";
        }
        else {
            type = "Série";
        }
        holder.tvType.setText(type);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImage;
        public TextView tvTitre;
        public TextView tvType;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_known_for_image);
            tvTitre = (TextView) itemView.findViewById(R.id.tv_known_for_titre);
            tvType = (TextView) itemView.findViewById(R.id.tv_known_for_type);
        }
    }
}
