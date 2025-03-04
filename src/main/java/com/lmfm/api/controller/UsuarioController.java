package com.lmfm.api.controller;


import com.lmfm.api.dto.ChangePassRequest;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.service.AuthServicio;
import com.lmfm.api.service.PermisoServicio;
import com.lmfm.api.service.UsuarioServicio;
import com.lmfm.api.translators.UsuarioTranslator;
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
@RequestMapping("usuarios")
public class UsuarioController {

    @Operation(summary = "Crear nuevo Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Optional<Permiso> permiso = PermisoServicio.getPermisoPorId(usuarioRequest.getPermisoId());

        if (permiso.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Usuario nuevoUsuario = UsuarioTranslator.fromDTO(usuarioRequest, permiso.get());

        // Si hubo un problema al intentar crear el usuario
        if (!UsuarioServicio.crearUsuario(nuevoUsuario)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoUsuario.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obtener todos los Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Usuarios existentes")
    })
    @GetMapping
    public ResponseEntity<?> getUsuarios() {
        List<Usuario> usuarios = UsuarioServicio.getUsuarios();

        // Si no hay usuarios
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obtener Usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @Operation(summary = "Obtener Usuario por legajo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<?> getUsuarioPorLegajo(@PathVariable int legajo) {
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorLegajo(legajo);

        // Si el Legajo no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @Operation(summary = "Eliminar Usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable int id) {
        // Si hubo algun error al eliminar el usuario
        if (!UsuarioServicio.eliminarUsuario(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar Usuario por Legajo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/legajo/{legajo}")
    public ResponseEntity<?> eliminarUsuarioPorLegajo(@PathVariable int legajo) {
        // Si hubo algun error al eliminar el usuario
        if (!UsuarioServicio.eliminarUsuarioPorLegajo(legajo)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping()
    public ResponseEntity<?> actualizarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Optional<Permiso> permiso = PermisoServicio.getPermisoPorId(usuarioRequest.getPermisoId());

        if (permiso.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Usuario usuario = UsuarioTranslator.fromDTO(usuarioRequest, permiso.get());

        // Si hubo algun problema al actualizar al usuario
        if(!UsuarioServicio.actualizarUsuario(usuario)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Usuario por Legajo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
    @PutMapping("/legajo")
    public ResponseEntity<?> actualizarUsuarioPorLegajo(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Optional<Permiso> permiso = PermisoServicio.getPermisoPorId(usuarioRequest.getPermisoId());

        if (permiso.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Usuario usuario = UsuarioTranslator.fromDTO(usuarioRequest, permiso.get());

        // Si hubo algun problema al actualizar al usuario
        if(!UsuarioServicio.actualizarUsuario(usuario)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cambiar contraseña de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos")
    })
    @PutMapping("/password")
    public ResponseEntity<?> cambiarPassword(@RequestBody @Valid ChangePassRequest changePassRequest) {
        if (!AuthServicio.validarPassword(changePassRequest.getNewPass())) {
            return ResponseEntity.badRequest().body("La contraseña no cumple los requisitos");
        };

        if (!UsuarioServicio.cambiarPassword(changePassRequest)) {
            return ResponseEntity.badRequest().body("Datos incorrectos");
        }

        return ResponseEntity.ok().build();
    }
}
