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

        return request.getId() != null;
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

    // Buscar subsector por ID !!CHEKEAR 2 RELATED PROBLEMS -> LO PASÉ A OPTIONAL PARA EL CONTROLADOR
    public static Optional<Subsector> getSubsectorPorId(int id) {
         return subsectorDAO.obtenerSubsectorPorId(id);
    }
}
