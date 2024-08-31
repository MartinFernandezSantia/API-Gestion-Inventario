package com.lmfm.api.dao;

import com.lmfm.api.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaDAO {
    void insertarCategoria(Categoria categoria);
    Optional<Categoria> obtenerCategoriaPorId(int id);
    List<Categoria> obtenerTodasLasCategorias();
    void actualizarCategoria(Categoria categoria);
    void eliminarCategoriaPorId(int id);
}
