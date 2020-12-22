package com.example.siembrapp.data.model;

import android.util.Pair;

import java.util.ArrayList;

public class Vivero {

    String nombre,direccion;
    ArrayList<String> telefonos;
    ArrayList<Pair<String,String>> horarios;

    public Vivero(ViveroBuilder builder){
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefonos = builder.telefonos;
        this.horarios = builder.horarios;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public ArrayList<Pair<String, String>> getHorarios() {
        return horarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public static class ViveroBuilder{

        String nombre,direccion;

        ArrayList<String> telefonos;
        ArrayList<Pair<String,String>> horarios;

        public ViveroBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ViveroBuilder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public ViveroBuilder setTelefonos(ArrayList<String> telefonos) {
            this.telefonos = telefonos;
            return this;
        }

        public ViveroBuilder setHorarios(ArrayList<Pair<String, String>> horarios) {
            this.horarios = horarios;
            return this;
        }

        public Vivero build(){
            return new Vivero(this);
        }
    }
}
