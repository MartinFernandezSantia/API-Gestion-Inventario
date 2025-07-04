package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.PermisoDAO;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermisoDAOImpl implements PermisoDAO {

    @Override
    public void insertarPermiso(Permiso permiso) {
        String sql = "INSERT INTO permisos (nombre) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, permiso.getNombre());

            int rowsAfectadas = stmt.executeUpdate();

            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    permiso.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Permiso> obtenerPermisoPorId(int id) {
        String sql = "SELECT * FROM permisos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setId(rs.getInt("id"));
                permiso.setNombre(rs.getString("nombre"));
                permiso.setBorrado(rs.getBoolean("borrado"));

                return Optional.of(permiso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Permiso> obtenerTodosLosPermisos() {
        List<Permiso> permisos = new ArrayList<>();
        String sql = "SELECT * FROM permisos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setId(rs.getInt("id"));
                permiso.setNombre(rs.getString("nombre"));
                permiso.setBorrado(rs.getBoolean("borrado"));

                permisos.add(permiso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permisos;
    }

    @Override
    public boolean actualizarPermiso(Permiso permiso) {
        String sql = "UPDATE permisos SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, permiso.getNombre());
            stmt.setInt(2, permiso.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarPermisoPorId(int id) {
        String sql = "UPDATE permisos SET borrado = 1 WHERE id = ?";
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
