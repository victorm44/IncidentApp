package com.braian.incidentapp.clases;

public class Reportes {
    private String fecha;
    private String titulo;
    private String descripcion;
    private String prioridad;

    public Reportes(String fecha, String titulo, String descripcion, String prioridad){
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion =descripcion;
        this.prioridad = prioridad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
