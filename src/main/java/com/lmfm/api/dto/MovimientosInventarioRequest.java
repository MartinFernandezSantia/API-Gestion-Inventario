package com.lmfm.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovimientosInventarioRequest {
    @Schema(nullable = true)
    private Integer id;

    @NotNull
    @Positive
    private int articuloId;

    @NotNull
    @Positive
    private int usuarioId;

    @NotNull
    @Positive
    private int turnoId;

    @NotNull
    @Positive
    private int subsectorId;

    @NotNull
    @Min(value = 0)
    private int cantidad;

    @NotNull
    private boolean tipoMovimiento;

    @NotNull
    private boolean esPedido;

    @NotNull
    private boolean esDiferencia;

    @Schema(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String fechaHora;

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isEsDiferencia() {
        return esDiferencia;
    }

    public void setEsDiferencia(boolean esDiferencia) {
        this.esDiferencia = esDiferencia;
    }

    public boolean isEsPedido() {
        return esPedido;
    }

    public void setEsPedido(boolean esPedido) {
        this.esPedido = esPedido;
    }

    public boolean isTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(boolean tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubsectorId() {
        return subsectorId;
    }

    public void setSubsectorId(int subsectorId) {
        this.subsectorId = subsectorId;
    }

    public int getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(int turnoId) {
        this.turnoId = turnoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MovimientosInventarioRequest{" +
                "id=" + id +
                ", articuloId=" + articuloId +
                ", usuarioId=" + usuarioId +
                ", turnoId=" + turnoId +
                ", subsectorId=" + subsectorId +
                ", cantidad=" + cantidad +
                ", tipoMovimiento=" + tipoMovimiento +
                ", esPedido=" + esPedido +
                ", esDiferencia=" + esDiferencia +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}