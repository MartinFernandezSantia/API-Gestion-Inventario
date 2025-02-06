package com.lmfm.api.dao;


import com.lmfm.api.model.Turno;

import java.util.List;
import java.util.Optional;

public interface TurnoDAO {
    void insertarTurno(Turno turno);
    Optional<Turno> obtenerTurnoPorId(int id);
    List<Turno> obtenerTodosLosTurnos();
    boolean actualizarTurno(Turno turno);
    boolean eliminarTurnoPorId(int id);
}
