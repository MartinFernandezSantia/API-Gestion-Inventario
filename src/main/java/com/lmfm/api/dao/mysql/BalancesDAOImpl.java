package com.lmfm.api.dao.mysql;

import com.lmfm.api.dao.BalancesDAO;
import com.lmfm.api.model.Balances;
import com.lmfm.api.bd.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BalancesDAOImpl implements BalancesDAO {

    @Override
    public void insertarBalance(Balances balance) {
        String sql = "INSERT INTO balances (articulo_id, stock, stock_real, fecha_hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, balance.getArticuloId());
            stmt.setInt(2, balance.getStock());
            stmt.setInt(3, balance.getStockReal());
            stmt.setTimestamp(4, balance.getFechaHora());
            stmt.executeUpdate();
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
                balance.setId(rs.getInt("id"));
                balance.setArticuloId(rs.getInt("articulo_id"));
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
                balance.setId(rs.getInt("id"));
                balance.setArticuloId(rs.getInt("articulo_id"));
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
    public void actualizarBalance(Balances balance) {
        String sql = "UPDATE balances SET articulo_id = ?, stock = ?, stock_real = ?, fecha_hora = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, balance.getArticuloId());
            stmt.setInt(2, balance.getStock());
            stmt.setInt(3, balance.getStockReal());
            stmt.setTimestamp(4, balance.getFechaHora());
            stmt.setInt(5, balance.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarBalancePorId(int id) {
        String sql = "DELETE FROM balances WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
