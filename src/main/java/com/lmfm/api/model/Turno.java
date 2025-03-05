package com.lmfm.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Objects;

public class Turno {
    @Schema(nullable = true)
    private Integer id;
    @NotEmpty
    private String nombre;
    @NotNull
    private Time horaInicio;
    @NotNull
    private Time horaFin;
    @Schema(nullable = true)
    private boolean borrado = false;

    // Constructor por defecto
    public Turno() {}

    // Constructor con parámetros
    public Turno(Integer id, String nombre, Time horaInicio, Time horaFin) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Turno{id=" + id + ", nombre='" + nombre + "', horaInicio=" + horaInicio + ", horaFin=" + horaFin + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno turno = (Turno) o;
        return id == turno.id &&
                Objects.equals(nombre, turno.nombre) &&
                Objects.equals(horaInicio, turno.horaInicio) &&
                Objects.equals(horaFin, turno.horaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, horaInicio, horaFin);
    }
}
