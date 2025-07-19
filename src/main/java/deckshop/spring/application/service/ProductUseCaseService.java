package deckshop.spring.application.service;

import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.product.port.in.ManageProductUseCase;
import deckshop.spring.domain.product.port.out.ProductRepositoryPort;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductUseCaseService  implements ManageProductUseCase {

    private final UserRepositoryPort userRepository;
    private final ProductRepositoryPort repository;

    public ProductUseCaseService(UserRepositoryPort userRepository, ProductRepositoryPort repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @Override
    public Product searchbyID(Long id) {
        return repository.findProduct(id);
    }

    @Override
    public List<Product> searchByNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Product> searchByIdOrNombre(Long id, String nombre) {
        return repository.findByIdOrNombreContainingIgnoreCase(id, nombre);
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<String> createProduct(Product product) {
        System.out.println(product.getPrecio() + " " + product.getCantidad());

        if (product.getPrecio().compareTo(BigDecimal.ZERO) <= 0 || product.getCantidad() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los valores numéricos deben ser mayores a cero!");
        } else {
            product.setEstadoDePublicacion("ACTIVA");
            repository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con éxito");
        }
    }

    @Override
    public void productExistenceVerification(Long id) {
        Product product = searchbyID(id);
        if (product != null){
            repository.removeProductBD(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateState(Long id) {
        Product product = searchbyID(id);
        if (product != null){
            product.setEstadoDePublicacion("PAUSADA");
            repository.save(product);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ResponseEntity<String> updateAll(Product productbody, Long id) {
        Product product = searchbyID(id);
        if (productbody.getPrecio().compareTo(BigDecimal.ZERO) <= 0 || productbody.getCantidad() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los valores numéricos deben ser mayores a cero!");
        }

        if (product != null){
            productbody.setId(id);
            repository.save(productbody);
            return ResponseEntity.status(HttpStatus.OK).body("Updated product!");
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateAmount(Long id) {
        Product product = searchbyID(id);
        if (product != null){
            // Tendria que agregar hilos o algun lock para controlar esto?
            if (product.getCantidad() > 0 ){
                product.setCantidad(product.getCantidad() - 1);
                repository.save(product);
            } else {
                throw new IllegalArgumentException("Stock insuficiente!");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
