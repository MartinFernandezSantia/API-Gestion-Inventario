package com.lmfm.api.model;

import java.sql.Timestamp;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private Timestamp fechaHora;
    private int legajo;
    private String password;
    private int permisoId;

    // Constructor, getters y setters

    public Usuario() {}

    public Usuario(int id, String nombre, String apellido, int legajo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
        this.password = password;
        this.permisoId = permisoId;
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

    public void setContrasena(String password) {
        this.password = password;
    }

    public int getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(int permisoId) {
        this.permisoId = permisoId;
    }



    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellido: " + apellido + " Legajo: " + legajo;
    }
}
