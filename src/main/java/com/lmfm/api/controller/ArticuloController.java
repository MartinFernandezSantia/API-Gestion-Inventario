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
@RequestMapping("articulos")
public class ArticuloController {

    @PostMapping
    public ResponseEntity<?> crearArticulo(@RequestBody @Valid ArticuloRequest articuloRequest) {
        Categoria categoria = Optional.ofNullable(articuloRequest.getCategoriaId())
                .flatMap(CategoriaServicio::buscarCategoriaPorId)
                .orElse(null);

        if(articuloRequest.getCategoriaId() != null && categoria == null) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        Articulo nuevoArticulo = ArticuloTranslator.fromDTO(articuloRequest, categoria);

        if(!ArticuloServicio.crearArticulo(nuevoArticulo)) {
            return ResponseEntity.badRequest().body("Datos incorrectos.");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoArticulo.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> getArticulos() {
        List<Articulo> articulos = ArticuloServicio.getArticulos();

        if(articulos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticuloPorId(@PathVariable int id) {
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorId(id);

        if(articulo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articulo.get());
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> getCodigo(@PathVariable int codigo){
        Optional<Articulo> articulo = ArticuloServicio.getArticuloPorCodigo(codigo);

        if(articulo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articulo.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloPorId(@PathVariable int id) {
        if(!ArticuloServicio.eliminarArticulo(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<?> actualizarArticulo(@RequestBody ArticuloRequest articuloRequest) {
        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
        Optional<Categoria> categoria = categoriaDAO.obtenerCategoriaPorId(articuloRequest.getCategoriaId());

        if(categoria.isEmpty()) {
            return ResponseEntity.badRequest().body("Datos incorrectos");
        }

        Articulo articulo = ArticuloTranslator.fromDTO(articuloRequest, categoria.get());

        if(!ArticuloServicio.actualizarArticulo(articulo)){
            return ResponseEntity.badRequest().build();
        }

        return  ResponseEntity.ok().build();
    }

}
