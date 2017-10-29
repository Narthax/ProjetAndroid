package com.example.yoric.projet.model;

import java.io.Serializable;

/**
 * Created by yoric on 28-10-17.
 */

public class User implements Serializable{
    private String name, password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object o){
        if (o instanceof User){
            User user = (User)o;
            return this.name.equals(user.name);
        }
        return false;
    }

    public User clone(){
        return new User(this.name, this.password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

