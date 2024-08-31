package com.lmfm.api.dao;


import com.lmfm.api.model.Permiso;
import java.util.List;
import java.util.Optional;

public interface PermisoDAO {
    void insertarPermiso(Permiso permiso);
    Optional<Permiso> obtenerPermisoPorNivel(int nivel);
    List<Permiso> obtenerTodosLosPermisos();
    void actualizarPermiso(Permiso permiso);
    void eliminarPermisoPorId(int id);
}
