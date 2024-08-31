package com.lmfm.api.dao;

import model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void insertarUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorLegajo(int legajo);
    List<Usuario> obtenerTodosLosUsuarios();
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuarioPorId(int id);
}
