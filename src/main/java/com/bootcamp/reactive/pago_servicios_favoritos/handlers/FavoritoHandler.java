package com.bootcamp.reactive.pago_servicios_favoritos.handlers;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Favorito;
import com.bootcamp.reactive.pago_servicios_favoritos.entities.InputFavorito;
import com.bootcamp.reactive.pago_servicios_favoritos.exceptions.FavoritoBaseException;
import com.bootcamp.reactive.pago_servicios_favoritos.services.FavoritoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FavoritoHandler {
    @Autowired
    private FavoritoService favoritoService;

    public Mono<ServerResponse> findAll(ServerRequest request){

        return this.favoritoService.findAll()
                .switchIfEmpty(Mono.error(new FavoritoBaseException("No se encontró elementos")))
                .collectList()
                .flatMap(list-> ServerResponse.ok().body(Mono.just(list), Favorito.class));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {

        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return this.favoritoService.findById(id)
                .flatMap(s-> ServerResponse.ok().body(Mono.just(s), Favorito.class)) ;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Favorito.class)
                .flatMap(favorito -> this.favoritoService.save(favorito))
                .flatMap(favorito -> ServerResponse.ok().body(Mono.just(favorito), Favorito.class));
    }

    public Mono<ServerResponse> registrarFavorito(ServerRequest request) {
        var tokenHeader = request.headers().header("Authorization");

        return request.bodyToMono(InputFavorito.class)
                .flatMap(inputFavorito -> this.favoritoService.registrarFavorito(inputFavorito,tokenHeader.get(0)))
                .flatMap(favorito -> ServerResponse.ok().body(Mono.just(favorito), Favorito.class));
    }
}
