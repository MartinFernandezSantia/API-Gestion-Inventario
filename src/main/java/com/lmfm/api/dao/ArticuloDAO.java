package com.lmfm.api.dao;


import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import java.util.List;
import java.util.Optional;

public interface ArticuloDAO {
    void insertarArticulo(ArticuloRequest articulo);
    Optional<Articulo> obtenerArticuloPorCodigo(int codigo);
    Optional<Articulo> obtenerArticuloPorId(int id);
    List<Articulo> obtenerTodosLosArticulos();
    boolean actualizarArticulo(ArticuloRequest articulo);
    boolean eliminarArticuloPorId(int id);
}
