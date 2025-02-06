package com.lmfm.api.model;

import java.util.Objects;
import java.sql.Timestamp;

public class MovimientosInventario {
    private Integer id;
    private Articulo articulo;
    private Usuario usuario;
    private Turno turno;
    private Subsector subsector;
    private int cantidad;
    private boolean tipoMovimiento; // true para entrada, false para salida
    private boolean esPedido;
    private boolean esDiferencia;
    private Timestamp fechaHora;

    // Constructor por defecto
    public MovimientosInventario() {}

    // Constructor con parámetros
    public MovimientosInventario(Integer id, Articulo articulo, Usuario usuario, Turno turno, Subsector subsector,
                                 int cantidad, boolean tipoMovimiento, boolean esPedido, boolean esDiferencia,
                                 Timestamp fechaHora) {
        this.id = id;
        this.articulo = articulo;
        this.usuario = usuario;
        this.turno = turno;
        this.subsector = subsector;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
        this.esPedido = esPedido;
        this.esDiferencia = esDiferencia;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Subsector getSubsector() {
        return subsector;
    }

    public void setSubsector(Subsector subsector) {
        this.subsector = subsector;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(boolean tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public boolean isEsPedido() {
        return esPedido;
    }

    public void setEsPedido(boolean esPedido) {
        this.esPedido = esPedido;
    }

    public boolean isEsDiferencia() {
        return esDiferencia;
    }

    public void setEsDiferencia(boolean esDiferencia) {
        this.esDiferencia = esDiferencia;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "MovimientosInventario{" +
                "id=" + id +
                ", articulo=" + articulo +
                ", usuario=" + usuario +
                ", turno=" + turno +
                ", subsector=" + subsector +
                ", cantidad=" + cantidad +
                ", tipoMovimiento=" + tipoMovimiento +
                ", esPedido=" + esPedido +
                ", esDiferencia=" + esDiferencia +
                ", fechaHora=" + fechaHora +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientosInventario that = (MovimientosInventario) o;
        return id == that.id &&
                articulo == that.articulo &&
                usuario == that.usuario &&
                turno == that.turno &&
                subsector == that.subsector &&
                cantidad == that.cantidad &&
                tipoMovimiento == that.tipoMovimiento &&
                esPedido == that.esPedido &&
                esDiferencia == that.esDiferencia &&
                Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articulo, usuario, turno, subsector, cantidad, tipoMovimiento, esPedido, esDiferencia, fechaHora);
    }
}
