package com.lmfm.api.service;

import com.lmfm.api.dao.SubsectorDAO;
import com.lmfm.api.model.Subsector;

import java.util.List;
import java.util.Optional;

public class SubsectorServicio {

    private final SubsectorDAO subsectorDAO;

    public SubsectorServicio(SubsectorDAO subsectorDAO) {
        this.subsectorDAO = subsectorDAO;
    }

    // Crear nuevo subsector
    public void crearSubsector(String nombre, int sectorId) {
        Subsector nuevoSubsector = new Subsector();
        nuevoSubsector.setNombre(nombre);
        nuevoSubsector.setSectorId(sectorId);
        subsectorDAO.insertarSubsector(nuevoSubsector);
    }

    // Listar subsectores
    public List<Subsector> obtenerTodosLosSubsectores() {
        return subsectorDAO.obtenerTodosLosSubsectores();
    }

    // Actualizar subsector
    public void actualizarSubsector(int id, String nuevoNombre, int nuevoSectorId) {
        Optional<Subsector> subsectorOpt = subsectorDAO.obtenerSubsectorPorId(id);
        if (subsectorOpt.isPresent()) {
            Subsector subsector = subsectorOpt.get();
            subsector.setNombre(nuevoNombre);
            subsector.setSectorId(nuevoSectorId);
            subsectorDAO.actualizarSubsector(subsector);
        }
    }

    // Eliminar subsector
    public void eliminarSubsector(int id) {
        subsectorDAO.eliminarSubsectorPorId(id);
    }

    // Buscar subsector por ID
    public Subsector buscarSubsectorPorId(int id) {
        Optional<Subsector> subsectorOpt = subsectorDAO.obtenerSubsectorPorId(id);
        return subsectorOpt.orElse(null); // Retorna null si no encuentra ningún subsector por ID
    }
}
