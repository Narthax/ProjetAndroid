package com.example.yoric.projet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yoric.projet.R;
import com.example.yoric.projet.model.Personne;

import java.util.List;

/**
 * Created by Simon on 26-09-17.
 */

public class CustomAdapterPersonne extends RecyclerView.Adapter<CustomAdapterPersonne.MyViewHolder> {
    private List<Personne> list;
    private Context context;

    public void setList(List<Personne> list){
        this.list.clear();
        this.list = list;
        this.notifyDataSetChanged();
    }

    public CustomAdapterPersonne(List<Personne> list, Context con){
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
        Personne p = list.get(position);

        holder.ivImage.setImageResource(R.drawable.a);
        holder.tvTitre.setText(p.getName());
        holder.tvReal.setText(p.getId()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivImage;
        public TextView tvTitre;
        public TextView tvReal;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_listFilm_image);
            tvTitre = (TextView) itemView.findViewById(R.id.tv_listFilm_titre);
            tvReal = (TextView) itemView.findViewById(R.id.tv_listFilm_realisateur);
        }
    }
}

