package com.lmfm.api.dao;


import com.lmfm.api.model.MovimientosInventario;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovimientosInventarioDAOImpl implements MovimientosInventarioDAO {

    @Override
    public void insertarMovimiento(MovimientosInventario movimiento) {
        String sql = "INSERT INTO movimientos_inventario (articulo_id, usuario_id, turno_id, subsector_id, cantidad, tipo_movimiento, es_pedido, es_diferencia, fecha_hora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movimiento.getArticuloId());
            stmt.setInt(2, movimiento.getUsuarioId());
            stmt.setInt(3, movimiento.getTurnoId());
            stmt.setInt(4, movimiento.getSubsectorId());
            stmt.setInt(5, movimiento.getCantidad());
            stmt.setBoolean(6, movimiento.isTipoMovimiento());
            stmt.setBoolean(7, movimiento.isEsPedido());
            stmt.setBoolean(8, movimiento.isEsDiferencia());
            stmt.setTimestamp(9, movimiento.getFechaHora());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<MovimientosInventario> obtenerMovimientoPorId(int id) {
        String sql = "SELECT * FROM movimientos_inventario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MovimientosInventario movimiento = new MovimientosInventario();
                movimiento.setId(rs.getInt("id"));
                movimiento.setArticuloId(rs.getInt("articulo_id"));
                movimiento.setUsuarioId(rs.getInt("usuario_id"));
                movimiento.setTurnoId(rs.getInt("turno_id"));
                movimiento.setSubsectorId(rs.getInt("subsector_id"));
                movimiento.setCantidad(rs.getInt("cantidad"));
                movimiento.setTipoMovimiento(rs.getBoolean("tipo_movimiento"));
                movimiento.setEsPedido(rs.getBoolean("es_pedido"));
                movimiento.setEsDiferencia(rs.getBoolean("es_diferencia"));
                movimiento.setFechaHora(rs.getTimestamp("fecha_hora"));
                return Optional.of(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MovimientosInventario> obtenerTodosLosMovimientos() {
        List<MovimientosInventario> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovimientosInventario movimiento = new MovimientosInventario();
                movimiento.setId(rs.getInt("id"));
                movimiento.setArticuloId(rs.getInt("articulo_id"));
                movimiento.setUsuarioId(rs.getInt("usuario_id"));
                movimiento.setTurnoId(rs.getInt("turno_id"));
                movimiento.setSubsectorId(rs.getInt("subsector_id"));
                movimiento.setCantidad(rs.getInt("cantidad"));
                movimiento.setTipoMovimiento(rs.getBoolean("tipo_movimiento"));
                movimiento.setEsPedido(rs.getBoolean("es_pedido"));
                movimiento.setEsDiferencia(rs.getBoolean("es_diferencia"));
                movimiento.setFechaHora(rs.getTimestamp("fecha_hora"));
                movimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    @Override
    public void actualizarMovimiento(MovimientosInventario movimiento) {
        String sql = "UPDATE movimientos_inventario SET articulo_id = ?, usuario_id = ?, turno_id = ?, subsector_id = ?, cantidad = ?, tipo_movimiento = ?, es_pedido = ?, es_diferencia = ?, fecha_hora = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movimiento.getArticuloId());
            stmt.setInt(2, movimiento.getUsuarioId());
            stmt.setInt(3, movimiento.getTurnoId());
            stmt.setInt(4, movimiento.getSubsectorId());
            stmt.setInt(5, movimiento.getCantidad());
            stmt.setBoolean(6, movimiento.isTipoMovimiento());
            stmt.setBoolean(7, movimiento.isEsPedido());
            stmt.setBoolean(8, movimiento.isEsDiferencia());
            stmt.setTimestamp(9, movimiento.getFechaHora());
            stmt.setInt(10, movimiento.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMovimientoPorId(int id) {
        String sql = "DELETE FROM movimientos_inventario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
