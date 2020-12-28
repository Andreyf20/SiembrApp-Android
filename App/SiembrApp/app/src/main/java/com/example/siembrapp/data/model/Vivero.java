package com.example.siembrapp.data.model;

import java.io.Serializable;

public class Vivero implements Serializable {

    String nombre,direccion,telefonos,horarios;

    public Vivero(ViveroBuilder builder){
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefonos = builder.telefonos;
        this.horarios = builder.horarios;
    }


    public String getTelefonos() {
        return telefonos;
    }

    public String getHorarios() {
        return horarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }


    public static class ViveroBuilder{

        private String nombre,direccion,telefonos,horarios;

        public ViveroBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ViveroBuilder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public ViveroBuilder setTelefonos(String telefonos) {
            this.telefonos = telefonos;
            return this;
        }

        public ViveroBuilder setHorarios(String horarios) {
            this.horarios = horarios;
            return this;
        }

        public Vivero build(){
            return new Vivero(this);
        }
    }
}
