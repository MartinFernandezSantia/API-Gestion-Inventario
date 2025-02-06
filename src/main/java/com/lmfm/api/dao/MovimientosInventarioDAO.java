package com.lmfm.api.dao;

import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.MovimientosInventario;

import java.util.List;
import java.util.Optional;

public interface MovimientosInventarioDAO {
    void insertarMovimiento(MovimientosInventarioRequest movimiento);
    Optional<MovimientosInventario> obtenerMovimientoPorId(int id);
    List<MovimientosInventario> obtenerTodosLosMovimientos();
    boolean actualizarMovimiento(MovimientosInventarioRequest movimiento);
    boolean eliminarMovimientoPorId(int id);
}
