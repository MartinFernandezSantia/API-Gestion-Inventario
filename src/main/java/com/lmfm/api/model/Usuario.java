package com.lmfm.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lmfm.api.dto.UsuarioRequest;

import java.sql.Timestamp;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private Timestamp fechaHora;
    private int legajo;
    private Permiso permiso;

    @JsonIgnore // Evita que se retorne la password en los endpoints
    private String password;

    // Constructor, getters y setters

    public Usuario() {}

    public Usuario(String nombre, String apellido, int legajo, String password, Permiso permiso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.password = password;
        this.permiso = permiso;
    }

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }



    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellido: " + apellido + " Legajo: " + legajo;
    }
}
