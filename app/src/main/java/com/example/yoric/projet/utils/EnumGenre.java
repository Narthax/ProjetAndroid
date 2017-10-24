package com.example.yoric.projet.utils;

import java.util.List;

/**
 * Created by Kista on 23-10-17.
 */

public enum EnumGenre {
    Action(28,"Action"),
    Adventure(12, "Adventure"),
    Animation(16,"Animation"),
    Comedy(35,"Comedy"),
    Crime(80,"Crime"),
    Documentary(99,"Documentary"),
    Drama(18,"Drama"),
    Family(10751,"Family"),
    Fantasy(14,"Fantasy"),
    History(36,"History"),
    Horror(27,"Horror"),
    Music(10402,"Music"),
    Mystery(9648,"Mystery"),
    Romance(10749,"Romance"),
    Science_Fiction(878,"Science Fiction"),
    TV_Movie(10770,"TV Movie"),
    Thriller(53,"Thriller"),
    War(10752,"War"),
    Western(37,"Western")
    ;

    private int id=0;
    private String genre="";

    EnumGenre(int id, String genre){
        this.id = id;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return this.genre;
    }

    public int getId(){
        return this.id;
    }

    public static String genresString(List<Long> list){
        String tmp="";
        for(EnumGenre enumGenre : EnumGenre.values()){
            for(int i=0;i<list.size();i++) {
                if (list.get(i)== enumGenre.getId()) {
                    if(i!=list.size()-1){
                        tmp += enumGenre.toString()+", ";
                    }
                    else {
                        tmp += enumGenre.toString();
                    }
                }
            }
        }
        return tmp;
    }
}
