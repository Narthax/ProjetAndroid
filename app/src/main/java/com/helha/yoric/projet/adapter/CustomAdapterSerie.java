package com.helha.yoric.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.helha.yoric.projet.R;

import com.helha.yoric.projet.model.Serie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simon on 26-09-17.
 */

public class CustomAdapterSerie extends RecyclerView.Adapter<CustomAdapterSerie.MyViewHolder> {
    private List<Serie> list;
    private Context context;

    public void updateAffichage(List<Serie> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public CustomAdapterSerie(List<Serie> list, Context con){
        this.list = list;
        this.context=con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_list_film_serie,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Serie s = list.get(position);

        String date="";
        if(!s.getFirstAirDate().toString().equals("")){
            date = s.getFirstAirDate().substring(0,4);
        }
        holder.tvDate.setText(date);

        if(s.getPosterPath()==null){
            holder.ivImage.setImageResource(R.drawable.noimage);
        }
        else {
            Picasso.with(this.context).load("https://image.tmdb.org/t/p/original"+s.getPosterPath()).resize(200,300).into(holder.ivImage);
        }
        holder.tvTitre.setText(s.getName());
        holder.ratingBar.setRating((Float.parseFloat(s.getVoteAverage().toString()))/2);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImage;
        public TextView tvTitre;
        public TextView tvDate;
        public RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_listFilmSerie_image);
            tvTitre = (TextView) itemView.findViewById(R.id.tv_listFilmSerie_titre);
            tvDate = (TextView) itemView.findViewById(R.id.tv_listFilmSerie_date);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rb_listeFilmSerie_rating);
        }
    }
}

