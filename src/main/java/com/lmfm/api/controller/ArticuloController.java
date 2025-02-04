package com.lmfm.api.controller;

import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.model.Categoria;
import com.lmfm.api.service.ArticuloServicio;
import com.lmfm.api.service.CategoriaServicio;
import com.lmfm.api.translators.ArticuloTranslator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("articulos")
public class ArticuloController {

    @PostMapping
    public ResponseEntity<?> crearArticulo(@RequestBody @Valid ArticuloRequest articuloRequest) {

        CategoriaServicio categoriaServicio = new CategoriaServicio();

        Categoria categoria = categoriaServicio.buscarCategoriaPorId(articuloRequest.getCategoriaId());

        if(categoria == null) {
            return ResponseEntity.badRequest().body("Datos incorrectos");
        }

        Articulo nuevoArticulo = ArticuloTranslator.fromDTO(articuloRequest, categoria);

        // Continuar (Revisar incompatibilidades metodos CategoriaServicio
    }
}
