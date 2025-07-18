package deckshop.spring.application.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateDTO {
    private Long id;
    private Long idUsuario;

    private BigDecimal precio;
    private String nombre;
    private String modelo;
    private String descripcion;
    private String estadoUso;
    private Integer cantidad;
    private String estadoDePublicacion;
}
