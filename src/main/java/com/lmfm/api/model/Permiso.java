package com.lmfm.api.model;

public class Permiso {
    private Integer id;
    private String nombre;

    // Constructor, getters y setters

    public Permiso() {}

    public Permiso(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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
}
