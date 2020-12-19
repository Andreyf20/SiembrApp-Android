package com.example.siembrapp.data.model;

import java.util.ArrayList;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    public static class LoggedUser{

        private static User loggedUser;

        public static User getLoggedUser(){
            return loggedUser;
        }

        public static void setLoggedUser(User newLoggedUser){
            loggedUser = newLoggedUser;
        }
    }

}