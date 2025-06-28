package deckshop.spring.application.service;

import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCaseService implements ManageUserUseCase {

    private final UserRepositoryPort repository;

    public UserUseCaseService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public User searchbyID(Long id) {
        return repository.findUserById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateAll(Long id, User user) {
        repository.updateAll(id, user);
    }

//    @Override
//    public void specificUpdate(Long id, User user) {
//        //repository.specificUpdate(id);
//    }
}
