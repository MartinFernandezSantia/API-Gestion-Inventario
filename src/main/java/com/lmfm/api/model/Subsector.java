package com.lmfm.api.model;

public class Subsector {
    private Integer id;
    private String nombre;
    private Sector sector;
    private boolean borrado;

    // Constructor por defecto
    public Subsector() {}

    // Constructor con parámetros
    public Subsector(Integer id, String nombre, Sector sector) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
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

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Subsector{id=" + id + ", nombre='" + nombre + "', sector=" + sector + "}";
    }
}
