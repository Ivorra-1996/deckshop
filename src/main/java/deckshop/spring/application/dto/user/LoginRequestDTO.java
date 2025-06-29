package deckshop.spring.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {
    // Puede ser email, celular.
    private String cuenta;
    private String pass;
}
