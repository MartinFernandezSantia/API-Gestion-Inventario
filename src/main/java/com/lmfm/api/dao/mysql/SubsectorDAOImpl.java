package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.SubsectorDAO;
import com.lmfm.api.dto.SubsectorRequest;
import com.lmfm.api.model.Sector;
import com.lmfm.api.model.Subsector;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.service.SectorServicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubsectorDAOImpl implements SubsectorDAO {

    @Override
    public void insertarSubsector(SubsectorRequest subsector) {
        String sql = "INSERT INTO subsectores (nombre, sector_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, subsector.getNombre());
            stmt.setInt(2, subsector.getSectorId());
            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    subsector.setId(id);
                }
            }
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
                Sector sector = SectorServicio.getSectorPorId(rs.getInt("sector_id"));

                subsector.setId(rs.getInt("id"));
                subsector.setNombre(rs.getString("nombre"));
                subsector.setSector(sector);
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
                Sector sector = SectorServicio.getSectorPorId(rs.getInt("sector_id"));

                subsector.setId(rs.getInt("id"));
                subsector.setNombre(rs.getString("nombre"));
                subsector.setSector(sector);
                subsectores.add(subsector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subsectores;
    }

    @Override
    public boolean actualizarSubsector(SubsectorRequest subsector) {
        String sql = "UPDATE subsectores SET nombre = ?, sector_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subsector.getNombre());
            stmt.setInt(2, subsector.getSectorId());
            stmt.setInt(3, subsector.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarSubsectorPorId(int id) {
        String sql = "DELETE FROM subsectores WHERE id = ?";
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
