package com.braian.incidentapp.clases;

public class User {

    String nombre;
    String apellido;
    String rol;
    String cedula;

    public User(String nombre, String apellido, String rol, String cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
