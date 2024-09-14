package com.lmfm.api.controller;

import com.lmfm.api.dao.mysql.PermisoDAOImpl;
import com.lmfm.api.dao.mysql.UsuarioDAOImpl;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.service.UsuarioServicio;
import com.lmfm.api.translators.UsuarioTranslator;
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
    private UsuarioTranslator usuarioTranslator;

    @Autowired
    private  UsuarioServicio usuarioServicio;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        // !! Cambiar esta linea cuando se implemente PermisoServicio!!
        PermisoDAOImpl permisoDAO = new PermisoDAOImpl();
        Optional<Permiso> permiso = permisoDAO.obtenerPermisoPorId(usuarioRequest.getPermisoId());
        if (permiso.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Usuario nuevoUsuario = usuarioTranslator.fromDTO(usuarioRequest, permiso.get());
        UsuarioServicio usuarioServicio = new UsuarioServicio(nuevoUsuario);

        // Si hubo un problema al intentar crear el usuario
        if (!usuarioServicio.crearUsuario()) {
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
        List<Usuario> usuarios = usuarioServicio.getUsuarios();

        // Si no hay usuarios
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioServicio.getUsuarioPorId(id);

        // Si el ID no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @GetMapping("/legajo/{legajo}")
    public ResponseEntity<?> getUsuarioPorLegajo(@PathVariable int legajo) {
        Optional<Usuario> usuario = usuarioServicio.getUsuarioPorLegajo(legajo);

        // Si el Legajo no coincide con un usuario en la BD
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable int id) {
        // Si hubo algun error al eliminar el usuario
        if (!usuarioServicio.eliminarUsuario(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        // !! Cambiar esta linea cuando se implemente PermisoServicio!!
        PermisoDAOImpl permisoDAO = new PermisoDAOImpl();
        Optional<Permiso> permiso = permisoDAO.obtenerPermisoPorId(usuarioRequest.getPermisoId());
        if (permiso.isEmpty()) {
            System.out.println(1);
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        usuarioServicio = new UsuarioServicio(usuarioTranslator.fromDTO(usuarioRequest, permiso.get()));

        // Si hubo algun problema al actualizar al usuario
        if(!usuarioServicio.actualizarUsuario()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
