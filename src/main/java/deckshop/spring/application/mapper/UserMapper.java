package deckshop.spring.application.mapper;

import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.domain.user.model.User;

// Mapper para convertir entre UserDTO <-> User (modelo de dominio)
public class UserMapper {

    // Convierte un DTO recibido desde la API a un objeto del dominio
    public static User toDomain(UserDTO user) {
        User domainUser = new User();
        domainUser.setId(user.getId());
        domainUser.setNombre(user.getNombre());
        domainUser.setApellido(user.getApellido());
        domainUser.setDni(user.getDni());
        domainUser.setPass(user.getPass());
        domainUser.setDireccion(user.getDireccion());
        domainUser.setMail(user.getMail());
        domainUser.setTelefono(user.getTelefono());
        domainUser.setFechaDeNacimiento(user.getFechaDeNacimiento());
        domainUser.setEdad(user.getEdad());
        domainUser.setRol(user.getRol());
        domainUser.setEstado(user.getEstado());
        return domainUser;
    }

    // Convierte un objeto del dominio a un DTO para devolver al cliente
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        try {
            dto.setId(user.getId());
            dto.setNombre(user.getNombre());
            dto.setApellido(user.getApellido());
            dto.setDni(user.getDni());
            dto.setMail(user.getMail());
            dto.setFechaDeNacimiento(user.getFechaDeNacimiento());
            dto.setEdad(user.getEdad());
            dto.setRol(user.getRol());
            dto.setEstado(user.getEstado());
            return dto;
        } catch (NullPointerException e) {
            dto.setId(0L);
            return dto;
        }
    }
}
