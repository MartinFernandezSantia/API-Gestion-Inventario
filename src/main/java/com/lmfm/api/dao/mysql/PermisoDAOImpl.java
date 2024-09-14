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
        String sql = "INSERT INTO permisos (nivel, descripcion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, permiso.getNivel());
            stmt.setString(2, permiso.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Permiso> obtenerPermisoPorNivel(int nivel) {
        String sql = "SELECT * FROM permisos WHERE nivel = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nivel);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setId(rs.getInt("id"));
                permiso.setNivel(rs.getInt("nivel"));
                permiso.setDescripcion(rs.getString("descripcion"));
                return Optional.of(permiso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Permiso> obtenerPermisoPorId(int id) {
        String sql = "SELECT * FROM permisos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setId(rs.getInt("id"));
                permiso.setNivel(rs.getInt("nivel"));
                permiso.setDescripcion(rs.getString("descripcion"));
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
                permiso.setNivel(rs.getInt("nivel"));
                permiso.setDescripcion(rs.getString("descripcion"));
                permisos.add(permiso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permisos;
    }

    @Override
    public void actualizarPermiso(Permiso permiso) {
        String sql = "UPDATE permisos SET descripcion = ? WHERE nivel = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, permiso.getDescripcion());
            stmt.setInt(2, permiso.getNivel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPermisoPorId(int id) {
        String sql = "DELETE FROM permisos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
