package deckshop.spring.infrastructure.out.db;

import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.product.port.out.ProductRepositoryPort;
import deckshop.spring.infrastructure.out.db.entity.ProductEntity;
import deckshop.spring.infrastructure.out.db.mapper.ProductEntityMapper;
import deckshop.spring.infrastructure.out.db.repository.SpringDataProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostgresProductRepository implements ProductRepositoryPort {

    private final SpringDataProductRepository repository;

    public PostgresProductRepository(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    // Lo borrO ?
    @Override
    public Product findProduct(Long id) {
        return repository.findById(id).map(ProductEntityMapper::toDomain).orElse(null);
    }

    @Override
    public List<Product> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByIdOrNombreContainingIgnoreCase(Long id, String nombre) {
        return repository.findByIdOrNombreContainingIgnoreCase(id, nombre)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        ProductEntity entity = ProductEntityMapper.toEntity(product);
        repository.save(entity);
    }

    @Override
    public void removeProductBD(Long id) {
        Optional<ProductEntity> existingProductOpt = repository.findById(id);

        if (existingProductOpt.isPresent()) {
            ProductEntity entity = existingProductOpt.get();

            // Anonimizamos los campos
            entity.setEstadoDePublicacion("ELIMINADO");

            repository.save(entity);
        }
    }
}
