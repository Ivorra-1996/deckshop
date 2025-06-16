package deckshop.spring.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
    // note: 'cliente, vendedor, admin, etc.'
    private String rol;
}
