package deckshop.spring.domain.product.port.out;

import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.user.model.User;

import java.util.List;

public interface ProductRepositoryPort {
    Product findProduct(Long id);
    List<Product> findAll();
    void save(Product product);
    void removeProductBD(Long id);
}
