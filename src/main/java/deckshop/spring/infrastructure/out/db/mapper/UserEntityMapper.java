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
        return new User(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getDni(),
                entity.getPass(),
                entity.getDireccion(),
                entity.getMail(),
                entity.getTelefono(),
                entity.getFechaDeNacimiento(),
                entity.getEdad(),
                entity.getRol(),
                entity.getEstado()
        );
    }
}
