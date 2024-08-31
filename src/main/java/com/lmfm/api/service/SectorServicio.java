package com.lmfm.api.service;

import com.lmfm.api.dao.SectorDAO;
import com.lmfm.api.model.Sector;

import java.util.List;
import java.util.Optional;

public class SectorServicio {

    private final SectorDAO sectorDAO;

    public SectorServicio(SectorDAO sectorDAO) {
        this.sectorDAO = sectorDAO;
    }

    // Crear nuevo sector
    public void crearSector(String nombre) {
        Sector nuevoSector = new Sector();
        nuevoSector.setNombre(nombre);
        sectorDAO.insertarSector(nuevoSector);
    }

    // Listar sectores
    public List<Sector> obtenerTodosLosSectores() {
        return sectorDAO.obtenerTodosLosSectores();
    }

    // Actualizar sector
    public void actualizarSector(int id, String nuevoNombre) {
        Optional<Sector> sectorOpt = sectorDAO.obtenerSectorPorId(id);
        if (sectorOpt.isPresent()) {
            Sector sector = sectorOpt.get();
            sector.setNombre(nuevoNombre);
            sectorDAO.actualizarSector(sector);
        }
    }

    // Eliminar sector
    public void eliminarSector(int id) {
        sectorDAO.eliminarSectorPorId(id);
    }

    // Buscar sector por ID
    public Sector buscarSectorPorId(int id) {
        Optional<Sector> sectorOpt = sectorDAO.obtenerSectorPorId(id);
        return sectorOpt.orElse(null); // Retorna null si no encuentra ningún sector por ID
    }
}
