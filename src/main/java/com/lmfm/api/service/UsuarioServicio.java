package com.lmfm.api.service;

import com.lmfm.api.dao.mysql.UsuarioDAOImpl;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.translators.UsuarioTranslator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioServicio {

    private UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
    private UsuarioTranslator usuarioTranslator = new UsuarioTranslator();
    private Usuario usuario;

    public  UsuarioServicio() {}

    public UsuarioServicio(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean crearUsuario() {
        boolean passwordValida = AuthServicio.validarPassword(this.usuario.getPassword());
        Optional<Usuario> usuarioExiste = this.usuarioDAO.obtenerUsuarioPorLegajo(this.usuario.getLegajo());

        // Verifico si la contraseña cumple con los requerimientos o si el usuario ya existe
        if (!passwordValida || usuarioExiste.isPresent()) {
            return false;
        }

        // Hasheo la contraseña
        this.usuario.setPassword(AuthServicio.hashPassword(this.usuario.getPassword()));

        return usuarioDAO.insertarUsuario(usuarioTranslator.toDTO(this.usuario));
    }

    public List<Usuario> getUsuarios() {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }

    public boolean actualizarUsuario() {
        // Si el usuario no esta en la BD
        if (getUsuarioPorId(usuario.getId()).isEmpty()) {
            System.out.println(2);
            System.out.println(usuario.getId());
            return false;
        }

        return usuarioDAO.actualizarUsuario(usuarioTranslator.toDTO(usuario));
    }

    public boolean eliminarUsuario(int id) {
        // Si no hay usuario que coincida con el id
        if (getUsuarioPorId(id).isEmpty()) {
            return false;
        }

        return usuarioDAO.eliminarUsuarioPorId(id);
    }

    public Optional<Usuario> getUsuarioPorLegajo(int legajo) {
        return usuarioDAO.obtenerUsuarioPorLegajo(legajo);
    }

    public Optional<Usuario> getUsuarioPorId(int id) {
        return usuarioDAO.obtenerUsuarioPorId(id);
    }
}
