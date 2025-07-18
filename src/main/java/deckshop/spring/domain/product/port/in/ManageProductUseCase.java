package deckshop.spring.domain.product.port.in;

import deckshop.spring.domain.product.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ManageProductUseCase {
    Product searchbyID(Long id);
    List<Product> getAll();
    ResponseEntity<String> createProduct(Product product);

//    void verifyAccount(LoginRequestDTO loginRequestDTO);
      void productExistenceVerification(Long id);
      void updateState(Long id);
//    void updateEmail(String email, Long id);
//    void specificUpdate(Long id, User user);
}
