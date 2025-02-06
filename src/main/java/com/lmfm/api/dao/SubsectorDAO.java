package com.lmfm.api.dao;

import com.lmfm.api.dto.SubsectorRequest;
import com.lmfm.api.model.Subsector;

import java.util.List;
import java.util.Optional;

public interface SubsectorDAO {
    void insertarSubsector(SubsectorRequest subsector);
    Optional<Subsector> obtenerSubsectorPorId(int id);
    List<Subsector> obtenerTodosLosSubsectores();
    boolean actualizarSubsector(SubsectorRequest subsector);
    boolean eliminarSubsectorPorId(int id);
}
