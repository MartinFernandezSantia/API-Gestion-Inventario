package com.lmfm.api.translators;

import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.*;

import java.sql.Timestamp;

public class MovimientosInventarioTranslator {

    public static MovimientosInventario fromDTO(
            MovimientosInventarioRequest movimientosInventarioRequest,
            Articulo articulo, Usuario usuario, Turno turno, Subsector subsector) {

        MovimientosInventario movimientosInventario = new MovimientosInventario();

        movimientosInventario.setCantidad(movimientosInventarioRequest.getCantidad());
        movimientosInventario.setTipoMovimiento(movimientosInventarioRequest.isTipoMovimiento());
        movimientosInventario.setId(movimientosInventarioRequest.getId());
        movimientosInventario.setArticulo(articulo);
        movimientosInventario.setSubsector(subsector);
        movimientosInventario.setUsuario(usuario);
        movimientosInventario.setTurno(turno);
        movimientosInventario.setEsDiferencia(movimientosInventarioRequest.isEsDiferencia());
        movimientosInventario.setEsPedido(movimientosInventarioRequest.isEsPedido());
        movimientosInventario.setFechaHora(Timestamp.valueOf(movimientosInventarioRequest.getFechaHora()));

        return movimientosInventario;
    }

    public static MovimientosInventarioRequest toDTO(MovimientosInventario movimientosInventario) {
        MovimientosInventarioRequest request = new MovimientosInventarioRequest();

        request.setArticuloId(movimientosInventario.getArticulo().getId());
        request.setCantidad(movimientosInventario.getCantidad());
        request.setSubsectorId(movimientosInventario.getSubsector().getId());
        request.setEsDiferencia(movimientosInventario.isEsDiferencia());
        request.setEsPedido(movimientosInventario.isEsPedido());
        request.setTipoMovimiento(movimientosInventario.isTipoMovimiento());
        request.setUsuarioId(movimientosInventario.getUsuario().getId());
        request.setTurnoId(movimientosInventario.getTurno().getId());
        request.setFechaHora(movimientosInventario.getFechaHora().toString());
        request.setId(movimientosInventario.getId());

        return request;
    }

}