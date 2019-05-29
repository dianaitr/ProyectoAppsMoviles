package com.app.icesi.proyectoappsmoviles.model;

import java.util.ArrayList;
import java.util.Date;

public class Servicio {



    private String estado;
    private String id_colab;
    private String id_cliente;
    private double calificacion;
    private String comentarios;
    private Date fechaServicio;
    private ArrayList<String> tiposServicios;

    public Servicio(){

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_colab() {
        return id_colab;
    }

    public void setId_colab(String id_colab) {
        this.id_colab = id_colab;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public ArrayList<String> getTiposServicios() {
        return tiposServicios;
    }

    public void setTiposServicios(ArrayList<String> tiposServicios) {
        this.tiposServicios = tiposServicios;
    }
}
