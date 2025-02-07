package com.lmfm.api.dao;


import com.lmfm.api.model.Permiso;
import java.util.List;
import java.util.Optional;

public interface PermisoDAO {
    void insertarPermiso(Permiso permiso);
    Optional<Permiso> obtenerPermisoPorNivel(int nivel);
    Optional<Permiso> obtenerPermisoPorId(int id);
    List<Permiso> obtenerTodosLosPermisos();
    boolean actualizarPermiso(Permiso permiso);
    boolean eliminarPermisoPorId(int id);
}
