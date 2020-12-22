package com.example.siembrapp.data.model;

public class Vivero {

    String nombre,direccion,telefono,horaInicio,horaFin,dias;

    public Vivero(ViveroBuilder builder){
        this.nombre = builder.nombre;
        this.direccion = builder.direccion;
        this.telefono = builder.telefono;
        this.horaInicio = builder.horaInicio;
        this.horaFin = builder.horaFin;
        this.dias = builder.dias;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getDias() {
        return dias;
    }

    public static class ViveroBuilder{

        String nombre,direccion,telefono,horaInicio,horaFin,dias;

        public ViveroBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ViveroBuilder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public ViveroBuilder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public ViveroBuilder setHoraInicio(String horaInicio) {
            this.horaInicio = horaInicio;
            return this;
        }

        public ViveroBuilder setHoraFin(String horaFin) {
            this.horaFin = horaFin;
            return this;
        }

        public ViveroBuilder setDias(String dias) {
            this.dias = dias;
            return this;
        }
    }
}
