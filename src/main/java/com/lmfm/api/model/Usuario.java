package com.lmfm.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lmfm.api.dto.UsuarioRequest;

import java.sql.Timestamp;
import java.util.Objects;

public class Usuario {
    private Integer id;
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

    public Usuario(Integer id, String nombre, String apellido, Timestamp fechaHora, int legajo, Permiso permiso) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaHora = fechaHora;
        this.legajo = legajo;
        this.permiso = permiso;
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
    public boolean equals(Object obj) {
        if (this == obj) { return true; }

        if (obj == null || getClass() != obj.getClass()) { return false; }

        final Usuario usuario = (Usuario) obj;

        if (this.id != usuario.getId()) { return false; }
        if (!this.nombre.equals(usuario.getNombre())) { return false; }
        if (!this.apellido.equals(usuario.getApellido())) { return false; }
        if (this.legajo != usuario.getLegajo()) { return false; }

        return this.permiso.equals(usuario.permiso);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.nombre);
        hash = 11 * hash + Objects.hashCode(this.apellido);
        hash = 11 * hash + this.legajo;
        hash = 11 * hash + Objects.hashCode(this.permiso);

        return hash;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellido: " + apellido + " Legajo: " + legajo;
    }
}
