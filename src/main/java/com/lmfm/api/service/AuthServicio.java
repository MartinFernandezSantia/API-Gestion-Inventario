package com.lmfm.api.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lmfm.api.dao.UsuarioDAOImpl;
import com.lmfm.api.dto.LoginRequest;
import com.lmfm.api.model.Usuario;

import java.util.Date;
import java.util.Optional;

public class AuthServicio {
    private static final String SECRET_KEY = "CykY#fE3*x&SedZBZiYDV29MHNr$2CT#yXZYM7QT!";

    /**
     * Genera un Token JWT para la autenticación de los usuarios
     * @param legajo legajo del usuario
     * @param permiso Nivel de permiso del usuario
     * @return String token
     */
    public static String generarToken(int legajo, int permiso) {

        return JWT.create()
                .withClaim("permiso", permiso)
                .withClaim("legajo", legajo)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Valida un Token generado por la API
     */
    public static Optional<DecodedJWT> validarToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return Optional.of(decodedJWT);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Hashea un String con algoritmo BCrypt.
     * @param password String para hashear.
     * @return password hasheada.
     */
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * @param password String plano.
     * @param hashed String previamente hasheado.
     * @return true si las contraseña coinciden, sino false.
     */
    public static Boolean checkPassword(String password, String hashed) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashed).verified;
    }

    /**
     * Autentifica al usuario y retorna una instancia del mismo si los datos son correctos.
     * @param legajo n° de legajo del usuario.
     * @param password contraseña del usuario.
     * @return token JWT o null si los datos no son correctos.
     */
    public static Optional<String> login(LoginRequest loginRequest) {
        Optional<Usuario> usuario = new UsuarioDAOImpl().obtenerUsuarioPorLegajo(loginRequest.getLegajo());

        if (usuario.isPresent()) {
            if (AuthServicio.checkPassword(loginRequest.getPassword(), usuario.get().getPassword())) {
                String token = generarToken(usuario.get().getId(), usuario.get().getPermisoId());

                return Optional.of(token);
            }
        }

        return Optional.empty();
    }

    // TODO: resetPassword - logout
}
