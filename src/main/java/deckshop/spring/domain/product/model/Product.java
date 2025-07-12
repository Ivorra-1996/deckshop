package deckshop.spring.domain.product.model;

import deckshop.spring.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos a uno con User
    @Column(name = "id_usuario", nullable = false)
    private Long vendedorId;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    private String nombre;
    private String modelo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado_uso")
    private String estadoUso;

    private Integer cantidad;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
