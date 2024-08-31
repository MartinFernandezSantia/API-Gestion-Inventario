package com.lmfm.api.service;

import com.lmfm.api.dao.MovimientosInventarioDAO;
import com.lmfm.api.model.MovimientosInventario;

import java.util.List;
import java.util.Optional;

public class MovimientosInventarioServicio {

    private final MovimientosInventarioDAO movimientosInventarioDAO;

    public MovimientosInventarioServicio(MovimientosInventarioDAO movimientosInventarioDAO) {
        this.movimientosInventarioDAO = movimientosInventarioDAO;
    }

    // Crear nuevo movimiento
    public void crearMovimiento(int articuloId, int usuarioId, int turnoId, int subsectorId, int cantidad,
                                boolean tipoMovimiento, boolean esPedido, boolean esDiferencia,
                                java.sql.Timestamp fechaHora) {
        MovimientosInventario nuevoMovimiento = new MovimientosInventario();
        nuevoMovimiento.setArticuloId(articuloId);
        nuevoMovimiento.setUsuarioId(usuarioId);
        nuevoMovimiento.setTurnoId(turnoId);
        nuevoMovimiento.setSubsectorId(subsectorId);
        nuevoMovimiento.setCantidad(cantidad);
        nuevoMovimiento.setTipoMovimiento(tipoMovimiento);
        nuevoMovimiento.setEsPedido(esPedido);
        nuevoMovimiento.setEsDiferencia(esDiferencia);
        nuevoMovimiento.setFechaHora(fechaHora);
        movimientosInventarioDAO.insertarMovimiento(nuevoMovimiento);
    }

    // Listar movimientos
    public List<MovimientosInventario> obtenerTodosLosMovimientos() {
        return movimientosInventarioDAO.obtenerTodosLosMovimientos();
    }

    // Actualizar movimiento
    public void actualizarMovimiento(int id, int articuloId, int usuarioId, int turnoId, int subsectorId,
                                     int cantidad, boolean tipoMovimiento, boolean esPedido,
                                     boolean esDiferencia, java.sql.Timestamp fechaHora) {
        Optional<MovimientosInventario> movimientoOpt = movimientosInventarioDAO.obtenerMovimientoPorId(id);
        if (movimientoOpt.isPresent()) {
            MovimientosInventario movimiento = movimientoOpt.get();
            movimiento.setArticuloId(articuloId);
            movimiento.setUsuarioId(usuarioId);
            movimiento.setTurnoId(turnoId);
            movimiento.setSubsectorId(subsectorId);
            movimiento.setCantidad(cantidad);
            movimiento.setTipoMovimiento(tipoMovimiento);
            movimiento.setEsPedido(esPedido);
            movimiento.setEsDiferencia(esDiferencia);
            movimiento.setFechaHora(fechaHora);
            movimientosInventarioDAO.actualizarMovimiento(movimiento);
        }
    }

    // Eliminar movimiento
    public void eliminarMovimiento(int id) {
        movimientosInventarioDAO.eliminarMovimientoPorId(id);
    }

    // Buscar movimiento por ID
    public MovimientosInventario buscarMovimientoPorId(int id) {
        Optional<MovimientosInventario> movimientoOpt = movimientosInventarioDAO.obtenerMovimientoPorId(id);
        return movimientoOpt.orElse(null); // Retorna null si no encuentra ningún movimiento por ID
    }
}
