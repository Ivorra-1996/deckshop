package deckshop.spring.infrastructure.out.db;

import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import deckshop.spring.infrastructure.out.db.entity.UserEntity;
import deckshop.spring.infrastructure.out.db.mapper.UserEntityMapper;
import deckshop.spring.infrastructure.out.db.repository.SpringDataUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
public class PostgresUserRepository implements UserRepositoryPort {

    private final SpringDataUserRepository repository;

    public PostgresUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id)
                .map(UserEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(UserEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserEntityMapper.toEntity(user);
        repository.save(entity);
    }

    @Override
    public User findByAccount(String cuenta) {
        // El Optional se usa cuando puede existir el usuario o no, en este caso null.
        Optional<UserEntity> entity = Optional.empty();

        if (cuenta.contains("@")) {
            entity = repository.findByMail(cuenta);
        } else if (cuenta.matches("\\d+")) {
            entity = repository.findByTelefono(cuenta);
        }

        return entity.map(UserEntityMapper::toDomain).orElse(null);
    }

    @Override
    public void removeUserBD(Long id) {
        Optional<UserEntity> existingUserOpt = repository.findById(id);

        if (existingUserOpt.isPresent()) {
            UserEntity entity = existingUserOpt.get();

            // Anonimizamos los campos
            entity.setEstado("ELIMINADO");
            entity.setPass("");
            entity.setMail("");

            repository.save(entity);
        }
    }

    @Override
    public void updateAll(Long id, User user) {

    }

}
