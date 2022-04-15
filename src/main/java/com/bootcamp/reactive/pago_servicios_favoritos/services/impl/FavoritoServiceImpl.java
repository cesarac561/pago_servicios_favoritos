package com.bootcamp.reactive.pago_servicios_favoritos.services.impl;

import com.bootcamp.reactive.pago_servicios_favoritos.entities.Favorito;
import com.bootcamp.reactive.pago_servicios_favoritos.entities.InputFavorito;
import com.bootcamp.reactive.pago_servicios_favoritos.exceptions.FavoritoBaseException;
import com.bootcamp.reactive.pago_servicios_favoritos.repositories.FavoritoRepository;
import com.bootcamp.reactive.pago_servicios_favoritos.repositories.SuministroRepository;
import com.bootcamp.reactive.pago_servicios_favoritos.services.FavoritoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FavoritoServiceImpl implements FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private SuministroRepository suministroRepository;

    @Override
    public Flux<Favorito> findAll() {
        return this.favoritoRepository.findAll();
    }

    @Override
    public Mono<Favorito> findById(Integer id) {
        return this.favoritoRepository.findById(id);
    }

    @Override
    public Mono<Favorito> save(Favorito favorito) {
        return this.favoritoRepository.save(favorito);
    }

    @Override
    public Mono<Favorito> registrarFavorito(InputFavorito inputFavorito, String token) {

        log.info("CALL registrarFavorito");

        Integer vInputFavorito = validarInputFavorito(inputFavorito);

        if(vInputFavorito == 1)
            return Mono.error(new FavoritoBaseException(HttpStatus.BAD_REQUEST,"Formato Código Servicio inválido"));

        if(vInputFavorito == 2)
            return Mono.error(new FavoritoBaseException(HttpStatus.BAD_REQUEST,"Formato Número Suministro inválido"));

        return suministroRepository
                .findByNumeroAndCodigoServicio(inputFavorito.getNumeroSuministro(),
                        inputFavorito.getCodigoServicio(), token)
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.error(new FavoritoBaseException(HttpStatus.NOT_FOUND,"Suministro no encontrado")))
                .flatMap(suministro -> {
                    Favorito favorito = new Favorito();
                    Favorito favoritoR = new Favorito();

                    favorito.setNombre(inputFavorito.getNombre());
                    favorito.setTipoFavorito(inputFavorito.getTipoFavorito());
                    favorito.setSuministroId(suministro.getId());

                    return this.favoritoRepository.save(favorito);
//                    return this.favoritoRepository.save(null);
//                    favoritoR = ((int)(Math.random()*10)>5) ? null : favorito;
//                    return this.favoritoRepository.save(favoritoR);
                });

    }

    public Integer validarInputFavorito(InputFavorito inputFavorito){
        if(inputFavorito.getCodigoServicio()==null || inputFavorito.getCodigoServicio().isEmpty() || inputFavorito.getCodigoServicio().isBlank() || inputFavorito.getCodigoServicio().length() != 2)
            return 1;
        if(inputFavorito.getNumeroSuministro()==null || inputFavorito.getNumeroSuministro().isEmpty() || inputFavorito.getNumeroSuministro().isBlank() || inputFavorito.getNumeroSuministro().length() != 7)
            return 2;
        return 0;
    }
}
