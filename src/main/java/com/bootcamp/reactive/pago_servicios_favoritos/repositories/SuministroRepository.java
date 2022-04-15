package com.bootcamp.reactive.pago_servicios_favoritos.repositories;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Suministro;
import reactor.core.publisher.Mono;

public interface SuministroRepository {
    Mono<Suministro> findByNumeroAndCodigoServicio(String numero, String codServicio, String token);
}
