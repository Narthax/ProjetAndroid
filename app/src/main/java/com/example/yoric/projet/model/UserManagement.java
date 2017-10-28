package com.example.yoric.projet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoric on 28-10-17.
 */

public class UserManagement {
    private static UserManagement instance = null;
    private List<User> listUser;

    private UserManagement() {
        listUser = new ArrayList<>();
    }

    public boolean addUser(User user){
        if (!listUser.contains(user)){
            listUser.add(user);
            return true;
        }else{
            return false;
        }
    }

    public static UserManagement getInstance() {
        if (instance == null) {
            instance = new UserManagement();
        }
        return instance;
    }

    public List<User> getListUser() {
        List<User> result = new ArrayList<>();
        for(User user : listUser){
            result.add(user.clone());
        }
        return listUser;
    }

/*
    public User getUser(String mail){
        for (int i=0;i<listUser.size();i++){
            if (listUser.get(i).getMail().equals(mail)){
                return listUser.get(i);
            }
        }
        return null;
    }*/
}
