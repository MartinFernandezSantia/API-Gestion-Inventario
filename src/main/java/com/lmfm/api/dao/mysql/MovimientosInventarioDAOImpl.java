package com.lmfm.api.dao.mysql;


import com.lmfm.api.dao.MovimientosInventarioDAO;
import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.*;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.service.ArticuloServicio;
import com.lmfm.api.service.SubsectorServicio;
import com.lmfm.api.service.TurnoServicio;
import com.lmfm.api.service.UsuarioServicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovimientosInventarioDAOImpl implements MovimientosInventarioDAO {

    @Override
    public void insertarMovimiento(MovimientosInventarioRequest movimiento) {
        String sql = "INSERT INTO movimientos_inventario (articulo_id, usuario_id, turno_id, subsector_id, cantidad, tipo_movimiento, es_pedido, es_diferencia, fecha_hora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, movimiento.getArticuloId());
            stmt.setInt(2, movimiento.getUsuarioId());
            stmt.setInt(3, movimiento.getTurnoId());
            stmt.setInt(4, movimiento.getSubsectorId());
            stmt.setInt(5, movimiento.getCantidad());
            stmt.setBoolean(6, movimiento.isTipoMovimiento());
            stmt.setBoolean(7, movimiento.isEsPedido());
            stmt.setBoolean(8, movimiento.isEsDiferencia());
            stmt.setTimestamp(9, Timestamp.valueOf(movimiento.getFechaHora()));

            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    movimiento.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> insertarMovimientos(List<MovimientosInventarioRequest> movimientos) {
        String sql = "INSERT IGNORE INTO movimientos_inventario (articulo_id, usuario_id, turno_id, subsector_id, cantidad, tipo_movimiento, es_pedido, es_diferencia, fecha_hora) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Integer> failedInserts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (MovimientosInventarioRequest movimiento : movimientos) {
                stmt.setInt(1, movimiento.getArticuloId());
                stmt.setInt(2, movimiento.getUsuarioId());
                stmt.setInt(3, movimiento.getTurnoId());
                stmt.setInt(4, movimiento.getSubsectorId());
                stmt.setInt(5, movimiento.getCantidad());
                stmt.setBoolean(6, movimiento.isTipoMovimiento());
                stmt.setBoolean(7, movimiento.isEsPedido());
                stmt.setBoolean(8, movimiento.isEsDiferencia());
                stmt.setTimestamp(9, Timestamp.valueOf(movimiento.getFechaHora()));

                stmt.addBatch();
            }
            int[] results = stmt.executeBatch();

            // Separate all failed inserts
            for (int i = 0; i < results.length; i++) {
                if (results[i] == 0) {
                    failedInserts.add(movimientos.get(i).getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return failedInserts;
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
                Articulo articulo = ArticuloServicio.getArticuloPorId(rs.getInt("articulo_id")).orElse(null);
                Usuario usuario = UsuarioServicio.getUsuarioPorId(rs.getInt("usuario_id")).orElse(null);
                Turno turno = TurnoServicio.getTurnoPorId(rs.getInt("turno_id")).orElse(null);
                Subsector subsector = SubsectorServicio.getSubsectorPorId(rs.getInt("subsector_id")).orElse(null);

                movimiento.setId(rs.getInt("id"));
                movimiento.setArticulo(articulo);
                movimiento.setUsuario(usuario);
                movimiento.setTurno(turno);
                movimiento.setSubsector(subsector);
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
                Articulo articulo = ArticuloServicio.getArticuloPorId(rs.getInt("articulo_id")).orElse(null);
                Usuario usuario = UsuarioServicio.getUsuarioPorId(rs.getInt("usuario_id")).orElse(null);
                Turno turno = TurnoServicio.getTurnoPorId(rs.getInt("turno_id")).orElse(null);
                Subsector subsector = SubsectorServicio.getSubsectorPorId(rs.getInt("subsector_id")).orElse(null);

                movimiento.setId(rs.getInt("id"));
                movimiento.setArticulo(articulo);
                movimiento.setUsuario(usuario);
                movimiento.setTurno(turno);
                movimiento.setSubsector(subsector);
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
    public boolean actualizarMovimiento(MovimientosInventarioRequest movimiento) {
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
            stmt.setTimestamp(9, Timestamp.valueOf(movimiento.getFechaHora()));
            stmt.setInt(10, movimiento.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarMovimientoPorId(int id) {
        String sql = "DELETE FROM movimientos_inventario WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
