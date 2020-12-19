package com.example.siembrapp.data.model;

import android.content.SharedPreferences;

/**
 * Clase que retiene el usuario loggeado
 */
public class Session {

    private static User loggedUser;

    public static User getLoggedUser(){
        return loggedUser;
    }

    public static void setLoggedUser(User newLoggedUser){
        loggedUser = newLoggedUser;
    }

}