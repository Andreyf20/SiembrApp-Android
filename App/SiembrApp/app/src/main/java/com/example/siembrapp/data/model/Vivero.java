package com.example.siembrapp.data.model;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Vivero implements Serializable {

    String nombre,direccion;
    ArrayList<String> telefonos;
    ArrayList<Pair<String,String>> horarios;

    public Vivero(ViveroBuilder builder){
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefonos = builder.telefonos;
        this.horarios = builder.horarios;
    }

    public String getTelefonosStr(){

        if(telefonos == null || telefonos.size() == 0){
            return "NO INDICA";
        }

        String telefonosStr= "";

        for (int i = 0; i < telefonos.size(); i++){

            telefonosStr = telefonosStr.concat(telefonos.get(i) + "\n");

        }

        return telefonosStr.substring(0,telefonosStr.length()-1); //Retornamos el string sin el ultimo caracter que es \n
    }

    public String getHorariosStr(){

        if(horarios == null || horarios.size() == 0){
            return "NO INDICA";
        }

        String horariosStr= "";

        for (int i = 0; i < horarios.size(); i++){

            Pair<String,String> horarioPair = horarios.get(i);

            horariosStr = horariosStr.concat(horarioPair.first +" / "+ horarioPair.second +"\n");

        }
        return horariosStr.substring(0,horariosStr.length()-1); //Retornamos el string sin el ultimo caracter que es \n
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

    public void setTelefonos(ArrayList<String> telefonos) {
        this.telefonos = telefonos;
    }

    public void setHorarios(ArrayList<Pair<String, String>> horarios){
        this.horarios = horarios;
    }

    public static class ViveroBuilder{

        private String nombre,direccion;

        private ArrayList<String> telefonos;
        private ArrayList<Pair<String,String>> horarios;

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
