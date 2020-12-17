package com.example.siembrapp.data.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

    private String nombre, correo, nombreOrganizacion, razon;

    ArrayList<Planta> plantas;

    private User(UserBuilder builder){
        this.nombre = builder.nombre;
        this.correo = builder.correo;
        this.nombreOrganizacion = builder.nombreOrganizacion;
        this.razon = builder.razon;
        plantas = new ArrayList<>();
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

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public static class UserBuilder{
        private String nombre, correo, nombreOrganizacion, razon;

        public UserBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UserBuilder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public UserBuilder setNombreOrganizacion(String nombreOrganizacion) {
            this.nombreOrganizacion = nombreOrganizacion;
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
