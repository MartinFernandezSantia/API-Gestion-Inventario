package com.lmfm.api.model;

public class Subsector {
    private int id;
    private String nombre;
    private int sectorId;

    // Constructor por defecto
    public Subsector() {}

    // Constructor con parámetros
    public Subsector(int id, String nombre, int sectorId) {
        this.id = id;
        this.nombre = nombre;
        this.sectorId = sectorId;
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

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public String toString() {
        return "Subsector{id=" + id + ", nombre='" + nombre + "', sectorId=" + sectorId + "}";
    }
}
