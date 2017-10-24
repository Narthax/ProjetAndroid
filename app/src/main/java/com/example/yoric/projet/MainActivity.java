package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yoric.projet.fragments.FragmentDetails;
import com.example.yoric.projet.fragments.FragmentList;
import com.example.yoric.projet.fragments.FragmentRecherche;


public class MainActivity extends AppCompatActivity implements FragmentRecherche.RechercheCallBack, FragmentDetails.DetailsCallBack{

    private FragmentRecherche fragmentRecherche = FragmentRecherche.getInstance();
    private FragmentList fragmentList = FragmentList.getInstance();
    private FragmentDetails fragmentDetails = FragmentDetails.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentRecherche.setRechercheCallBack(this);
        fragmentList.setListCallBack(fragmentDetails);
        fragmentDetails.setDetailsCallBack(this);

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main_fragment_rechercher,fragmentRecherche);
            transaction.add(R.id.fl_main_fragment_list, fragmentList);
        transaction.commit();
    }

    @Override
    public void afficherDetails() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main_fragment_details,fragmentDetails);
            transaction.hide(fragmentList);
            transaction.hide(fragmentRecherche);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragmentDetails);
            transaction.show(fragmentRecherche);
            transaction.show(fragmentList);
        transaction.commit();
    }
}