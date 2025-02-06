package com.lmfm.api.model;

import java.util.Objects;
import java.sql.Timestamp;

public class Balances {
    private Integer id;
    private Articulo articulo;
    private int stock;
    private int stockReal;
    private Timestamp fechaHora;

    // Constructor por defecto
    public Balances() {}

    // Constructor con parámetros
    public Balances(Integer id, Articulo articulo, int stock, int stockReal, Timestamp fechaHora) {
        this.id = id;
        this.articulo = articulo;
        this.stock = stock;
        this.stockReal = stockReal;
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

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Balances{" +
                "id=" + id +
                ", articulo=" + articulo +
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
                articulo == that.articulo &&
                stock == that.stock &&
                stockReal == that.stockReal &&
                Objects.equals(fechaHora, that.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articulo, stock, stockReal, fechaHora);
    }
}
