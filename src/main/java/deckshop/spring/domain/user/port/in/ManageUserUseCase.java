package deckshop.spring.domain.user.port.in;

import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.domain.user.model.User;
import java.util.List;

public interface ManageUserUseCase {
    User searchbyID(Long id);
    List<User> getAll();
    void createUser(User user);
    void verifyAccount(LoginRequestDTO loginRequestDTO);
    void deleteUser(Long id);
    void updateAll(Long id, User user);
//    void specificUpdate(Long id, User user);
}
