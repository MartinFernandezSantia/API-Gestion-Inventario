package com.lmfm.api.service;

import com.lmfm.api.dao.SectorDAO;
import com.lmfm.api.dao.mysql.SectorDAOImpl;
import com.lmfm.api.model.Sector;

import java.util.List;
import java.util.Optional;

public class SectorServicio {

    private static SectorDAO sectorDAO = new SectorDAOImpl();

    public static boolean crearSector(Sector sector) {
        sectorDAO.insertarSector(sector);

        return sector.getId() != null;
    }

    public static List<Sector> obtenerTodosLosSectores() {
        return sectorDAO.obtenerTodosLosSectores();
    }

    public static boolean actualizarSector(Sector sector) {
        return sectorDAO.actualizarSector(sector);
    }

    public static boolean eliminarSector(int id) {
         return sectorDAO.eliminarSectorPorId(id);
    }

    public static Optional<Sector> getSectorPorId(int id) {
         return sectorDAO.obtenerSectorPorId(id);
    }
}
