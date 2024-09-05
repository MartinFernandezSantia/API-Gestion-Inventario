package com.lmfm.api.controller;

import com.lmfm.api.dao.UsuarioDAOImpl;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.service.AuthServicio;
import com.lmfm.api.service.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAOImpl usuarioDAO;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        usuarioRequest.setPassword(AuthServicio.hashPassword(usuarioRequest.getPassword()));
        Usuario nuevoUsuario = new Usuario(usuarioRequest);

        // ¡Implementar esta linea directamente en el if cuando el metodo sea static!
        boolean crear = usuarioDAO.insertarUsuario(nuevoUsuario);

        // Si hubo un problema al intentar crear el usuario
        if (!crear) {
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
        List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();

        // Si no hay usuarios
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioDAO.obtenerUsuarioPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<?> getUsuarioPorLegajo(@PathVariable int legajo) {
        Optional<Usuario> usuario = usuarioDAO.obtenerUsuarioPorLegajo(legajo);

        // Si el Legajo no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioDAO.obtenerUsuarioPorId(id);

        // Si el ID no coincidio con ningun usuario existente
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioDAO.eliminarUsuarioPorId(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuario = usuarioDAO.obtenerUsuarioPorId(usuarioRequest.getId());

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuario.get().setNombre(usuarioRequest.getNombre());
        usuario.get().setApellido(usuarioRequest.getApellido());
        usuario.get().setLegajo(usuarioRequest.getLegajo());
        usuario.get().setPermisoId(usuarioRequest.getPermisoId());

        boolean actualizado = usuarioDAO.actualizarUsuario(usuario.get(), usuarioRequest.getPassword());

        if(!actualizado) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
