package com.lmfm.api.controller;

import com.lmfm.api.dto.BalancesRequest;
import com.lmfm.api.model.*;
import com.lmfm.api.service.ArticuloServicio;
import com.lmfm.api.service.BalancesServicio;
import com.lmfm.api.translators.BalancesTranslator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crear nuevo Balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Balance creado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
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

    @Operation(summary = "Obtener todos los Balances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balances encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay Balances existentes")
    })
    @GetMapping
    public ResponseEntity<?> getBalances() {
        List<Balances> balances = BalancesServicio.obtenerTodosLosBalances();

        if(balances.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balances);
    }

    @Operation(summary = "Obtener Balance por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance encontrado"),
            @ApiResponse(responseCode = "404", description = "Balance no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBalancePorId(@PathVariable int id) {
        Optional<Balances> balances = BalancesServicio.getBalancePorId(id);

        if(balances.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(balances.get());
    }

    @Operation(summary = "Eliminar Balance por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance eliminado"),
            @ApiResponse(responseCode = "404", description = "Balance no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBalancePorId(@PathVariable int id) {
        if(!BalancesServicio.eliminarBalance(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar Balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o valores UNIQUE duplicados")
    })
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
