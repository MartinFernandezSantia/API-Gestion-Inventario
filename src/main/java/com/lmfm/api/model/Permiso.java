package com.lmfm.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class Permiso {
    @Schema(nullable = true)
    private Integer id;
    @NotEmpty
    private String nombre;
    @Schema(nullable = true)
    private boolean borrado = false;

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

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
}
