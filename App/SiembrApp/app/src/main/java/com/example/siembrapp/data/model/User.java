package com.example.siembrapp.data.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

    private String uuid, nombre, correo, tipoOrganizacion, razon;

    ArrayList<Planta> plantas;

    private User(UserBuilder builder){
        this.nombre = builder.uuid;
        this.nombre = builder.nombre;
        this.correo = builder.correo;
        this.tipoOrganizacion = builder.tipoOrganizacion;
        this.razon = builder.razon;
        this.uuid = builder.uuid;
        plantas = new ArrayList<>();
    }

    public String getUuid(){
        return this.uuid;
    }

    public void agregarPlanta(Planta nuevaPlanta){
        this.plantas.add(nuevaPlanta);
    }

    public void setPlantas(ArrayList<Planta> plantas){
        this.plantas = plantas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public static class UserBuilder{
        private String uuid, nombre, correo, tipoOrganizacion, razon;

        public UserBuilder setUUID(String uuid){
            this.uuid = uuid;
            return this;
        }

        public UserBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UserBuilder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public UserBuilder setTipoOrganizacion(String tipoOrganizacion) {
            this.tipoOrganizacion = tipoOrganizacion;
            return this;
        }

        public UserBuilder setRazon(String razon) {
            this.razon = razon;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
