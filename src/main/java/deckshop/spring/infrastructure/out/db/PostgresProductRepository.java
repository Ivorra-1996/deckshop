package deckshop.spring.infrastructure.out.db;

import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.product.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresProductRepository implements ProductRepositoryPort {

    @Override
    public Product findProduct(long id) {
        return null; // acá luego implementás el acceso real a la base
    }
}
