package com.lmfm.api.dao;

import com.lmfm.api.model.Subsector;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubsectorDAOImpl implements SubsectorDAO {

    @Override
    public void insertarSubsector(Subsector subsector) {
        String sql = "INSERT INTO subsectores (nombre, sector_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subsector.getNombre());
            stmt.setInt(2, subsector.getSectorId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Subsector> obtenerSubsectorPorId(int id) {
        String sql = "SELECT * FROM subsectores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Subsector subsector = new Subsector();
                subsector.setId(rs.getInt("id"));
                subsector.setNombre(rs.getString("nombre"));
                subsector.setSectorId(rs.getInt("sector_id"));
                return Optional.of(subsector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Subsector> obtenerTodosLosSubsectores() {
        List<Subsector> subsectores = new ArrayList<>();
        String sql = "SELECT * FROM subsectores";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Subsector subsector = new Subsector();
                subsector.setId(rs.getInt("id"));
                subsector.setNombre(rs.getString("nombre"));
                subsector.setSectorId(rs.getInt("sector_id"));
                subsectores.add(subsector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subsectores;
    }

    @Override
    public void actualizarSubsector(Subsector subsector) {
        String sql = "UPDATE subsectores SET nombre = ?, sector_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subsector.getNombre());
            stmt.setInt(2, subsector.getSectorId());
            stmt.setInt(3, subsector.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarSubsectorPorId(int id) {
        String sql = "DELETE FROM subsectores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
