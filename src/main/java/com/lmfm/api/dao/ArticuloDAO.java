package com.lmfm.api.dao;


import com.lmfm.api.model.Articulo;
import java.util.List;
import java.util.Optional;

public interface ArticuloDAO {
    void insertarArticulo(Articulo articulo);
    Optional<Articulo> obtenerArticuloPorCodigo(int codigo);
    List<Articulo> obtenerTodosLosArticulos();
    void actualizarArticulo(Articulo articulo);
    void eliminarArticuloPorId(int id);
}
