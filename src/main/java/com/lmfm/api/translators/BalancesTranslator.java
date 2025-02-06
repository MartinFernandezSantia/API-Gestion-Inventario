package com.lmfm.api.translators;

import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.model.Balances;

import java.sql.Timestamp;

public class BalancesTranslator {

    public static Balances fromDTO(BalancesRequest balancesRequest, Articulo articulo) {
        Balances balances = new Balances();

        balances.setStock(balancesRequest.getStock());
        balances.setId(balancesRequest.getId());
        balances.setFechaHora(Timestamp.valueOf(balancesRequest.getFechaHora()));
        balances.setArticulo(articulo);
        balances.setStockReal(balancesRequest.getStockReal());

        return balances;
    }

    public static BalancesRequest toDTO(Balances balances) {
        BalancesRequest balancesRequest = new BalancesRequest();

        balancesRequest.setArticuloId(balances.getArticulo().getId());
        balancesRequest.setId(balances.getId());
        balancesRequest.setFechaHora(balances.getFechaHora().toString());
        balancesRequest.setStock(balances.getStock());
        balancesRequest.setStockReal(balances.getStockReal());

        return balancesRequest;
    }
}