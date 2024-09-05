package com.lmfm.api.dao;

import com.lmfm.api.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    boolean insertarUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorLegajo(int legajo);
    List<Usuario> obtenerTodosLosUsuarios();
    boolean actualizarUsuario(Usuario usuario, String nuevaPassword);
    boolean eliminarUsuarioPorId(int id);
}
