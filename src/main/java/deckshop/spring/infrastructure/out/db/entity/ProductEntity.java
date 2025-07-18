package deckshop.spring.infrastructure.out.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Relacion
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserEntity usuario;

    private BigDecimal precio;
    private String nombre;
    private String modelo;
    private String descripcion;
    @Column(name = "estado_uso")
    private String estadoUso;
    private Integer cantidad;
    private String estadoDePublicacion;

}
