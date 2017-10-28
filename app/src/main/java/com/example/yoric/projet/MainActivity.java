package com.example.yoric.projet;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.yoric.projet.fragments.FragmentConnexion;
import com.example.yoric.projet.fragments.FragmentDetails;
import com.example.yoric.projet.fragments.FragmentInscription;
import com.example.yoric.projet.fragments.FragmentList;
import com.example.yoric.projet.fragments.FragmentRecherche;
import com.example.yoric.projet.model.User;
import com.example.yoric.projet.utils.Serialisation;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity implements FragmentRecherche.RechercheCallBack, FragmentDetails.DetailsCallBack, FragmentDetails.BoucleCallBack,FragmentConnexion.ConnexionCallBack, FragmentInscription.InscriptionCallBack
{

    private FragmentRecherche fragmentRecherche = FragmentRecherche.getInstance();
    private FragmentList fragmentList = FragmentList.getInstance();
    private FragmentDetails fragmentDetails = FragmentDetails.getInstance();
    private FragmentConnexion fragmentConnexion = FragmentConnexion.getInstance();
    private FragmentInscription fragmentInscription = FragmentInscription.getInstance();

    private Menu m = null;
    private User user = null;

    public Menu getMenu(){
        return m;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User newUser){
        user = newUser;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        m = menu;
        menu.getItem(3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        switch (item.getItemId()){
            case R.id.menu_accueil:
                transaction = getFragmentManager().beginTransaction();
                transaction.remove(fragmentDetails);
                transaction.remove(fragmentConnexion);
                transaction.remove(fragmentInscription);
                transaction.show(fragmentRecherche);
                transaction.show(fragmentList);
                m.getItem(1).setVisible(true);
                m.getItem(2).setVisible(true);
                transaction.commit();
                return true;
            case R.id.menu_connect:
                transaction = getFragmentManager().beginTransaction();
                transaction.remove(fragmentInscription);
                transaction.remove(fragmentDetails);
                transaction.add(R.id.fl_main_fragment_connexion,fragmentConnexion);
                transaction.hide(fragmentList);
                transaction.hide(fragmentRecherche);
                m.getItem(1).setVisible(false);
                m.getItem(2).setVisible(true);
                transaction.commit();
                return true;
            case R.id.menu_inscription:
                transaction = getFragmentManager().beginTransaction();
                transaction.remove(fragmentConnexion);
                transaction.remove(fragmentDetails);
                transaction.add(R.id.fl_main_fragment_inscription,fragmentInscription);
                transaction.hide(fragmentList);
                transaction.hide(fragmentRecherche);
                m.getItem(2).setVisible(false);
                m.getItem(1).setVisible(true);
                transaction.commit();
                return true;
            case R.id.menu_deconnection:
                user =null;
                m.getItem(2).setVisible(true);
                m.getItem(1).setVisible(true);
                m.getItem(3).setVisible(false);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentRecherche.setRechercheCallBack(this);
        fragmentList.setListCallBack(fragmentDetails);
        fragmentDetails.setDetailsCallBack(this);
        fragmentDetails.setBoucleCallBack(this);
        fragmentConnexion.setCallBack(this);
        fragmentInscription.setCallBack(this);


        Serialisation.initializationList("user");



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
    public void goToBoucle() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main_fragment_details,fragmentDetails);
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

    public  void goHome(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(fragmentDetails);
        transaction.remove(fragmentConnexion);
        transaction.remove(fragmentInscription);
        transaction.show(fragmentRecherche);
        transaction.show(fragmentList);
        FragmentConnexion.getInstance().setFragmentConnexion();
        transaction.commit();
    }

    public  void goConnexion(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(fragmentDetails);
        transaction.remove(fragmentInscription);
        transaction.add(R.id.fl_main_fragment_connexion,fragmentConnexion);
        FragmentInscription.getInstance().setFragmentInscription();
        m.getItem(1).setVisible(false);
        m.getItem(2).setVisible(true);
        transaction.commit();
    }
}