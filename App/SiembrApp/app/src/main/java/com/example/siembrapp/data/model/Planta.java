package com.example.siembrapp.data.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;

public class Planta implements Serializable {

    private String nombreComun,nombreCientifico,familia,origen,fenologia,agentePolinizador,requerimientosDeLuz,metodoDispersion,fruto,texturaFruto,flor,habito,imagen;
    private ArrayList<String> usosConocidos,paisajeRecomendado;
    private double minRangoAltitudinal,maxRangoAltitudinal,metros;

    private Planta(PlantaBuilder builder){
        this.nombreComun = builder.nombreComun;
        this.nombreCientifico = builder.nombreCientifico;
        this.familia = builder.familia;
        this.origen = builder.origen;
        this.fenologia = builder.fenologia;
        this.agentePolinizador = builder.agentePolinizador;
        this.requerimientosDeLuz = builder.requerimientosDeLuz;
        this.metodoDispersion = builder.metodoDispersion;
        this.fruto = builder.fruto;
        this.texturaFruto = builder.texturaFruto;
        this.flor = builder.flor;
        this.paisajeRecomendado = builder.paisajeRecomendado;
        this.habito =builder.habito;
        this.usosConocidos = builder.usosConocidos;
        this.minRangoAltitudinal = builder.minRangoAltitudinal;
        this.maxRangoAltitudinal = builder.maxRangoAltitudinal;
        this.metros = builder.metros;
        this.imagen = builder.imagen;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public String getImagenBase64() {
        return imagen;
    }

    public Bitmap getImage(){
        Bitmap bmp = null;
        if(!this.imagen.equals("")){
            //REF: https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
            byte[] decodedString = Base64.decode(this.imagen, Base64.DEFAULT);
            bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bmp;
    }

    public String getFamilia() {
        return familia;
    }

    public String getOrigen() {
        return origen;
    }

    public String getFenologia() {
        return fenologia;
    }

    public String getAgentePolinizador() {
        return agentePolinizador;
    }

    public String getRequerimientosDeLuz() {
        return requerimientosDeLuz;
    }

    public String getMetodoDispersion() {
        return metodoDispersion;
    }

    public void setMetodoDispersion(String metodoDispersion) {
        this.metodoDispersion = metodoDispersion;
    }

    public String getFruto() {
        return fruto;
    }

    public String getTexturaFruto() {
        return texturaFruto;
    }

    public String getFlor() {
        return flor;
    }

    public ArrayList<String> getPaisajeRecomendado() {
        return paisajeRecomendado;
    }

    public String getHabito() {
        return habito;
    }

    public ArrayList<String> getUsosConocidos() {
        return usosConocidos;
    }

    public String getUsosConocidosString()
    {
        StringBuilder str = new StringBuilder();

        for(int i=0;i < usosConocidos.size()-1; i++){
            str.append(usosConocidos.get(i));
            str.append(' ');
        }
        str.append(usosConocidos.get(usosConocidos.size()-1)); // Para no agregar un espacio al final
        return str.toString();
    }

    public String getPaisajesRecomendadosString()
    {
        StringBuilder str = new StringBuilder();

        for(int i=0;i < paisajeRecomendado.size()-1; i++){
            str.append(paisajeRecomendado.get(i));
            str.append(' ');
        }
        str.append(paisajeRecomendado.get(paisajeRecomendado.size()-1)); // Para no agregar un espacio al final
        return str.toString();
    }

    public double getMinRangoAltitudinal() {
        return minRangoAltitudinal;
    }

    public double getMaxRangoAltitudinal() {
        return maxRangoAltitudinal;
    }

    public double getMetros() {
        return metros;
    }

    public String getInfoBasica(){
        String info = "";

        info += "Nombre comun: "+ getNombreComun() +"\n";
        info += "Nombre cientifico: "+ getNombreComun()+"\n";

        return info;
    }

    public static class PlantaBuilder{

        private String nombreComun,nombreCientifico,familia,origen,fenologia,agentePolinizador,requerimientosDeLuz,metodoDispersion,fruto,texturaFruto,flor,habito;
        private String imagen = "";
        private ArrayList<String> usosConocidos,paisajeRecomendado;
        private double minRangoAltitudinal,maxRangoAltitudinal,metros;

        public PlantaBuilder setNombreComun(String nombreComun) {
            this.nombreComun = nombreComun;
            return this;
        }

        public PlantaBuilder setNombreCientifico(String nombreCientifico) {
            this.nombreCientifico = nombreCientifico;
            return this;
        }

        public PlantaBuilder setFamilia(String familia) {
            this.familia = familia;
            return this;
        }

        public PlantaBuilder setOrigen(String origen) {
            this.origen = origen;
            return this;
        }

        public PlantaBuilder setFenologia(String fenologia) {
            this.fenologia = fenologia;
            return this;
        }

        public PlantaBuilder setAgentePolinizador(String agentePolinizador) {
            this.agentePolinizador = agentePolinizador;
            return this;
        }

        public PlantaBuilder setRequerimientosDeLuz(String requerimientosDeLuz) {
            this.requerimientosDeLuz = requerimientosDeLuz;
            return this;
        }

        public PlantaBuilder setMetodoDispersion(String metodoDispersion) {
            this.metodoDispersion = metodoDispersion;
            return this;
        }

        public PlantaBuilder setFruto(String fruto) {
            this.fruto = fruto;
            return this;
        }

        public PlantaBuilder setTexturaFruto(String texturaFruto) {
            this.texturaFruto = texturaFruto;
            return this;
        }

        public PlantaBuilder setFlor(String flor) {
            this.flor = flor;
            return this;
        }

        public PlantaBuilder setPaisajeRecomendado(ArrayList<String> paisajeRecomendado) {
            this.paisajeRecomendado = paisajeRecomendado;
            return this;
        }

        public PlantaBuilder setHabito(String habito) {
            this.habito = habito;
            return this;
        }

        public PlantaBuilder setUsosConocidos(ArrayList<String> usosConocidos) {
            this.usosConocidos = usosConocidos;
            return this;
        }

        public PlantaBuilder setMinRangoAltitudinal(double minRangoAltitudinal) {
            this.minRangoAltitudinal = minRangoAltitudinal;
            return this;
        }

        public PlantaBuilder setMaxRangoAltitudinal(double maxRangoAltitudinal) {
            this.maxRangoAltitudinal = maxRangoAltitudinal;
            return this;
        }

        public PlantaBuilder setMetros(double metros) {
            this.metros = metros;
            return this;
        }

        public PlantaBuilder setImagen(String imageBase64){
            this.imagen = imageBase64;
            return this;
        }

        public Planta build(){
            return new Planta(this);
        }

    }
}
