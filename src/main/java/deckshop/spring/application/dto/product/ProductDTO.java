package deckshop.spring.application.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private Long vendedorId;

    private BigDecimal precio;
    private String nombre;
    private String modelo;
    private String descripcion;
    private String estadoUso;
    private Integer cantidad;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
