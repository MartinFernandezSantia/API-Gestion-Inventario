package com.lmfm.api.controller;

import com.lmfm.api.model.Sector;
import com.lmfm.api.service.SectorServicio;
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
@RequestMapping("sector")
public class SectorController {

    @Operation(summary = "Crear nuevo Sector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sector creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PostMapping
    public ResponseEntity<?> crearSector (@RequestBody @Valid Sector sector) {

        if(!SectorServicio.crearSector(sector)){
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sector.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obtener todos los Sectores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sectores encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Sectores existentes")
    })
    @GetMapping
    public ResponseEntity<?> getSectores() {
        List<Sector> sectores = SectorServicio.obtenerTodosLosSectores();

        if(sectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sectores);
    }

    @Operation(summary = "Obtener Sector por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sector encontrado"),
            @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSectorPorId(@PathVariable int id) {
        Optional<Sector> sector = SectorServicio.getSectorPorId(id);

        if(sector.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sector.get());
    }

    @Operation(summary = "Eliminar Sector por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sector eliminado"),
            @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSectorPorId (@PathVariable int id) {
        if(!SectorServicio.eliminarSector(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Sector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sector actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping
    public ResponseEntity<?> actualizarSector(@RequestBody @Valid Sector sector) {
        if(!SectorServicio.actualizarSector(sector)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
