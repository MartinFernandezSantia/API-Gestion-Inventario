package com.lmfm.api.dao.mysql;


import com.lmfm.api.dao.UsuarioDAO;
import com.lmfm.api.dto.ChangePassRequest;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.service.AuthServicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void insertarUsuario(UsuarioRequest usuarioRequest) {
        String sql = "INSERT INTO usuarios (nombre, apellido, legajo, contraseña, permiso_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuarioRequest.getNombre());
            stmt.setString(2, usuarioRequest.getApellido());
            stmt.setInt(3, usuarioRequest.getLegajo());
            stmt.setString(4, usuarioRequest.getPassword());
            stmt.setInt(5, usuarioRequest.getPermisoId());

            int rowsAfectadas = stmt.executeUpdate();

            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    usuarioRequest.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorLegajo(int legajo) {
        String sql = "SELECT * FROM usuarios WHERE legajo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, legajo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // !! Cambiar esta linea cuando se implemente PermisoServicio!!
                Optional<Permiso> permiso = new PermisoDAOImpl().obtenerPermisoPorId(rs.getInt("permiso_id"));
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermiso(permiso.get());

                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // !! Cambiar esta linea cuando se implemente PermisoServicio!!
                Optional<Permiso> permiso = new PermisoDAOImpl().obtenerPermisoPorId(rs.getInt("permiso_id"));
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermiso(permiso.get());

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
                // !! Cambiar esta linea cuando se implemente PermisoServicio!!
                Optional<Permiso> permiso = new PermisoDAOImpl().obtenerPermisoPorId(rs.getInt("permiso_id"));
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFechaHora(rs.getTimestamp("fecha_hora"));
                usuario.setLegajo(rs.getInt("legajo"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setPermiso(permiso.get());

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public boolean actualizarUsuario(UsuarioRequest usuarioRequest) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, permiso_id = ?, legajo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioRequest.getNombre());
            stmt.setString(2, usuarioRequest.getApellido());
            stmt.setInt(3, usuarioRequest.getPermisoId());
            stmt.setInt(4, usuarioRequest.getLegajo());
            stmt.setInt(5, usuarioRequest.getId());

            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarUsuarioPorLegajo(UsuarioRequest usuarioRequest) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, permiso_id = ? WHERE legajo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuarioRequest.getNombre());
            stmt.setString(2, usuarioRequest.getApellido());
            stmt.setInt(3, usuarioRequest.getPermisoId());
            stmt.setInt(4, usuarioRequest.getLegajo());

            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean eliminarUsuarioPorId(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarUsuarioPorLegajo(int legajo) {
        String sql = "DELETE FROM usuarios WHERE legajo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, legajo);
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cambiarPassword(ChangePassRequest changePassRequest) {
        String sql = "UPDATE usuarios SET contraseña = ? WHERE legajo = ? AND contraseña = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String newPass = AuthServicio.hashPassword(changePassRequest.getNewPass());
            String currPass = AuthServicio.hashPassword(changePassRequest.getCurrentPass());

            stmt.setString(1, newPass);
            stmt.setInt(2, changePassRequest.getLegajo());
            stmt.setString(3, currPass);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
