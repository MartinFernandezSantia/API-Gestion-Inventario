package com.lmfm.api.controller;

import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.*;
import com.lmfm.api.service.*;
import com.lmfm.api.translators.MovimientosInventarioTranslator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("movimientos")
public class MovimientosInventarioController {

    @Operation(summary = "Crear nuevo Movimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody @Valid MovimientosInventarioRequest request) {
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorId(request.getArticuloId());
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorId(request.getUsuarioId());
        Optional<Turno> turno = TurnoServicio.getTurnoPorId(request.getTurnoId());
        Optional<Subsector> subsector = SubsectorServicio.getSubsectorPorId(request.getSubsectorId());

        if (articulo.isEmpty() || usuario.isEmpty() || turno.isEmpty() || subsector.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        MovimientosInventario movimientosInventario = MovimientosInventarioTranslator.fromDTO(
                request,
                articulo.get(),
                usuario.get(),
                turno.get(),
                subsector.get()
                );

        if (!MovimientosInventarioServicio.crearMovimiento(movimientosInventario)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movimientosInventario.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Crear varios Movimientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimientos creados"),
            @ApiResponse(responseCode = "400", description = "Ningun Movimiento pudo ser creado"),
            @ApiResponse(responseCode = "207", description = "Algunos Movimientos no fueron creados")
    })
    @PostMapping("/varios")
    public ResponseEntity<?> crearVarios(@RequestBody @Valid List<MovimientosInventarioRequest> request) {
        List<MovimientosInventarioRequest> failedInserts = MovimientosInventarioServicio.crearMovimientos(request);

        if (!failedInserts.isEmpty()) {
            if (failedInserts.size() == request.size()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "Ningun elemento pudo ser creado",
                        "failedInserts", failedInserts
                ));
            }
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(Map.of(
                    "message", "Algunos elementos no fueron creados",
                    "failedInserts", failedInserts
            ));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Todos los elementos fueron creados");
    }


    @Operation(summary = "Obtener todos los Movimientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Movimientos existentes")
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MovimientosInventario> movimientos = MovimientosInventarioServicio.obtenerTodosLosMovimientos();

        // Si no hay usuarios
        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movimientos);
    }

    @Operation(summary = "Obtener Movimiento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPorId(@PathVariable int id) {
        Optional<MovimientosInventario> movimiento = MovimientosInventarioServicio.getMovimientoPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (movimiento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movimiento.get());
    }

    @Operation(summary = "Eliminar Movimiento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento eliminado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable int id) {
        // Si hubo algun error al eliminar el usuario
        if (!MovimientosInventarioServicio.eliminarMovimiento(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Movimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping()
    public ResponseEntity<?> actualizar(@RequestBody @Valid MovimientosInventarioRequest request) {
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorId(request.getArticuloId());
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorId(request.getUsuarioId());
        Optional<Turno> turno = TurnoServicio.getTurnoPorId(request.getTurnoId());
        Optional<Subsector> subsector = SubsectorServicio.getSubsectorPorId(request.getSubsectorId());

        if (articulo.isEmpty() || usuario.isEmpty() || turno.isEmpty() || subsector.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        MovimientosInventario movimientosInventario = MovimientosInventarioTranslator.fromDTO(
                request,
                articulo.get(),
                usuario.get(),
                turno.get(),
                subsector.get()
        );

        // Si hubo algun problema al actualizar al usuario
        if(!MovimientosInventarioServicio.actualizarMovimiento(movimientosInventario)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
