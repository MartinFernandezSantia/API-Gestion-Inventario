package com.lmfm.api.service;

import com.lmfm.api.dao.BalancesDAO;
import com.lmfm.api.dao.mysql.BalancesDAOImpl;
import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.Balances;
import com.lmfm.api.translators.BalancesTranslator;

import java.util.List;
import java.util.Optional;

public class BalancesServicio {

    private static BalancesDAO balancesDAO = new BalancesDAOImpl();

    // Crear nuevo balance
    public static boolean crearBalance(Balances balance) {
        BalancesRequest balancesRequest = BalancesTranslator.toDTO(balance);
        balancesDAO.insertarBalance(balancesRequest);

        return balancesRequest.getId() != null;
    }

    // Listar balances
    public static List<Balances> obtenerTodosLosBalances() {
        return balancesDAO.obtenerTodosLosBalances();
    }

    // Actualizar balance
    public static boolean actualizarBalance(Balances balance) {
        BalancesRequest balancesRequest = BalancesTranslator.toDTO(balance);

        return balancesDAO.actualizarBalance(balancesRequest);
    }

    // Eliminar balance
    public static boolean eliminarBalance(int id) {
        return balancesDAO.eliminarBalancePorId(id);
    }

    // Buscar balance por ID
    public static Balances buscarBalancePorId(int id) {
        Optional<Balances> balanceOpt = balancesDAO.obtenerBalancePorId(id);
        return balanceOpt.orElse(null); // Retorna null si no encuentra ningún balance por ID
    }
}
