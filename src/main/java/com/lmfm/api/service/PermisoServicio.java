package com.lmfm.api.service;


import com.lmfm.api.dao.PermisoDAO;
import com.lmfm.api.dao.PermisoDAOImpl;
import com.lmfm.api.model.Permiso;

import java.util.List;
import java.util.Optional;

public class PermisoServicio {

    private final PermisoDAO permisoDAO;

    public PermisoServicio() {
        this.permisoDAO = new PermisoDAOImpl();
    }

    public PermisoServicio(PermisoDAO permisoDAO) {
        this.permisoDAO = permisoDAO;
    }

    // Crear nuevo permiso
    public void crearPermiso(int nivel, String descripcion) {
        Permiso nuevoPermiso = new Permiso();
        nuevoPermiso.setNivel(nivel);
        nuevoPermiso.setDescripcion(descripcion);
        permisoDAO.insertarPermiso(nuevoPermiso);
    }

    // Listar permisos
    public List<Permiso> obtenerTodosLosPermisos() {
        return permisoDAO.obtenerTodosLosPermisos();
    }

    // Actualizar permiso
    public void actualizarPermiso(int nivel, String nuevaDescripcion) {
        Optional<Permiso> permisoOpt = permisoDAO.obtenerPermisoPorNivel(nivel);
        if (permisoOpt.isPresent()) {
            Permiso permiso = permisoOpt.get();
            permiso.setDescripcion(nuevaDescripcion);
            permisoDAO.actualizarPermiso(permiso);
        }
    }

    // Eliminar permiso
    public void eliminarPermiso(int id) {
        permisoDAO.eliminarPermisoPorId(id);
    }

    // Buscar permiso por nivel
    public Permiso buscarPermisoPorNivel(int nivel) {
        Optional<Permiso> permisoOpt = permisoDAO.obtenerPermisoPorNivel(nivel);
        return permisoOpt.orElse(null); // Retorna null si no encuentra ningún permiso por nivel
    }
}
