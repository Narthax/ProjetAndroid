package com.helha.yoric.projet.utils;

import java.util.List;

/**
 * Created by Kista on 23-10-17.
 */

public enum EnumGenre {
    Action(28,"Action"),
    Adventure(12, "Aventure"),
    Animation(16,"Animation"),
    Comedy(35,"Comédie"),
    Crime(80,"Crime"),
    Documentary(99,"Documentaire"),
    Drama(18,"Drame"),
    Family(10751,"Familial"),
    Fantasy(14,"Fantastique"),
    History(36,"Histoire"),
    Horror(27,"Horreur"),
    Music(10402,"Musique"),
    Mystery(9648,"Mystère"),
    Romance(10749,"Romance"),
    Science_Fiction(878,"Science-Fiction"),
    TV_Movie(10770,"Série"),
    Thriller(53,"Thriller"),
    War(10752,"Guerre"),
    Western(37,"Western")
    ;

    private int id;
    private String genre;

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
        for(int i=0;i<list.size();i++) {
            for(EnumGenre enumGenre : EnumGenre.values()){
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

        if(tmp.length()>2) {
            if (tmp.substring(tmp.length() - 2, tmp.length()).equals(", ")) {
                tmp = tmp.substring(0, tmp.length() - 2);
            }
        }
        return tmp;
    }
}
