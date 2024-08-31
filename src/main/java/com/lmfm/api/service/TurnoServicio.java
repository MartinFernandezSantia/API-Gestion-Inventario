package com.lmfm.api.service;


import com.lmfm.api.dao.TurnoDAO;
import com.lmfm.api.model.Turno;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public class TurnoServicio {

    private final TurnoDAO turnoDAO;

    public TurnoServicio(TurnoDAO turnoDAO) {
        this.turnoDAO = turnoDAO;
    }

    // Crear nuevo turno
    public void crearTurno(String nombre, Time horaInicio, Time horaFin) {
        Turno nuevoTurno = new Turno();
        nuevoTurno.setNombre(nombre);
        nuevoTurno.setHoraInicio(horaInicio);
        nuevoTurno.setHoraFin(horaFin);
        turnoDAO.insertarTurno(nuevoTurno);
    }

    // Listar turnos
    public List<Turno> obtenerTodosLosTurnos() {
        return turnoDAO.obtenerTodosLosTurnos();
    }

    // Actualizar turno
    public void actualizarTurno(int id, String nuevoNombre, Time nuevaHoraInicio, Time nuevaHoraFin) {
        Optional<Turno> turnoOpt = turnoDAO.obtenerTurnoPorId(id);
        if (turnoOpt.isPresent()) {
            Turno turno = turnoOpt.get();
            turno.setNombre(nuevoNombre);
            turno.setHoraInicio(nuevaHoraInicio);
            turno.setHoraFin(nuevaHoraFin);
            turnoDAO.actualizarTurno(turno);
        }
    }

    // Eliminar turno
    public void eliminarTurno(int id) {
        turnoDAO.eliminarTurnoPorId(id);
    }

    // Buscar turno por ID
    public Turno buscarTurnoPorId(int id) {
        Optional<Turno> turnoOpt = turnoDAO.obtenerTurnoPorId(id);
        return turnoOpt.orElse(null); // Retorna null si no encuentra ningún turno por ID
    }
}
