package com.lmfm.api.model;


public class Sector {
    private Integer id;
    private String nombre;

    // Constructor por defecto
    public Sector() {}

    // Constructor con parámetros
    public Sector(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
