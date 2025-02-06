package com.lmfm.api.service;

import com.lmfm.api.dao.CategoriaDAO;
import com.lmfm.api.dao.mysql.CategoriaDAOImpl;
import com.lmfm.api.model.Categoria;

import java.util.List;
import java.util.Optional;

public class CategoriaServicio {

    private static CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    public CategoriaServicio() {}

    // Crear nueva categoría
    public static void crearCategoria(String nombre) {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(nombre);
        categoriaDAO.insertarCategoria(nuevaCategoria);
    }

    // Listar categorías
    public static List<Categoria> obtenerTodasLasCategorias() {
        return categoriaDAO.obtenerTodasLasCategorias();
    }

    // Actualizar categoría
    public static void actualizarCategoria(int id, String nuevoNombre) {
        Optional<Categoria> categoriaOpt = categoriaDAO.obtenerCategoriaPorId(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setNombre(nuevoNombre);
            categoriaDAO.actualizarCategoria(categoria);
        }
    }

    // Eliminar categoría
    public static void eliminarCategoria(int id) {
        categoriaDAO.eliminarCategoriaPorId(id);
    }

    // Buscar categoría por ID
    public static Categoria buscarCategoriaPorId(int id) {
        Optional<Categoria> categoriaOpt = categoriaDAO.obtenerCategoriaPorId(id);
        return categoriaOpt.orElse(null); // Retorna null si no encuentra ninguna categoría por ID
    }
}
