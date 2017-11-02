package com.example.yoric.projet;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.yoric.projet.fragments.FragmentConnexion;
import com.example.yoric.projet.fragments.FragmentDetails;
import com.example.yoric.projet.fragments.FragmentFavoris;
import com.example.yoric.projet.fragments.FragmentInscription;
import com.example.yoric.projet.fragments.FragmentList;
import com.example.yoric.projet.fragments.FragmentRecherche;
import com.example.yoric.projet.model.User;
import com.example.yoric.projet.model.UserManagement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FragmentFavoris.FavorisCallBack, FragmentRecherche.RechercheCallBack, FragmentDetails.DetailsCallBack, FragmentDetails.BoucleCallBack,FragmentConnexion.ConnexionCallBack, FragmentInscription.InscriptionCallBack
{
    private FragmentRecherche fragmentRecherche = FragmentRecherche.getInstance();
    private FragmentList fragmentList = FragmentList.getInstance();
    private FragmentDetails fragmentDetails = FragmentDetails.getInstance();
    private FragmentConnexion fragmentConnexion = FragmentConnexion.getInstance();
    private FragmentInscription fragmentInscription = FragmentInscription.getInstance();
    private FragmentFavoris fragmentFavoris = FragmentFavoris.getInstance();

    private Menu m = null;
    public Menu getMenu(){
        return m;
    }

    private User user = null;
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
        menu.getItem(1).setVisible(false);
        menu.getItem(4).setVisible(false);
        menu.getItem(5).setVisible(false);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        switch (item.getItemId()){
            case R.id.menu_accueil:
                onBackPressed();
                fragmentList.clearAllListes();
                fragmentRecherche.setTextToNull();
                return true;
            case R.id.menu_connect:
                transaction = getFragmentManager().beginTransaction();
                    transaction.remove(fragmentInscription);
                    transaction.remove(fragmentDetails);
                    transaction.add(R.id.fl_main_fragment_connexion,fragmentConnexion);
                    transaction.hide(fragmentList);
                    transaction.hide(fragmentRecherche);
                    m.getItem(2).setVisible(false);
                    m.getItem(3).setVisible(true);
                transaction.commit();
                return true;
            case R.id.menu_inscription:
                transaction = getFragmentManager().beginTransaction();
                    transaction.remove(fragmentConnexion);
                    transaction.remove(fragmentDetails);
                    transaction.add(R.id.fl_main_fragment_inscription,fragmentInscription);
                    transaction.hide(fragmentList);
                    transaction.hide(fragmentRecherche);
                    m.getItem(3).setVisible(false);
                    m.getItem(2).setVisible(true);
                transaction.commit();
                return true;
            case R.id.menu_deconnection:
                user =null;
                onBackPressed();
                return true;
            case R.id.menu_favoris:
                transaction = getFragmentManager().beginTransaction();
                    transaction.remove(fragmentDetails);
                    transaction.show(fragmentFavoris);
                    transaction.hide(fragmentList);
                    transaction.hide(fragmentRecherche);
                transaction.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("YosiFlix");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentRecherche.setRechercheCallBack(this);
        fragmentList.setListCallBack(fragmentDetails);
        fragmentDetails.setDetailsCallBack(this);
        fragmentDetails.setBoucleCallBack(this);
        fragmentConnexion.setCallBack(this);
        fragmentInscription.setCallBack(this);
        fragmentFavoris.setFavorisCallBack(this);

        initializationList("user");

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main_fragment_rechercher,fragmentRecherche);
            transaction.add(R.id.fl_main_fragment_list, fragmentList);

            transaction.add(R.id.fl_main_fragment_favoris,fragmentFavoris);
            transaction.hide(fragmentFavoris);
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
            transaction.remove(fragmentConnexion);
            transaction.remove(fragmentInscription);
            transaction.hide(fragmentFavoris);
            transaction.show(fragmentRecherche);
            transaction.show(fragmentList);
        transaction.commit();

        if(user==null){
            m.getItem(1).setVisible(false);
            m.getItem(2).setVisible(true);
            m.getItem(3).setVisible(true);
            m.getItem(4).setVisible(false);
            m.getItem(5).setVisible(false);
        }
        else {
            m.getItem(1).setVisible(true);
            m.getItem(2).setVisible(false);
            m.getItem(3).setVisible(false);
            m.getItem(4).setVisible(true);
            m.getItem(5).setVisible(true);
        }
    }



    public void goHome(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragmentDetails);
            transaction.remove(fragmentConnexion);
            transaction.remove(fragmentInscription);
            transaction.show(fragmentRecherche);
            transaction.show(fragmentList);
            FragmentConnexion.getInstance().setFragmentConnexion();
        transaction.commit();
    }
    public void goConnexion(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(fragmentDetails);
            transaction.remove(fragmentInscription);
            transaction.add(R.id.fl_main_fragment_connexion,fragmentConnexion);
            FragmentInscription.getInstance().setFragmentInscription();
            m.getItem(1).setVisible(false);
            m.getItem(2).setVisible(true);
        transaction.commit();
    }



    public String readJson(String file){
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(openFileInput(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public void initializationList(String type){
        if (type.equals("user")){
            String txt =readJson("User.json");
            Log.i("USER-JSON",txt);
            Type collectionType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = new Gson().fromJson(txt, collectionType);

            if(userList!=null){
                for (User user : userList){
                    UserManagement.getInstance().addUser(user);
                }
            }
        }
    }
    public static void hideSoftKeyboard (Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}