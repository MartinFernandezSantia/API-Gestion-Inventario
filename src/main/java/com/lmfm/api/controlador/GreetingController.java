package com.lmfm.api.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/saludar")
    public String greet(@RequestParam(value = "name", defaultValue = "Mundo") String name) {
        return String.format("Hola, %s!", name);
    }
}
