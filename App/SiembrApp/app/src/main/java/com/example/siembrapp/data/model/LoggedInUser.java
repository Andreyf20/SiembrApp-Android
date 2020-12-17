package com.example.siembrapp.data.model;

import java.util.ArrayList;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String correo;
    private String uuid;
    private String nombre;

    private ArrayList<Planta> plantasUsuario;

    public String getCorreo() {
        return correo;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPlantas(ArrayList<Planta> plantas){
        this.plantasUsuario = plantas;
    }

    public ArrayList<Planta> getPlantasUsuario(){
        return plantasUsuario;
    }

    public LoggedInUser(LoggedInUserBuilder builder) {
        this.correo = builder.correo;
        this.uuid = builder.uuid;
        this.nombre = builder.nombre;
    }

    public static class LoggedInUserBuilder {

        private String correo;
        private String uuid;
        private String nombre;

        public LoggedInUserBuilder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public LoggedInUserBuilder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public LoggedInUserBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public LoggedInUser build(){
            return new LoggedInUser(this);
        }
    }

    public static class LoggedUser{

        private static LoggedInUser loggedUser;

        public static LoggedInUser getLoggedUser() {
            return loggedUser;
        }

        public static void setLoggedUser(LoggedInUser loggedUser) {
            LoggedUser.loggedUser = loggedUser;
        }
    }
}