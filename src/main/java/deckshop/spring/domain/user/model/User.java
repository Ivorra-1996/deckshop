package deckshop.spring.domain.user.model;

import deckshop.spring.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(unique = true, nullable = false)
    private String dni;
    @Column(nullable = false)
    private String pass;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String mail;
    @Column(unique = true, nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String fechaDeNacimiento;
    @Column(nullable = false)
    private String edad;
    @Column(nullable = false)
    private String rol;
    @Column(nullable = false)
    private String estado;

}