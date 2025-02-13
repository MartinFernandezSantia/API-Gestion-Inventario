package com.lmfm.api.controller;

import com.lmfm.api.model.Categoria;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.service.PermisoServicio;
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
@RequestMapping("permisos")
public class PermisoController {

    @Operation(summary = "Crear nuevo Permiso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permiso creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
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

    @Operation(summary = "Obtener todos los Permisos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permisos encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Permisos existentes")
    })
    @GetMapping
    public ResponseEntity<?> getPermisos() {
        List<Permiso> permisos = PermisoServicio.obtenerTodosLosPermisos();

        if(permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(permisos);
    }

    @Operation(summary = "Obtener Permiso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPermisoPorId(@PathVariable int id) {
        Optional<Permiso> permiso = PermisoServicio.getPermisoPorId(id);

        if(permiso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permiso.get());
    }

    @Operation(summary = "Eliminar Permiso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso eliminado"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPermisoPorId(@PathVariable int id) {
        if(!PermisoServicio.eliminarPermiso(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Permiso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permiso actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping
    public ResponseEntity<?> actualizarPermiso(@RequestBody @Valid Permiso permiso) {
        if(!PermisoServicio.actualizarPermiso(permiso)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }
}
