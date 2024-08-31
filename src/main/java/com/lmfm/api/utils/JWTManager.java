package com.lmfm.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.ZonedDateTime;
import java.util.Date;

public class JWTManager {
    private static final String SECRET_KEY = "CykY#fE3*x&SedZBZiYDV29MHNr$2CT#yXZYM7QT!";

    /**
     * Genera un Token JWT para la autenticación de los usuarios
     * @param id Id del usuario
     * @param permiso Nivel de permiso del usuario
     * @return String token
     */
    public static String generarToken(int id, int permiso) {
        String token = JWT.create()
                .withSubject(String.valueOf(id))
                .withClaim("permiso", String.valueOf(permiso))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMonths(1).toInstant()))
                .sign(Algorithm.HMAC256(SECRET_KEY));

        return token;
    }

    /**
     * Valida un Token generado por la API
     * @param token String token
     * @return DecodedJWT o null si es invalido
     */
    public static DecodedJWT validarToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
        }
        catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        String token = generarToken(1, 1);
        System.out.println(token);
        System.out.println(validarToken(token));

    }
}
