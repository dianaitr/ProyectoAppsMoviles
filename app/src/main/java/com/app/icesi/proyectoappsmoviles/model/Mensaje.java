package com.app.icesi.proyectoappsmoviles.model;

public class Mensaje {

    public String contenido;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String nombre;

    public Mensaje(){

    }

    public Mensaje(String nombre, String contenido){

        this.contenido=contenido;
        this.nombre=nombre;

    }

}
