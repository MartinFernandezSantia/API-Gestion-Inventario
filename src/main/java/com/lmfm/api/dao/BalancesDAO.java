package com.lmfm.api.dao;

import com.lmfm.api.model.Balances;

import java.util.List;
import java.util.Optional;

public interface BalancesDAO {
    void insertarBalance(Balances balance);
    Optional<Balances> obtenerBalancePorId(int id);
    List<Balances> obtenerTodosLosBalances();
    void actualizarBalance(Balances balance);
    void eliminarBalancePorId(int id);
}
