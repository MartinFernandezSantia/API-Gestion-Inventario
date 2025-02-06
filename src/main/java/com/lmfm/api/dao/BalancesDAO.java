package com.lmfm.api.dao;

import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.Balances;

import java.util.List;
import java.util.Optional;

public interface BalancesDAO {
    void insertarBalance(BalancesRequest balance);
    Optional<Balances> obtenerBalancePorId(int id);
    List<Balances> obtenerTodosLosBalances();
    boolean actualizarBalance(BalancesRequest balance);
    boolean eliminarBalancePorId(int id);
}
