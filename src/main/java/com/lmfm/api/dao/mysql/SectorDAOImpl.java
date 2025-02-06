package com.lmfm.api.dao.mysql;


import com.lmfm.api.dao.SectorDAO;
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
            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    sector.setId(id);
                }
            }
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
    public boolean actualizarSector(Sector sector) {
        String sql = "UPDATE sectores SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sector.getNombre());
            stmt.setInt(2, sector.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarSectorPorId(int id) {
        String sql = "DELETE FROM sectores WHERE id = ?";
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
