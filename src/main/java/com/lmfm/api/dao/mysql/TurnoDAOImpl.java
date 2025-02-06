package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.TurnoDAO;
import com.lmfm.api.model.Turno;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnoDAOImpl implements TurnoDAO {

    @Override
    public void insertarTurno(Turno turno) {
        String sql = "INSERT INTO turnos (nombre, hora_inicio, hora_fin) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turno.getNombre());
            stmt.setTime(2, turno.getHoraInicio());
            stmt.setTime(3, turno.getHoraFin());
            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    turno.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Turno> obtenerTurnoPorId(int id) {
        String sql = "SELECT * FROM turnos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Turno turno = new Turno();
                turno.setId(rs.getInt("id"));
                turno.setNombre(rs.getString("nombre"));
                turno.setHoraInicio(rs.getTime("hora_inicio"));
                turno.setHoraFin(rs.getTime("hora_fin"));
                return Optional.of(turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Turno> obtenerTodosLosTurnos() {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT * FROM turnos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Turno turno = new Turno();
                turno.setId(rs.getInt("id"));
                turno.setNombre(rs.getString("nombre"));
                turno.setHoraInicio(rs.getTime("hora_inicio"));
                turno.setHoraFin(rs.getTime("hora_fin"));
                turnos.add(turno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnos;
    }

    @Override
    public boolean actualizarTurno(Turno turno) {
        String sql = "UPDATE turnos SET nombre = ?, hora_inicio = ?, hora_fin = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turno.getNombre());
            stmt.setTime(2, turno.getHoraInicio());
            stmt.setTime(3, turno.getHoraFin());
            stmt.setInt(4, turno.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarTurnoPorId(int id) {
        String sql = "DELETE FROM turnos WHERE id = ?";
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
