package com.lmfm.api.translators;

import com.lmfm.api.dao.mysql.PermisoDAOImpl;
import com.lmfm.api.dto.UsuarioRequest;
import com.lmfm.api.model.Permiso;
import com.lmfm.api.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioTranslator {

    public static Usuario fromDTO(UsuarioRequest usuarioRequest, Permiso permiso) {
        Usuario usuario = new Usuario();

        usuario.setId(usuarioRequest.getId());
        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setApellido(usuarioRequest.getApellido());
        usuario.setLegajo(usuarioRequest.getLegajo());
        usuario.setPassword(usuarioRequest.getPassword());
        usuario.setPermiso(permiso);
        usuario.setBorrado(usuarioRequest.isBorrado());

        return usuario;
    }

    public static UsuarioRequest toDTO(Usuario usuario) {
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setId(usuario.getId());
        usuarioRequest.setNombre(usuario.getNombre());
        usuarioRequest.setApellido(usuario.getApellido());
        usuarioRequest.setLegajo(usuario.getLegajo());
        usuarioRequest.setPassword(usuario.getPassword());
        usuarioRequest.setPermisoId(usuario.getPermiso().getId());
        usuarioRequest.setBorrado(usuario.isBorrado());

        return usuarioRequest;
    }
}
