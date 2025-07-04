package deckshop.spring.domain.user.port.out;

import deckshop.spring.domain.user.model.User;

import java.util.List;

public interface UserRepositoryPort {
    User findUserById(Long id);
    List<User> findAll();
    void save(User user);
    User findByAccount(String cuenta);
    void removeUserBD(Long id);
    void updateAll(User userBody, User userBD);
    User findEmail(String email);

//    @Modifying
//    @Query("UPDATE User u SET u.algo = :valor WHERE u.id = :id")
//    void specificUpdate(Long id, User user);

}