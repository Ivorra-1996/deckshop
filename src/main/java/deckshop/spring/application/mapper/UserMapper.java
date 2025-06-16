package deckshop.spring.application.mapper;


import deckshop.spring.application.dto.UserDTO;
import deckshop.spring.domain.user.model.User;

public class UserMapper {

    public static User toDomain(User dto) {
        return new User(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni(),
                dto.getUsuario(),
                dto.getPass(),
                dto.getDireccion(),
                dto.getMail(),
                dto.getTelefono(),
                dto.getFechaDeNacimiento(),
                dto.getRol()
        );
    }

    // Mapeas la informacion traida de la BD..
    // Y con esto devolves lo que vos queres, no hace falta mostrar la pass, direccion u otros datos que son sencibles.
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setDni(user.getDni());
        dto.setUsuario(user.getUsuario());
        dto.setDireccion(user.getDireccion());
        dto.setMail(user.getMail());
        dto.setTelefono(user.getTelefono());
        dto.setFechaDeNacimiento(user.getFechaDeNacimiento());
        dto.setRol(user.getRol());
        return dto;
    }
}
