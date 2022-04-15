package com.bootcamp.reactive.pago_servicios_favoritos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Suministro {
    private String id;
    private String numero;
    private String clienteId;
    private String servicioId;
}
