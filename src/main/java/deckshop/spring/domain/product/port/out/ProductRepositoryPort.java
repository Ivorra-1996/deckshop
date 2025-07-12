package deckshop.spring.domain.product.port.out;

import deckshop.spring.domain.product.model.Product;

public interface ProductRepositoryPort {
    Product findProduct(long id);
}
