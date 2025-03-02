package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.BalancesDAO;
import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.model.Balances;
import com.lmfm.api.bd.DatabaseConnection;
import com.lmfm.api.service.ArticuloServicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BalancesDAOImpl implements BalancesDAO {

    @Override
    public void insertarBalance(BalancesRequest balance) {
        String sql = "INSERT INTO balances (articulo_id, stock, stock_real, fecha_hora) " +
                "SELECT ?, stock, ?, ? FROM articulos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, balance.getArticuloId());
            stmt.setInt(2, balance.getStockReal());
            stmt.setTimestamp(3, Timestamp.valueOf(balance.getFechaHora()));
            stmt.setInt(4, balance.getArticuloId());

            int rowsAfectadas = stmt.executeUpdate();

            // Agrego ID generado al objeto
            if (rowsAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    balance.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Balances> obtenerBalancePorId(int id) {
        String sql = "SELECT * FROM balances WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Balances balance = new Balances();
                Articulo articulo = ArticuloServicio.getArticuloPorId(rs.getInt("articulo_id")).get();

                balance.setId(rs.getInt("id"));
                balance.setArticulo(articulo);
                balance.setStock(rs.getInt("stock"));
                balance.setStockReal(rs.getInt("stock_real"));
                balance.setFechaHora(rs.getTimestamp("fecha_hora"));

                return Optional.of(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Balances> obtenerTodosLosBalances() {
        List<Balances> balances = new ArrayList<>();
        String sql = "SELECT * FROM balances";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Balances balance = new Balances();
                Articulo articulo = ArticuloServicio.getArticuloPorId(rs.getInt("articulo_id")).get();

                balance.setId(rs.getInt("id"));
                balance.setArticulo(articulo);
                balance.setStock(rs.getInt("stock"));
                balance.setStockReal(rs.getInt("stock_real"));
                balance.setFechaHora(rs.getTimestamp("fecha_hora"));
                balances.add(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balances;
    }

    @Override
    public boolean actualizarBalance(BalancesRequest balance) {
        String sql = "UPDATE balances SET articulo_id = ?, stock = ?, stock_real = ?, fecha_hora = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, balance.getArticuloId());
            stmt.setInt(2, balance.getStock());
            stmt.setInt(3, balance.getStockReal());
            stmt.setTimestamp(4, Timestamp.valueOf(balance.getFechaHora()));
            stmt.setInt(5, balance.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarBalancePorId(int id) {
        String sql = "DELETE FROM balances WHERE id = ?";
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
