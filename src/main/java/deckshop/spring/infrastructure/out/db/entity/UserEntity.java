package deckshop.spring.infrastructure.out.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String pass;
    private String direccion;
    private String mail;
    private String telefono;
    private String fechaDeNacimiento;
    private String edad;
    private String rol;
    private String estado;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> productos = new ArrayList<>();

}