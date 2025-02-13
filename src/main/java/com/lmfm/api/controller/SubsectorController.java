package com.lmfm.api.controller;

import com.lmfm.api.dto.SubsectorRequest;
import com.lmfm.api.model.Sector;
import com.lmfm.api.model.Subsector;
import com.lmfm.api.service.SectorServicio;
import com.lmfm.api.service.SubsectorServicio;
import com.lmfm.api.translators.SubsectorTranslator;
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
@RequestMapping("subsector")
public class SubsectorController {

    @Operation(summary = "Crear nuevo Subsector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subsector creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PostMapping
    public ResponseEntity<?> crearSubsector(@RequestBody @Valid SubsectorRequest subsectorRequest) {
        Optional<Sector> sector = SectorServicio.getSectorPorId(subsectorRequest.getSectorId());

        if(sector.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Subsector nuevoSubsector = SubsectorTranslator.fromDTO(subsectorRequest, sector.get());

        if(!SubsectorServicio.crearSubsector(nuevoSubsector)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoSubsector.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obtener todos los Subsectores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subsectores encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Subsectores existentes")
    })
    @GetMapping
    public ResponseEntity<?> getSubsectores() {
        List<Subsector> subsectores = SubsectorServicio.obtenerTodosLosSubsectores();

        if(subsectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subsectores);
    }

    @Operation(summary = "Obtener Subsector por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subsector encontrado"),
            @ApiResponse(responseCode = "404", description = "Subsector no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubsectorPorId(@PathVariable int id) {
        Optional<Subsector> subsector = SubsectorServicio.getSubsectorPorId(id);

        if(subsector.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subsector.get());
    }

    @Operation(summary = "Eliminar Subsector por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subsector eliminado"),
            @ApiResponse(responseCode = "404", description = "Subsector no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSubsectorPorId(@PathVariable int id) {
        if(!SubsectorServicio.eliminarSubsector(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Subsector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subsector actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping
    public ResponseEntity<?> actualizarSubsector(@RequestBody @Valid SubsectorRequest subsectorRequest) {
        Optional<Sector> sector = SectorServicio.getSectorPorId(subsectorRequest.getSectorId());

        if(sector.isEmpty()){
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Subsector subsector = SubsectorTranslator.fromDTO(subsectorRequest, sector.get());

        if(!SubsectorServicio.actualizarSubsector(subsector)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
