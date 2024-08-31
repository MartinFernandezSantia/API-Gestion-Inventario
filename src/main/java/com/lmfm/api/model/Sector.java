package com.lmfm.api.model;


public class Sector {
    private int id;
    private String nombre;

    // Constructor por defecto
    public Sector() {}

    // Constructor con parámetros
    public Sector(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Sector{id=" + id + ", nombre='" + nombre + "'}";
    }
}
