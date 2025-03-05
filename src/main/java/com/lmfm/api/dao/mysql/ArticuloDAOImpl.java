package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.ArticuloDAO;
import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.model.Categoria;
import com.lmfm.api.service.CategoriaServicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticuloDAOImpl implements ArticuloDAO {

    @Override
    public void insertarArticulo(ArticuloRequest articulo) {
        String sql = "INSERT INTO articulos (codigo, nombre, stock, limite, categoria_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, articulo.getCodigo());
            stmt.setString(2, articulo.getNombre());
            stmt.setInt(3, articulo.getStock());
            stmt.setObject(4, articulo.getLimite(), Types.INTEGER);
            stmt.setObject(5, articulo.getCategoriaId(), Types.INTEGER);


            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    articulo.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Articulo> obtenerArticuloPorCodigo(int codigo) {
        String sql = "SELECT * FROM articulos WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Articulo articulo = new Articulo();
                Categoria categoria = CategoriaServicio.getCategoriaPorId(
                        rs.getInt("categoria_id")
                ).orElse(null);

                articulo.setId(rs.getInt("id"));
                articulo.setCodigo(rs.getInt("codigo"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));
                articulo.setLimite(rs.getObject("limite", Integer.class));
                articulo.setFechaHora(rs.getTimestamp("fecha_hora"));
                articulo.setCategoria(categoria);

                return Optional.of(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Articulo> obtenerArticuloPorId(int id) {
        String sql = "SELECT * FROM articulos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Articulo articulo = new Articulo();
                Categoria categoria = CategoriaServicio.getCategoriaPorId(
                        rs.getInt("categoria_id")
                ).orElse(null);

                articulo.setId(rs.getInt("id"));
                articulo.setCodigo(rs.getInt("codigo"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));
                articulo.setLimite(rs.getObject("limite", Integer.class));
                articulo.setFechaHora(rs.getTimestamp("fecha_hora"));
                articulo.setCategoria(categoria);

                return Optional.of(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Articulo> obtenerTodosLosArticulos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Articulo articulo = new Articulo();
                Categoria categoria = CategoriaServicio.getCategoriaPorId(
                        rs.getInt("categoria_id")
                ).orElse(null);

                articulo.setId(rs.getInt("id"));
                articulo.setCodigo(rs.getInt("codigo"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setStock(rs.getInt("stock"));
                articulo.setLimite(rs.getObject("limite", Integer.class));
                articulo.setFechaHora(rs.getTimestamp("fecha_hora"));
                articulo.setCategoria(categoria);

                articulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    @Override
    public boolean actualizarArticuloPorCodigo(ArticuloRequest articulo) {
        String sql = "UPDATE articulos SET nombre = ?, stock = ?, limite = ?, categoria_id = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getNombre());
            stmt.setInt(2, articulo.getStock());
            stmt.setObject(3, articulo.getLimite(), Types.INTEGER);
            stmt.setInt(4, articulo.getCategoriaId());
            stmt.setInt(5, articulo.getCodigo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarArticuloPorId(int id) {
        String sql = "UPDATE articulos SET borrado = 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarStock(int articuloID, int cantidad) {
        String sql = "UPDATE articulos SET stock = stock + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, articuloID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
