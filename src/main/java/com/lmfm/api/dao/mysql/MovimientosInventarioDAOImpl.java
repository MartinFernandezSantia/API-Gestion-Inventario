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
        String sql = "SELECT \n" +
                "    m.id AS movimiento_id,\n" +
                "    m.cantidad AS movimiento_cantidad,\n" +
                "    m.tipo_movimiento AS movimiento_tipo,\n" +
                "    m.fecha_hora AS movimiento_fecha_hora,\n" +
                "    m.es_pedido AS movimiento_es_pedido,\n" +
                "    m.es_diferencia AS movimiento_es_diferencia,\n" +
                "    \n" +
                "    a.id AS articulo_id,\n" +
                "    a.codigo AS articulo_codigo,\n" +
                "    a.nombre AS articulo_nombre,\n" +
                "    a.stock AS articulo_stock,\n" +
                "    a.limite AS articulo_limite,\n" +
                "    a.fecha_hora AS articulo_fecha_hora,\n" +
                "\n" +
                "    u.id AS usuario_id,\n" +
                "    u.nombre AS usuario_nombre,\n" +
                "    u.apellido AS usuario_apellido,\n" +
                "    u.fecha_hora AS usuario_fecha_hora,\n" +
                "    u.legajo AS usuario_legajo,\n" +
                "\n" +
                "    t.id AS turno_id,\n" +
                "    t.nombre AS turno_nombre,\n" +
                "    t.hora_inicio AS turno_hora_inicio,\n" +
                "    t.hora_fin AS turno_hora_fin,\n" +
                "\n" +
                "    s.id AS subsector_id,\n" +
                "    s.nombre AS subsector_nombre,\n" +
                "\n" +
                "    p.id AS permiso_id," +
                "    p.nombre AS permiso_nombre," +
                "\n" +
                "    c.id AS categoria_id," +
                "    c.nombre AS categoria_nombre," +
                "\n" +
                "    sectores.id AS sector_id," +
                "    sectores.nombre AS sector_nombre" +
                "\n" +
                "FROM movimientos_inventario m\n" +
                "JOIN articulos a ON m.articulo_id = a.id\n" +
                "JOIN usuarios u ON m.usuario_id = u.id\n" +
                "JOIN turnos t ON m.turno_id = t.id\n" +
                "JOIN subsectores s ON m.subsector_id = s.id\n" +
                "JOIN categorias c ON a.categoria_id = c.id\n" +
                "JOIN sectores ON s.sector_id = sectores.id\n" +
                "JOIN permisos p ON u.permiso_id = p.id\n" +
                "ORDER BY movimiento_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int movimientoId = rs.getInt("movimiento_id");
                int movimientoCantidad = rs.getInt("movimiento_cantidad");
                boolean movimientoTipo = rs.getBoolean("movimiento_tipo");
                String movimientoFechaHora = rs.getString("movimiento_fecha_hora");
                boolean movimientoEsPedido = rs.getBoolean("movimiento_es_pedido");
                boolean movimientoEsDiferencia = rs.getBoolean("movimiento_es_diferencia");

                int articuloId = rs.getInt("articulo_id");
                int articuloCodigo = rs.getInt("articulo_codigo");
                String articuloNombre = rs.getString("articulo_nombre");
                int articuloStock = rs.getInt("articulo_stock");
                Integer articuloLimite = rs.getObject("articulo_limite", Integer.class); // Puede ser NULL
                String articuloFechaHora = rs.getString("articulo_fecha_hora");

                int usuarioId = rs.getInt("usuario_id");
                String usuarioNombre = rs.getString("usuario_nombre");
                String usuarioApellido = rs.getString("usuario_apellido");
                String usuarioFechaHora = rs.getString("usuario_fecha_hora");
                int usuarioLegajo = rs.getInt("usuario_legajo");

                int turnoId = rs.getInt("turno_id");
                String turnoNombre = rs.getString("turno_nombre");
                String turnoHoraInicio = rs.getString("turno_hora_inicio");
                String turnoHoraFin = rs.getString("turno_hora_fin");

                int subsectorId = rs.getInt("subsector_id");
                String subsectorNombre = rs.getString("subsector_nombre");

                int permisoId = rs.getInt("permiso_id");
                String permisoNombre = rs.getString("permiso_nombre");

                int sectorId = rs.getInt("sector_id");
                String sectorNombre = rs.getString("sector_nombre");

                int categoriaId = rs.getInt("categoria_id");
                String categoriaNombre = rs.getString("categoria_nombre");

                MovimientosInventario movimiento = new MovimientosInventario(
                        movimientoId,
                        new Articulo(articuloId, articuloCodigo, articuloNombre, articuloStock, articuloLimite,
                                Timestamp.valueOf(articuloFechaHora), new Categoria(categoriaId, categoriaNombre)),
                        new Usuario(usuarioId, usuarioNombre, usuarioApellido, Timestamp.valueOf(usuarioFechaHora),
                                usuarioLegajo, new Permiso(permisoId, permisoNombre)),
                        new Turno(turnoId, turnoNombre, Time.valueOf(turnoHoraInicio), Time.valueOf(turnoHoraFin)),
                        new Subsector(subsectorId, subsectorNombre, new Sector(sectorId, sectorNombre)),
                        movimientoCantidad,
                        movimientoTipo,
                        movimientoEsPedido,
                        movimientoEsDiferencia,
                        Timestamp.valueOf(movimientoFechaHora)
                );
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
