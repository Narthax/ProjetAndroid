package com.example.yoric.projet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoric on 28-10-17.
 */

public class UserManagement implements Serializable {
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
}
