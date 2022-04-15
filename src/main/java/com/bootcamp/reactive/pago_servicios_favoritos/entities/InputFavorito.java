package com.bootcamp.reactive.pago_servicios_favoritos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InputFavorito {

    private String nombre;
    private String tipoFavorito;
    private String codigoServicio;
    private String numeroSuministro;
}
