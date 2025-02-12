package com.lmfm.api.controller;

import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.*;
import com.lmfm.api.service.ArticuloServicio;
import com.lmfm.api.service.BalancesServicio;
import com.lmfm.api.translators.BalancesTranslator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("balances")
public class BalancesController {

    @PostMapping
    public ResponseEntity<?> crearBalance(@RequestBody @Valid BalancesRequest balancesRequest) {
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorId(balancesRequest.getArticuloId());

        if (articulo.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Balances balances = BalancesTranslator.fromDTO(balancesRequest, articulo.get());

        if(!BalancesServicio.crearBalance(balances)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(balances.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getBalances() {
        List<Balances> balances = BalancesServicio.obtenerTodosLosBalances();

        if(balances.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBalancePorId(@PathVariable int id) {
        Optional<Balances> balances = BalancesServicio.getBalancePorId(id);

        if(balances.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balances.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBalancePorId(@PathVariable int id) {
        if(!BalancesServicio.eliminarBalance(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<?> actualizarBalance(@RequestBody @Valid BalancesRequest balancesRequest) {
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorId(balancesRequest.getArticuloId());

        if (articulo.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Balances balances = BalancesTranslator.fromDTO(balancesRequest, articulo.get());

        if(!BalancesServicio.actualizarBalance(balances)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }
}
