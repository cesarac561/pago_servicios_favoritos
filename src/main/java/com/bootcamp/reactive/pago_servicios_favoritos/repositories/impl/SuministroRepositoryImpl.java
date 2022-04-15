package com.bootcamp.reactive.pago_servicios_favoritos.repositories.impl;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Suministro;
import com.bootcamp.reactive.pago_servicios_favoritos.exceptions.FavoritoBaseException;
import com.bootcamp.reactive.pago_servicios_favoritos.repositories.SuministroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Repository
public class SuministroRepositoryImpl implements SuministroRepository {

    private final WebClient client;

    public SuministroRepositoryImpl(WebClient.Builder builder,
                                    @Value( "${application.urlApiSuministros:http://localhost/suministros}" ) String urlApiSuministros){
        log.info("urlApiSuministros = " + urlApiSuministros);

//        this.client = builder.baseUrl(urlApiSuministros)
//        .build();

        // Configurar Response timeout
        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));
        this.client = builder.baseUrl(urlApiSuministros)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }

    public Mono<Suministro> findByNumeroAndCodigoServicio(String numero, String codServicio, String token) {
//        return this.client.get().uri(uriBuilder -> uriBuilder
//                        .path("/query/")
//                        .queryParam("numero","{numero}")
//                        .queryParam("codServicio","{codServicio}")
//                        .build(numero, codServicio))
//                .accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", token)
//                .retrieve()
//                .bodyToMono(Suministro.class);

        return this.client.get().uri(uriBuilder -> uriBuilder
                        .path("/query/")
                        .queryParam("numero","{numero}")
                        .queryParam("codServicio","{codServicio}")
                        .build(numero, codServicio))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response-> Mono.error(new FavoritoBaseException(HttpStatus.REQUEST_TIMEOUT,"Server error")))
                .bodyToMono(Suministro.class)
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(2))
                                .doBeforeRetry(x->  log.info("Suministro: LOG BEFORE RETRY=" + x))
                                .doAfterRetry(x->  log.info("Suministro: LOG AFTER RETRY=" + x))
                )
                .doOnError(x-> log.info("Suministro: LOG ERROR"))
                .doOnSuccess(x -> log.info("Suministro: LOG SUCCESS"));

    }
}
