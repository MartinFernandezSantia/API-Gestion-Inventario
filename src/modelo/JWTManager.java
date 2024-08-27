package modelo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.ZonedDateTime;
import java.util.Date;

public class JWTManager {
    private static final String SECRET_KEY = "CykY#fE3*x&SedZBZiYDV29MHNr$2CT#yXZYM7QT!";

    public static String generarToken(int id, int permiso) {
        String token = JWT.create()
                .withSubject(String.valueOf(id))
                .withClaim("permiso", String.valueOf(permiso))
                .withExpiresAt(Date.from(ZonedDateTime.now().plusMonths(1).toInstant()))
                .sign(Algorithm.HMAC256(SECRET_KEY));

        return token;
    }

    public static DecodedJWT validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
    }
}
