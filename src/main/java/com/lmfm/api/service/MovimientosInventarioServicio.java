package com.lmfm.api.service;

import com.lmfm.api.dao.MovimientosInventarioDAO;
import com.lmfm.api.dao.mysql.MovimientosInventarioDAOImpl;
import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.MovimientosInventario;
import com.lmfm.api.translators.MovimientosInventarioTranslator;

import java.util.List;
import java.util.Optional;

public class MovimientosInventarioServicio {

    private static MovimientosInventarioDAO movimientosInventarioDAO = new MovimientosInventarioDAOImpl();

    public static boolean crearMovimiento(MovimientosInventario movimientosInventario) {
        MovimientosInventarioRequest request = MovimientosInventarioTranslator.toDTO(movimientosInventario);
        movimientosInventarioDAO.insertarMovimiento(request);
        movimientosInventario.setId(request.getId());

        return request.getId() != null;
    }

    public static List<MovimientosInventarioRequest> crearMovimientos(List<MovimientosInventarioRequest> movimientos) {
        return movimientosInventarioDAO.insertarMovimientos(movimientos);
    }

    public static List<MovimientosInventario> obtenerTodosLosMovimientos() {
        return movimientosInventarioDAO.obtenerTodosLosMovimientos();
    }

    public static boolean actualizarMovimiento(MovimientosInventario movimientosInventario) {
        MovimientosInventarioRequest request = MovimientosInventarioTranslator.toDTO(movimientosInventario);
        return movimientosInventarioDAO.actualizarMovimiento(request);
    }

    public static boolean eliminarMovimiento(int id) {
        return movimientosInventarioDAO.eliminarMovimientoPorId(id);
    }

    public static Optional<MovimientosInventario> getMovimientoPorId(int id) {
        return movimientosInventarioDAO.obtenerMovimientoPorId(id);
    }
}
