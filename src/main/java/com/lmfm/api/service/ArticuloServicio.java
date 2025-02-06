package com.lmfm.api.service;

import com.lmfm.api.dao.ArticuloDAO;
import com.lmfm.api.dao.mysql.ArticuloDAOImpl;
import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.translators.ArticuloTranslator;

import java.util.List;
import java.util.Optional;

public class ArticuloServicio {

    private static ArticuloDAO articuloDAO = new ArticuloDAOImpl();

    public static boolean crearArticulo(Articulo articulo) {
        ArticuloRequest articuloRequest = ArticuloTranslator.toDTO(articulo);

        articuloDAO.insertarArticulo(articuloRequest);

        return articuloRequest.getId() != null;
    }

    public static List<Articulo> getArticulos() {
        return articuloDAO.obtenerTodosLosArticulos();
    }

    public static boolean actualizarArticulo(Articulo articulo) {
        ArticuloRequest articuloRequest = ArticuloTranslator.toDTO(articulo);

        return articuloDAO.actualizarArticulo(articuloRequest);
    }

    public static boolean eliminarArticulo(int id) {
        return articuloDAO.eliminarArticuloPorId(id);
    }

    public static Optional<Articulo> getArticuloPorCodigo(int codigo) {
        return articuloDAO.obtenerArticuloPorCodigo(codigo);
    }

    public static Optional<Articulo> getArticuloPorId(int id) {
        return articuloDAO.obtenerArticuloPorId(id);
    }


}
