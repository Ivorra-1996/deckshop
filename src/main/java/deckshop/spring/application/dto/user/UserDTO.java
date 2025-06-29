package deckshop.spring.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
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
}