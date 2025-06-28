package deckshop.spring.application.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
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
    public void createUser(User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPass());
        user.setPass(hash);
        repository.save(user);
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
