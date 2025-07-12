package deckshop.spring.domain.product.port.in;

import deckshop.spring.domain.product.model.Product;


public interface ManageProductUseCase {
    Product searchbyID(Long id);
//    List<User> getAll();
//    void createUser(User user);
//    void verifyAccount(LoginRequestDTO loginRequestDTO);
//    void userExistenceVerification(Long id);
//    void updateAll(User user);
//    void updateEmail(String email, Long id);
//    void specificUpdate(Long id, User user);
}
