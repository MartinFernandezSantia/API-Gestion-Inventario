package com.lmfm.api.translators;

import com.lmfm.api.dto.ArticuloRequest;
import com.lmfm.api.model.Articulo;
import com.lmfm.api.model.Categoria;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class ArticuloTranslator {

    public static Articulo fromDTO(ArticuloRequest articuloRequest, Categoria categoria) {
        Articulo articulo = new Articulo();

        articulo.setId(articuloRequest.getId());
        articulo.setCodigo(articuloRequest.getCodigo());
        articulo.setNombre(articuloRequest.getNombre());
        articulo.setStock(articuloRequest.getStock());
        articulo.setLimite(articuloRequest.getLimite());
        articulo.setCategoria(categoria);
        articulo.setFechaHora(Timestamp.valueOf(articuloRequest.getFechaHora()));

        return articulo;
    }

    public static ArticuloRequest toDTO(Articulo articulo){
        ArticuloRequest articuloRequest = new ArticuloRequest();

        articuloRequest.setId(articulo.getId());
        articuloRequest.setCodigo(articulo.getCodigo());
        articuloRequest.setNombre(articulo.getNombre());
        articuloRequest.setStock(articulo.getStock());
        articuloRequest.setLimite(articulo.getLimite());
        articuloRequest.setFechaHora(articulo.getFechaHora().toString());
        articuloRequest.setCategoriaId(articulo.getCategoria() == null ? null : articulo.getCategoria().getId());

        return articuloRequest;
    }

}
