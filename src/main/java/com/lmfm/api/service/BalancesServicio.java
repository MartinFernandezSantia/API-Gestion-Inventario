package com.lmfm.api.service;

import com.lmfm.api.dao.BalancesDAO;
import com.lmfm.api.model.Balances;

import java.util.List;
import java.util.Optional;

public class BalancesServicio {

    private final BalancesDAO balancesDAO;

    public BalancesServicio(BalancesDAO balancesDAO) {
        this.balancesDAO = balancesDAO;
    }

    // Crear nuevo balance
    public void crearBalance(int articuloId, int stock, int stockReal, java.sql.Timestamp fechaHora) {
        Balances nuevoBalance = new Balances();
        nuevoBalance.setArticuloId(articuloId);
        nuevoBalance.setStock(stock);
        nuevoBalance.setStockReal(stockReal);
        nuevoBalance.setFechaHora(fechaHora);
        balancesDAO.insertarBalance(nuevoBalance);
    }

    // Listar balances
    public List<Balances> obtenerTodosLosBalances() {
        return balancesDAO.obtenerTodosLosBalances();
    }

    // Actualizar balance
    public void actualizarBalance(int id, int articuloId, int stock, int stockReal, java.sql.Timestamp fechaHora) {
        Optional<Balances> balanceOpt = balancesDAO.obtenerBalancePorId(id);
        if (balanceOpt.isPresent()) {
            Balances balance = balanceOpt.get();
            balance.setArticuloId(articuloId);
            balance.setStock(stock);
            balance.setStockReal(stockReal);
            balance.setFechaHora(fechaHora);
            balancesDAO.actualizarBalance(balance);
        }
    }

    // Eliminar balance
    public void eliminarBalance(int id) {
        balancesDAO.eliminarBalancePorId(id);
    }

    // Buscar balance por ID
    public Balances buscarBalancePorId(int id) {
        Optional<Balances> balanceOpt = balancesDAO.obtenerBalancePorId(id);
        return balanceOpt.orElse(null); // Retorna null si no encuentra ningún balance por ID
    }
}
