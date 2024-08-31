package com.lmfm.api.model;

import java.util.Objects;

public class Balances {
    private int id;
    private int articuloId;
    private int stock;
    private int stockReal;
    private java.sql.Timestamp fechaHora;

    // Constructor por defecto
    public Balances() {}

    // Constructor con parámetros
    public Balances(int id, int articuloId, int stock, int stockReal, java.sql.Timestamp fechaHora) {
        this.id = id;
        this.articuloId = articuloId;
        this.stock = stock;
        this.stockReal = stockReal;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockReal() {
        return stockReal;
    }

    public void setStockReal(int stockReal) {
        this.stockReal = stockReal;
    }

    public java.sql.Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(java.sql.Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Balances{" +
                "id=" + id +
                ", articuloId=" + articuloId +
                ", stock=" + stock +
                ", stockReal=" + stockReal +
                ", fechaHora=" + fechaHora +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balances that = (Balances) o;
        return id == that.id &&
                articuloId == that.articuloId &&
                stock == that.stock &&
                stockReal == that.stockReal &&
                Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articuloId, stock, stockReal, fechaHora);
    }
}
