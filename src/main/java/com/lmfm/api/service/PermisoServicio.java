package com.lmfm.api.service;


import com.lmfm.api.dao.PermisoDAO;
import com.lmfm.api.dao.mysql.PermisoDAOImpl;
import com.lmfm.api.model.Permiso;

import java.util.List;
import java.util.Optional;

public class PermisoServicio {

    private static PermisoDAO permisoDAO = new PermisoDAOImpl();

    public static boolean crearPermiso(Permiso permiso) {
        permisoDAO.insertarPermiso(permiso);
        return permiso.getId() != null;
    }

    public static List<Permiso> obtenerTodosLosPermisos() {
        return permisoDAO.obtenerTodosLosPermisos();
    }

    public static boolean actualizarPermiso(Permiso permiso) {
        return permisoDAO.actualizarPermiso(permiso);
    }

    public static boolean eliminarPermiso(int id) {
         return permisoDAO.eliminarPermisoPorId(id);
    }

    public static Optional<Permiso> getPermisoPorId(int id) {
        return permisoDAO.obtenerPermisoPorId(id);
    }
}
