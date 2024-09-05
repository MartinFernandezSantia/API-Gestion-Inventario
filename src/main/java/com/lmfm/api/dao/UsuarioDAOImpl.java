package com.lmfm.api.dao;


import com.lmfm.api.model.Usuario;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.service.AuthServicio;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, legajo, contraseña, permiso_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getLegajo());
            stmt.setString(4, usuario.getPassword());
            stmt.setInt(5, usuario.getPermisoId());

            int rowsAfectadas = stmt.executeUpdate();

            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    usuario.setId(id);
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorLegajo(int legajo) {
        String sql = "SELECT * FROM usuarios WHERE legajo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, legajo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermisoId(rs.getInt("permiso_id"));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermisoId(rs.getInt("permiso_id"));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermisoId(rs.getInt("permiso_id"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario, String nuevaPassword) {
        // Construir la consulta SQL con un SET dinámico basado en los campos proporcionados
        StringBuilder sql = new StringBuilder("UPDATE usuarios SET nombre = ?, apellido = ?, permiso_id = ?, legajo = ?");

        // Solo agregar la columna de contraseña si se proporciona una nueva contraseña
        if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
            sql.append(", contraseña = ?");
        }

        sql.append(" WHERE id = ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Establecer los valores para los campos obligatorios
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getPermisoId());
            stmt.setInt(4, usuario.getLegajo());

            // Si se proporciona una nueva contraseña, establecerla en el PreparedStatement
            int index = 5;
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                stmt.setString(index++, AuthServicio.hashPassword(nuevaPassword));
            }

            // Establecer el valor del legajo
            stmt.setInt(index, usuario.getId());

            // Ejecutar la actualización
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarUsuarioPorId(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
