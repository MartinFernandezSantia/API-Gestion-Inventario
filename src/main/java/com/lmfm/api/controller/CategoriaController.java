package com.lmfm.api.controller;

import com.lmfm.api.dao.mysql.CategoriaDAOImpl;
import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.model.Categoria;
import com.lmfm.api.service.ArticuloServicio;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.translators.ArticuloTranslator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody @Valid Categoria categoria) {

        if(!CategoriaServicio.crearCategoria(categoria)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getCategorias() {
        List<Categoria> categorias = CategoriaServicio.getCategorias();

        if(categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaPorId(@PathVariable int id) {
        Optional<Categoria> categoria = CategoriaServicio.getCategoriaPorId(id);

        if(categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoriaPorId(@PathVariable int id) {
        if(!CategoriaServicio.eliminarCategoria(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<?> actualizarCategoria(@RequestBody @Valid Categoria categoria) {
        if(!CategoriaServicio.actualizarCategoria(categoria)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }
}
