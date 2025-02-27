package com.lmfm.api.dao;

import com.lmfm.api.dto.ChangePassRequest;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    void insertarUsuario(UsuarioRequest usuarioRequest);
    Optional<Usuario> obtenerUsuarioPorLegajo(int legajo);
    Optional<Usuario> obtenerUsuarioPorId(int id);
    List<Usuario> obtenerTodosLosUsuarios();
    boolean actualizarUsuario(UsuarioRequest usuarioRequest);
    boolean actualizarUsuarioPorLegajo(UsuarioRequest usuarioRequest);
    boolean eliminarUsuarioPorId(int id);
    boolean eliminarUsuarioPorLegajo(int legajo);
    boolean cambiarPassword(ChangePassRequest changePassRequest);
}
