package deckshop.spring.application.mapper;

import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.domain.user.model.User;

// Mapper para convertir entre UserDTO <-> User (modelo de dominio)
public class UserMapper {

    // Convierte un DTO recibido desde la API a un objeto del dominio
    public static User toDomain(UserDTO user) {
        return new User(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getDni(),
                user.getPass(),
                user.getDireccion(),
                user.getMail(),
                user.getTelefono(),
                user.getFechaDeNacimiento(),
                user.getEdad(),
                user.getRol()
        );
    }

    // Convierte un objeto del dominio a un DTO para devolver al cliente
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        try {
            dto.setId(user.getId());
            dto.setNombre(user.getNombre());
            dto.setApellido(user.getApellido());
            dto.setDni(user.getDni());
            dto.setDireccion(user.getDireccion());
            dto.setMail(user.getMail());
            dto.setTelefono(user.getTelefono());
            dto.setFechaDeNacimiento(user.getFechaDeNacimiento());
            dto.setEdad(user.getEdad());
            dto.setRol(user.getRol());
            return dto;
        } catch (NullPointerException e) {
            dto.setId(0L);
            return dto;
        }
    }
}
