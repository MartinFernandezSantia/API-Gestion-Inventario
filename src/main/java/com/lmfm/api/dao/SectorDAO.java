package com.lmfm.api.dao;


import  com.lmfm.api.model.Sector;
import java.util.List;
import java.util.Optional;

public interface SectorDAO {
    void insertarSector(Sector sector);
    Optional<Sector> obtenerSectorPorId(int id);
    List<Sector> obtenerTodosLosSectores();
    boolean actualizarSector(Sector sector);
    boolean eliminarSectorPorId(int id);
}

