package com.app.icesi.proyectoappsmoviles.model;

import android.location.Location;

import java.util.Date;

public class Usuario {

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
    private String nombres;
    private String apellidos;
    private Location ubicacion;
    private Date fecha_nacimiento;
    private String genero;
    private String correo;
    private String telefono;
    private String cedula;
    private double calificacion;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, Location ubicacion, Date fecha_nacimiento, String genero, String correo, String telefono, String cedula, double calificacion, boolean activo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ubicacion = ubicacion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.correo = correo;
        this.telefono = telefono;
        this.cedula = cedula;
        this.calificacion = calificacion;
        this.activo = activo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Location getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Location ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
