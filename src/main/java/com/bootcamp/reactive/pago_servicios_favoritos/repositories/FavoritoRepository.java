package com.bootcamp.reactive.pago_servicios_favoritos.repositories;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Favorito;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FavoritoRepository extends ReactiveCrudRepository<Favorito, Integer> {
}
