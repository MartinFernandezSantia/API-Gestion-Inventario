package com.lmfm.api.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UsuarioRequest {
    private Integer id;
    private String password;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @Positive
    private int legajo;
    @Positive
    private int permisoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(int permisoId) {
        this.permisoId = permisoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
