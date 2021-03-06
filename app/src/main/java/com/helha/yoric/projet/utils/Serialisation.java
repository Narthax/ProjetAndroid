package com.helha.yoric.projet.utils;

import android.content.Context;

import com.helha.yoric.projet.model.KnownFor;
import com.helha.yoric.projet.model.UserManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.BufferedWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;


/**
 * Created by yoric on 28-10-17.
 */

public abstract class Serialisation {
    public static void writeJson(String file, String text, Context context){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(file,Context.MODE_PRIVATE)));
            writer.write(text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
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

    public static String writeKnownFor(List<KnownFor> knownForList){
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String txt ="";
        txt=gson.toJson(knownForList);
        return txt;
    }

}
