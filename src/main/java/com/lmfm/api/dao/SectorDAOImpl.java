package com.lmfm.api.dao;


import com.lmfm.api.model.Sector;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SectorDAOImpl implements SectorDAO {

    @Override
    public void insertarSector(Sector sector) {
        String sql = "INSERT INTO sectores (nombre) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sector.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Sector> obtenerSectorPorId(int id) {
        String sql = "SELECT * FROM sectores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sector sector = new Sector();
                sector.setId(rs.getInt("id"));
                sector.setNombre(rs.getString("nombre"));
                return Optional.of(sector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Sector> obtenerTodosLosSectores() {
        List<Sector> sectores = new ArrayList<>();
        String sql = "SELECT * FROM sectores";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sector sector = new Sector();
                sector.setId(rs.getInt("id"));
                sector.setNombre(rs.getString("nombre"));
                sectores.add(sector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectores;
    }

    @Override
    public void actualizarSector(Sector sector) {
        String sql = "UPDATE sectores SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sector.getNombre());
            stmt.setInt(2, sector.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarSectorPorId(int id) {
        String sql = "DELETE FROM sectores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
