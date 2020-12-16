package com.example.siembrapp.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    public static LoggedInUser loggedUser;

    public static LoggedInUser getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(LoggedInUser loggedUser) {
        LoggedInUser.loggedUser = loggedUser;
    }

    private String correo;
    private String uuid;
    private String nombre;

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

}