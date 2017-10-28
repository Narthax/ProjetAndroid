package com.example.yoric.projet.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
}
