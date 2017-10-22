package com.example.yoric.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yoric.projet.R;
import com.example.yoric.projet.model.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Simon on 26-09-17.
 */

public class CustomAdapterFilm extends RecyclerView.Adapter<CustomAdapterFilm.MyViewHolder> {
    private List<Film> list;
    private Context context;

    public void updateAffichage(List<Film> list){
        this.list.clear();
        this.list = list;
        this.notifyDataSetChanged();
    }

    public CustomAdapterFilm(List<Film> list, Context con){
        this.list = list;
        this.context=con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_list,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Film m = list.get(position);

        Picasso.with(this.context).load("https://image.tmdb.org/t/p/original"+m.getPosterPath()).into(holder.ivImage);
        holder.tvTitre.setText(m.getTitle());
        holder.tvDate.setText(m.getReleaseDate()+"");
        holder.ratingBar.setRating((Float.parseFloat(m.getVoteAverage().toString()))/2);
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

            ivImage = (ImageView) itemView.findViewById(R.id.iv_listFilm_image);
            tvTitre = (TextView) itemView.findViewById(R.id.tv_listFilm_titre);
            tvDate = (TextView) itemView.findViewById(R.id.tv_listFilm_date);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rb_listeFilm);
        }
    }
}

