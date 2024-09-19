package com.lmfm.api.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lmfm.api.dao.mysql.UsuarioDAOImpl;
import com.lmfm.api.dto.LoginRequest;
import com.lmfm.api.model.Usuario;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class AuthServicio {
    private static final String SECRET_KEY_ACCESS = "CykY#fE3*x&SedZBZiYDV29MHNr$2CT#yXZYM7QT!";
    private static final String SECRET_KEY_REFRESH = "GXASQKyqtjo^BxgG3x5MybhVcQKa&$WFoH!tm3An&a%!iD*@76$jY^";
    private static final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1 hora
    private static final long REFRESH_TOKEN_EXPIRATION = 86400000L * 30; // 30 días

    /**
     * Genera un Token JWT para la autenticación de los usuarios
     * @param legajo legajo del usuario
     * @param permiso Nivel de permiso del usuario
     * @return String token
     */
    public static String generarAccessToken(int legajo, int permiso) {

        return JWT.create()
                .withClaim("permiso", permiso)
                .withClaim("legajo", legajo)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY_ACCESS));
    }

    public static String generarRefreshToken(int legajo) {
        return JWT.create()
                .withClaim("legajo", legajo)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY_REFRESH));
    }

    /**
     * Valida un Token generado por la API
     */
    public static Optional<DecodedJWT> validarAccessToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY_ACCESS))
                    .build()
                    .verify(token);
            return Optional.of(decodedJWT);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<DecodedJWT> validarRefreshToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY_REFRESH))
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
     * @param loginRequest instancia de LoginRequest con legajo y password
     * @return token JWT o null si los datos no son correctos.
     */
    public static Map<String, String> login(LoginRequest loginRequest) {
        Map<String, String> tokens = new HashMap<>();
        Optional<Usuario> usuario = UsuarioServicio.getUsuarioPorLegajo(loginRequest.getLegajo());

        if (usuario.isPresent()) {
            if (AuthServicio.checkPassword(loginRequest.getPassword(), usuario.get().getPassword())) {
                String accessToken = generarAccessToken(usuario.get().getId(), usuario.get().getPermiso().getId());
                // String refreshToken = generarRefreshToken(usuario.get().getLegajo());
                tokens.put("accessToken", accessToken);
                // tokens.put("refreshToken", refreshToken);

                return tokens;
            }
        }

        return null;
    }

    public static boolean validarPassword(String password) {
        // Expresión regular para la contraseña:
        // (?=.*[a-z]): al menos una minúscula
        // (?=.*[A-Z]): al menos una mayúscula
        // (?=.*\d): al menos un dígito
        // (?=.*[@$!%*?&]): al menos un carácter especial (puedes agregar más si es necesario)
        // .{8,}: al menos 8 caracteres de longitud

        String regex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,}$";

        // Compilamos el patrón y lo probamos con la contraseña
        Pattern patron = Pattern.compile(regex);
        return patron.matcher(password).matches();
    }

    // TODO: resetPassword - logout
}
