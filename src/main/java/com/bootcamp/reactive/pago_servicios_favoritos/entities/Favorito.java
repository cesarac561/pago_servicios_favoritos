package com.bootcamp.reactive.pago_servicios_favoritos.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("favorito")
public class Favorito {
    @Id
    @Column("Id")
    private Integer id;
    @Column("Nombre")
    private String nombre;
    @Column("TipoFavorito")
    private String tipoFavorito;
    @Column("SuministroId")
    private String suministroId;

}
