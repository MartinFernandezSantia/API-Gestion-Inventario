package com.lmfm.api.dao;

import com.lmfm.api.model.Subsector;

import java.util.List;
import java.util.Optional;

public interface SubsectorDAO {
    void insertarSubsector(Subsector subsector);
    Optional<Subsector> obtenerSubsectorPorId(int id);
    List<Subsector> obtenerTodosLosSubsectores();
    void actualizarSubsector(Subsector subsector);
    void eliminarSubsectorPorId(int id);
}
