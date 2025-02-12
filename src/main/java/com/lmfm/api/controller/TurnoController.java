package com.lmfm.api.controller;

import com.lmfm.api.model.Categoria;
import com.lmfm.api.model.Turno;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.service.TurnoServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    @PostMapping
    public ResponseEntity<?> crearTurno(@RequestBody @Valid Turno turno) {

        if(!TurnoServicio.crearTurno(turno)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(turno.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getTurnos() {
        List<Turno> turnos = TurnoServicio.obtenerTodosLosTurnos();

        if(turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTurnoPorId(@PathVariable int id) {
        Optional<Turno> turno = TurnoServicio.getTurnoPorId(id);

        if(turno.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurnoPorId(@PathVariable int id) {
        if(!TurnoServicio.eliminarTurno(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody @Valid Turno turno) {
        if(!TurnoServicio.actualizarTurno(turno)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }

}
