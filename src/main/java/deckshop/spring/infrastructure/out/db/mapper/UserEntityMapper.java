package deckshop.spring.infrastructure.out.db.mapper;

import deckshop.spring.domain.user.model.User;
import deckshop.spring.infrastructure.out.db.entity.UserEntity;

public class UserEntityMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(
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
                user.getRol(),
                user.getEstado()
        );
    }

    public static User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setNombre(entity.getNombre());
        user.setApellido(entity.getApellido());
        user.setDni(entity.getDni());
        user.setPass(entity.getPass());
        user.setDireccion(entity.getDireccion());
        user.setMail(entity.getMail());
        user.setTelefono(entity.getTelefono());
        user.setFechaDeNacimiento(entity.getFechaDeNacimiento());
        user.setEdad(entity.getEdad());
        user.setRol(entity.getRol());
        user.setEstado(entity.getEstado());
        return user;
    }
}
