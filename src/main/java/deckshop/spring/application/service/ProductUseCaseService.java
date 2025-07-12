package deckshop.spring.application.service;


import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.product.port.in.ManageProductUseCase;
import deckshop.spring.domain.product.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ProductUseCaseService  implements ManageProductUseCase {

    private final ProductRepositoryPort repository;

    public ProductUseCaseService(ProductRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Product searchbyID(Long id) {
        return repository.findProduct(id);
    }
}
