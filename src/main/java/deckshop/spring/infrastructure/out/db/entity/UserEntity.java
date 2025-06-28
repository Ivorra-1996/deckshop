package deckshop.spring.infrastructure.out.db.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String usuario;
    private String pass;
    private String direccion;
    private String mail;
    private String telefono;
    private String fechaDeNacimiento;
    private String rol;
}