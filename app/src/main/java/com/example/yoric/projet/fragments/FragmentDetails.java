package com.example.yoric.projet.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yoric.projet.R;
import com.example.yoric.projet.adapter.CustomAdapterFilm;

/**
 * Created by Kista on 22-10-17.
 */

public class FragmentDetails extends Fragment {
    private String type = "0";
    private TextView tv_description;
   // private TextView tv_

    private static FragmentDetails fragmentDetails = null;

    public static FragmentDetails getInstance() {
        if (fragmentDetails == null) {
            fragmentDetails = new FragmentDetails();
        }
        return fragmentDetails;
    }


    private FragmentDetails.DetailsCallBack detailsCallBack;

    public void setDetailsCallBack(FragmentDetails.DetailsCallBack detail) {
        this.detailsCallBack = detail;
    }

    public interface DetailsCallBack {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_details,container,false);



        return v;
    }
}
