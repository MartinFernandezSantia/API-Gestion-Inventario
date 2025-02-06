package com.lmfm.api.service;


import com.lmfm.api.dao.TurnoDAO;
import com.lmfm.api.dao.mysql.TurnoDAOImpl;
import com.lmfm.api.model.Turno;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class TurnoServicio {

    private static TurnoDAO turnoDAO = new TurnoDAOImpl();

    public static boolean crearTurno(Turno turno) {
        turnoDAO.insertarTurno(turno);

        return turno.getId() != null;
    }

    public static List<Turno> obtenerTodosLosTurnos() {
        return turnoDAO.obtenerTodosLosTurnos();
    }

    public static boolean actualizarTurno(Turno turno) {
        return turnoDAO.actualizarTurno(turno);
    }

    public static boolean eliminarTurno(int id) {
        return turnoDAO.eliminarTurnoPorId(id);
    }

    public static Turno getTurnoPorId(int id) {
        Optional<Turno> turnoOpt = turnoDAO.obtenerTurnoPorId(id);
        return turnoOpt.orElse(null); // Retorna null si no encuentra ningún turno por ID
    }
}
