package com.lmfm.api.service;

import com.lmfm.api.dao.SubsectorDAO;
import com.lmfm.api.dao.mysql.SubsectorDAOImpl;
import com.lmfm.api.dto.SubsectorRequest;
import com.lmfm.api.model.Subsector;
import com.lmfm.api.translators.SubsectorTranslator;

import java.util.List;
import java.util.Optional;

public class SubsectorServicio {

    private static SubsectorDAO subsectorDAO = new SubsectorDAOImpl();


    // Crear nuevo subsector
    public static boolean crearSubsector(Subsector subsector) {
        SubsectorRequest request = SubsectorTranslator.toDTO(subsector);
        subsectorDAO.insertarSubsector(request);

        return subsector.getId() != null;
    }

    // Listar subsectores
    public static List<Subsector> obtenerTodosLosSubsectores() {
        return subsectorDAO.obtenerTodosLosSubsectores();
    }

    // Actualizar subsector
    public static boolean actualizarSubsector(Subsector subsector) {
        SubsectorRequest request = SubsectorTranslator.toDTO(subsector);

        return subsectorDAO.actualizarSubsector(request);
    }

    // Eliminar subsector
    public static boolean eliminarSubsector(int id) {
        return subsectorDAO.eliminarSubsectorPorId(id);
    }

    // Buscar subsector por ID
    public static Subsector getSubsectorPorId(int id) {
        Optional<Subsector> subsectorOpt = subsectorDAO.obtenerSubsectorPorId(id);
        return subsectorOpt.orElse(null); // Retorna null si no encuentra ningún subsector por ID
    }
}
