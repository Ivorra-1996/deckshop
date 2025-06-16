package deckshop.spring.domain.user.port.out;

import deckshop.spring.domain.user.model.User;
import java.util.List;

public interface UserRepositoryPort {
    User searchbyID(Long id);
    List<User> getAll();
    User createUser(User user);
    void delete(Long id);
    void updateAll(Long id, User user);
    void specificUpdate(Long id);
}