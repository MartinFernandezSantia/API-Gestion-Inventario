package com.lmfm.api.controller;

import com.lmfm.api.model.Sector;
import com.lmfm.api.service.SectorServicio;
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

    @GetMapping
    public ResponseEntity<?> getSector() {
        List<Sector> sectores = SectorServicio.obtenerTodosLosSectores();

        if(sectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sectores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSectorPorId(@PathVariable int id) {
        Optional<Sector> sector = SectorServicio.getSectorPorId(id);

        if(sector.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sector.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSectorPorId (@PathVariable int id) {
        if(!SectorServicio.eliminarSector(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> actualizarSector(@RequestBody @Valid Sector sector) {
        if(!SectorServicio.actualizarSector(sector)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
