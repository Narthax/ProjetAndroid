package com.example.yoric.projet.utils;

import android.content.Context;
import android.widget.Toast;


import com.example.yoric.projet.model.User;
import com.example.yoric.projet.model.UserManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yoric on 28-10-17.
 */

public abstract class Serialisation {
    public static void writeJson(String file, String text){
        BufferedWriter BW = null;
        try {
            if(file.endsWith(".json")==false){
                file += ".json";
            }
            FileWriter FW = new FileWriter(file);
            BW = new BufferedWriter(FW);
            BW.write(text);
        }
        catch (IOException e) {
            //	e.printStackTrace();
        }
        finally {
            if(BW!=null){
                try {
                    BW.close();
                }
                catch (IOException e) {
                    //	e.printStackTrace();
                }
            }
        }
    }

    public static String readJson(String file){
        BufferedReader BR = null;
        String text="";
        try {
            if(file.endsWith(".json")){
                FileReader FR = new FileReader(file);
                BR = new BufferedReader(FR);

                String ligne;
                while(true){
                    ligne = BR.readLine();
                    if(ligne==null){
                        break;
                    }
                    text += ligne;
                }

                return text;
            }
            else {
                return text;
            }
        }
        catch (IOException e) {
            //	e.printStackTrace();
        }
        finally {
            if(BR!=null){
                try {
                    BR.close();
                }
                catch (IOException e) {
                    //	e.printStackTrace();
                }
            }
        }
        return text;
    }

    public static void initializationList(String type){

        if (type.equals("user")){
            String txt =Serialisation.readJson("user.json");
            Type collectionType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = new Gson().fromJson(txt, collectionType);

            if(userList!=null){
                for (User user : userList){
                    UserManagement.getInstance().addUser(user);
                }
            }

        }
    }

    public static String writeUser(){
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String txt ="";
        txt=gson.toJson(UserManagement.getInstance().getListUser());
        return txt;
    }
}
