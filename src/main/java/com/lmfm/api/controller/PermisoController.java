package com.lmfm.api.controller;

import com.lmfm.api.model.Categoria;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.service.PermisoServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("permisos")
public class PermisoController {

    @PostMapping
    public ResponseEntity<?> crearPermiso(@RequestBody @Valid Permiso permiso) {

        if(!PermisoServicio.crearPermiso(permiso)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(permiso.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getPermisos() {
        List<Permiso> permisos = PermisoServicio.obtenerTodosLosPermisos();

        if(permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(permisos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPermisoPorId(@PathVariable int id) {
        Optional<Permiso> permiso = PermisoServicio.getPermisoPorId(id);

        if(permiso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permiso.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPermisoPorId(@PathVariable int id) {
        if(!PermisoServicio.eliminarPermiso(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<?> actualizarPermiso(@RequestBody @Valid Permiso permiso) {
        if(!PermisoServicio.actualizarPermiso(permiso)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }
}
