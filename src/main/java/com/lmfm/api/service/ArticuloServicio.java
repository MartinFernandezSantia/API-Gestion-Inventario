package com.lmfm.api.service;

import com.lmfm.api.dao.ArticuloDAO;
import com.lmfm.api.model.Articulo;

import java.util.List;
import java.util.Optional;

public class ArticuloServicio {

    private ArticuloDAO articuloDAO;

    public ArticuloServicio(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    public void crearArticulo(Articulo articulo) {
        articuloDAO.insertarArticulo(articulo);
    }

    public List<Articulo> getArticulos() {
        return articuloDAO.obtenerTodosLosArticulos();
    }

    public void actualizarArticulo(Articulo articulo) {
        articuloDAO.actualizarArticulo(articulo);
    }

    public void eliminarArticulo(int id) {
        articuloDAO.eliminarArticuloPorId(id);
    }

    public Optional<Articulo> buscarArticuloPorCodigo(int codigo) {
        return articuloDAO.obtenerArticuloPorCodigo(codigo);
    }
}
