package com.example.yoric.projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.yoric.projet.fragments.FragmentList;
import com.example.yoric.projet.fragments.FragmentRecherche;


public class MainActivity extends AppCompatActivity implements FragmentList.ListCallBack, FragmentRecherche.RechercheCallBack{

    private FragmentRecherche fragmentRecherche = FragmentRecherche.getInstance();
    private FragmentList fragmentList = FragmentList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On crée le fragment film par défaut
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        fragmentRecherche.setRechercheCallBack(this);
        fragmentList.setListCallBack(this);
        transaction.add(R.id.fl_main_fragment_rechercher,fragmentRecherche);
        transaction.add(R.id.fl_main_fragment_list, fragmentList);
        transaction.commit();

    }

}
