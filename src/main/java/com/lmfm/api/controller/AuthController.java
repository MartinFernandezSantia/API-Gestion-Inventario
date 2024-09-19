package com.lmfm.api.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmfm.api.dto.LoginRequest;
import com.lmfm.api.model.Usuario;
import com.lmfm.api.service.AuthServicio;
import com.lmfm.api.service.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> tokens = AuthServicio.login(loginRequest);

        if (tokens != null) {
            //JsonNode respuesta = new ObjectMapper().createObjectNode().put("tokens", token.get());
            return ResponseEntity.status(HttpStatus.OK).body(tokens);
        }
        else {
            JsonNode respuesta = new ObjectMapper().createObjectNode().put("error", "Legajo y/o contraseña incorrecto/s");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }

    // @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String token = request.get("refreshToken");
        Optional<DecodedJWT> decodedJWT = AuthServicio.validarRefreshToken(token);

        // Si el JWT no es valido
        if (decodedJWT.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int legajo = decodedJWT.get().getClaim("legajo").asInt();
        Optional<Usuario> usuario = new UsuarioServicio().getUsuarioPorLegajo(legajo);

        // Si el usuario al que referencia el legajo ya no existe
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int permisoId = usuario.get().getPermiso().getId();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", AuthServicio.generarAccessToken(legajo, permisoId));

        return ResponseEntity.ok(tokens);
    }
}
