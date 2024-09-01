package com.lmfm.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmfm.api.dto.LoginRequest;
import com.lmfm.api.service.AuthServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> token = AuthServicio.login(loginRequest);

        if (token.isPresent()) {
            JsonNode respuesta = new ObjectMapper().createObjectNode().put("token", token.get());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
        else {
            JsonNode respuesta = new ObjectMapper().createObjectNode().put("error", "Legajo y/o contraseña incorrecto/s");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);

        }

    }
}
