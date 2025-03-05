package com.lmfm.api.service;

import com.lmfm.api.dao.mysql.UsuarioDAOImpl;
import com.lmfm.api.dto.ChangePassRequest;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.translators.UsuarioTranslator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioServicio {

    private static UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    public UsuarioServicio() {}

    public static boolean crearUsuario(Usuario usuario) {
//        boolean passwordValida = AuthServicio.validarPassword(usuario.getPassword());

        // Verifico si la contraseña cumple con los requerimientos
//        if (!passwordValida) {
//            return false;
//        }

        // Hasheo la contraseña
//        usuario.setPassword(AuthServicio.hashPassword(usuario.getPassword()));

        // Creo al usuario
        UsuarioRequest usuarioRequest = UsuarioTranslator.toDTO(usuario);
        usuarioDAO.insertarUsuario(usuarioRequest);
        usuario.setId(usuarioRequest.getId());

        // Si el usuario fue creado correctamente id != null
        return usuarioRequest.getId() != null;
    }

    public static boolean actualizarUsuario(Usuario usuario) {

        return usuarioDAO.actualizarUsuario(UsuarioTranslator.toDTO(usuario));
    }

    public static boolean actualizarUsuarioPorLegajo(Usuario usuario) {

        return usuarioDAO.actualizarUsuarioPorLegajo(UsuarioTranslator.toDTO(usuario));
    }

    public static boolean cambiarPassword(ChangePassRequest changePassRequest) {
        return usuarioDAO.cambiarPassword(changePassRequest);
    }

    public static boolean blanquearPasswordPorLegajo(int legajo) {
        return usuarioDAO.blanquearPasswordPorLegajo(legajo);
    }

    public static boolean eliminarUsuario(int id) {
        return usuarioDAO.eliminarUsuarioPorId(id);
    }

    public static boolean eliminarUsuarioPorLegajo(int legajo) { return usuarioDAO.eliminarUsuarioPorLegajo(legajo); }

    public static Optional<Usuario> getUsuarioPorLegajo(int legajo) {
        return usuarioDAO.obtenerUsuarioPorLegajo(legajo);
    }

    public static Optional<Usuario> getUsuarioPorId(int id) {
        return usuarioDAO.obtenerUsuarioPorId(id);
    }

    public static List<Usuario> getUsuarios() {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }
}
