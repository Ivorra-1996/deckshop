package deckshop.spring.domain.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(unique = true, nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String pass;
    @Column(nullable = false)
    private String direccion;
    @Column(unique = true, nullable = false)
    private String mail;
    @Column(unique = true, nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String fechaDeNacimiento;
    @Column(nullable = false)
    private String rol;
}