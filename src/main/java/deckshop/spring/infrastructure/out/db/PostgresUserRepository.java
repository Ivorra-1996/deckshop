package deckshop.spring.infrastructure.out.db;

import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import deckshop.spring.infrastructure.out.db.entity.UserEntity;
import deckshop.spring.infrastructure.out.db.mapper.UserEntityMapper;
import deckshop.spring.infrastructure.out.db.repository.SpringDataUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public void deleteById(Long id) {

    }

    @Override
    public void updateAll(Long id, User user) {

    }

}
