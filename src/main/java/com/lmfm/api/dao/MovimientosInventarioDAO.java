package com.lmfm.api.dao;

import com.lmfm.api.model.MovimientosInventario;

import java.util.List;
import java.util.Optional;

public interface MovimientosInventarioDAO {
    void insertarMovimiento(MovimientosInventario movimiento);
    Optional<MovimientosInventario> obtenerMovimientoPorId(int id);
    List<MovimientosInventario> obtenerTodosLosMovimientos();
    void actualizarMovimiento(MovimientosInventario movimiento);
    void eliminarMovimientoPorId(int id);
}
