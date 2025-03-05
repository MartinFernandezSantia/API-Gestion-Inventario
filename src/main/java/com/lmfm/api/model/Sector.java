package com.lmfm.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class Sector {
    @Schema(nullable = true)
    private Integer id;
    @NotEmpty
    private String nombre;
    @Schema(nullable = true)
    private boolean borrado = false;

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

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Sector{id=" + id + ", nombre='" + nombre + "'}";
    }
}
