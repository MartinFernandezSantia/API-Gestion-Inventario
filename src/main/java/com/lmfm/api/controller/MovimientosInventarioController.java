package com.lmfm.api.controller;

import com.lmfm.api.dto.MovimientosInventarioRequest;
import com.lmfm.api.model.*;
import com.lmfm.api.service.*;
import com.lmfm.api.translators.MovimientosInventarioTranslator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movimientos")
public class MovimientosInventarioController {

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

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MovimientosInventario> movimientos = MovimientosInventarioServicio.obtenerTodosLosMovimientos();

        // Si no hay usuarios
        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPorId(@PathVariable int id) {
        Optional<MovimientosInventario> movimiento = MovimientosInventarioServicio.getMovimientoPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (movimiento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movimiento.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable int id) {
        // Si hubo algun error al eliminar el usuario
        if (!MovimientosInventarioServicio.eliminarMovimiento(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

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
