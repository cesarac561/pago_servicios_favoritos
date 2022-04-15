package com.bootcamp.reactive.pago_servicios_favoritos.services;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Favorito;
import com.bootcamp.reactive.pago_servicios_favoritos.entities.InputFavorito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoritoService {
    Flux<Favorito> findAll();
    Mono<Favorito> findById(Integer id);
    Mono<Favorito> save(Favorito favorito);
    Mono<Favorito> registrarFavorito(InputFavorito inputFavorito, String token);
}
