package com.lmfm.api.controller;

import com.lmfm.api.model.Categoria;
import com.lmfm.api.model.Turno;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.service.TurnoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crear nuevo Turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
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

    @Operation(summary = "Obtener todos los Turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turnos encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Turnos existentes")
    })
    @GetMapping
    public ResponseEntity<?> getTurnos() {
        List<Turno> turnos = TurnoServicio.obtenerTodosLosTurnos();

        if(turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(turnos);
    }

    @Operation(summary = "Obtener Turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno encontrado"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getTurnoPorId(@PathVariable int id) {
        Optional<Turno> turno = TurnoServicio.getTurnoPorId(id);

        if(turno.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno.get());
    }

    @Operation(summary = "Eliminar Turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno eliminado"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurnoPorId(@PathVariable int id) {
        if(!TurnoServicio.eliminarTurno(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody @Valid Turno turno) {
        if(!TurnoServicio.actualizarTurno(turno)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }

}
