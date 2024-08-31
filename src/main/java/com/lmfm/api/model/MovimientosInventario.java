package com.lmfm.api.model;

import java.util.Objects;

public class MovimientosInventario {
    private int id;
    private int articuloId;
    private int usuarioId;
    private int turnoId;
    private int subsectorId;
    private int cantidad;
    private boolean tipoMovimiento; // true para entrada, false para salida
    private boolean esPedido;
    private boolean esDiferencia;
    private java.sql.Timestamp fechaHora;

    // Constructor por defecto
    public MovimientosInventario() {}

    // Constructor con parámetros
    public MovimientosInventario(int id, int articuloId, int usuarioId, int turnoId, int subsectorId,
                                 int cantidad, boolean tipoMovimiento, boolean esPedido, boolean esDiferencia,
                                 java.sql.Timestamp fechaHora) {
        this.id = id;
        this.articuloId = articuloId;
        this.usuarioId = usuarioId;
        this.turnoId = turnoId;
        this.subsectorId = subsectorId;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
        this.esPedido = esPedido;
        this.esDiferencia = esDiferencia;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(int turnoId) {
        this.turnoId = turnoId;
    }

    public int getSubsectorId() {
        return subsectorId;
    }

    public void setSubsectorId(int subsectorId) {
        this.subsectorId = subsectorId;
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

    public java.sql.Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(java.sql.Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "MovimientosInventario{" +
                "id=" + id +
                ", articuloId=" + articuloId +
                ", usuarioId=" + usuarioId +
                ", turnoId=" + turnoId +
                ", subsectorId=" + subsectorId +
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
                articuloId == that.articuloId &&
                usuarioId == that.usuarioId &&
                turnoId == that.turnoId &&
                subsectorId == that.subsectorId &&
                cantidad == that.cantidad &&
                tipoMovimiento == that.tipoMovimiento &&
                esPedido == that.esPedido &&
                esDiferencia == that.esDiferencia &&
                Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articuloId, usuarioId, turnoId, subsectorId, cantidad, tipoMovimiento, esPedido, esDiferencia, fechaHora);
    }
}
