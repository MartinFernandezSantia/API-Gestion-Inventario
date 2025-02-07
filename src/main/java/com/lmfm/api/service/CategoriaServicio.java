package com.lmfm.api.service;

import com.lmfm.api.dao.CategoriaDAO;
import com.lmfm.api.dao.mysql.CategoriaDAOImpl;
import com.lmfm.api.model.Categoria;

import java.util.List;
import java.util.Optional;

public class CategoriaServicio {

    private static CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    
    // Crear nueva categoría
    public static boolean crearCategoria(Categoria categoria) {
        categoriaDAO.insertarCategoria(categoria);
        return categoria.getId() != null;
    }

    // Listar categorías
    public static List<Categoria> getCategorias() {
        return categoriaDAO.obtenerTodasLasCategorias();
    }

    // Actualizar categoría
    public static boolean actualizarCategoria(Categoria categoria) {
        return categoriaDAO.actualizarCategoria(categoria);
    }

    // Eliminar categoría
    public static boolean eliminarCategoria(int id) {
        return categoriaDAO.eliminarCategoriaPorId(id);
    }

    // Buscar categoría por ID
    public static Optional<Categoria> getCategoriaPorId(int id) {
        return categoriaDAO.obtenerCategoriaPorId(id);
    }
}
