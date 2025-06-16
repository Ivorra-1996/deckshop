package deckshop.spring.domain.user.port.in;

import deckshop.spring.domain.user.model.User;
import java.util.List;

public interface ManageUserUseCase {
    User searchbyID(Long id);
    List<User> getAll();
    User createUser(User user);
    void deleteUser(Long id);
    void updateAll(Long id, User user);
    void specificUpdate(Long id);
}
