package com.lmfm.api.model;

public class Permiso {
    private int id;
    private int nivel;
    private String descripcion;

    // Constructor, getters y setters

    public Permiso() {}

    public Permiso(int id, int nivel, String descripcion) {
        this.id = id;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
