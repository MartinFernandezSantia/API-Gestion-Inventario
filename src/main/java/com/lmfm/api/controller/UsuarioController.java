package com.lmfm.api.controller;

import com.lmfm.api.dao.mysql.PermisoDAOImpl;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.service.PermisoServicio;
import com.lmfm.api.service.UsuarioServicio;
import com.lmfm.api.translators.UsuarioTranslator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
// 127.0.0.1/usuarios
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

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

    @GetMapping
    public ResponseEntity<?> getUsuarios() {
        List<Usuario> usuarios = UsuarioServicio.getUsuarios();

        // Si no hay usuarios
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<?> getUsuarioPorLegajo(@PathVariable int legajo) {
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorLegajo(legajo);

        // Si el Legajo no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable int id) {
        // Si hubo algun error al eliminar el usuario
        if (!UsuarioServicio.eliminarUsuario(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

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
}
