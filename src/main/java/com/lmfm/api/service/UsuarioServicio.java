package com.lmfm.api.service;

import com.lmfm.api.dao.UsuarioDAO;
import com.lmfm.api.model.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioServicio {

    private UsuarioDAO usuarioDAO;

    public UsuarioServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void crearUsuario(Usuario usuario) {
        usuarioDAO.insertarUsuario(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminarUsuarioPorId(id);
    }

    public Optional<Usuario> buscarUsuarioPorLegajo(int legajo) {
        return usuarioDAO.obtenerUsuarioPorLegajo(legajo);
    }
}
