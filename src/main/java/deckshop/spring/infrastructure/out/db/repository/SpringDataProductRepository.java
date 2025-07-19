package deckshop.spring.infrastructure.out.db.repository;

import deckshop.spring.infrastructure.out.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataProductRepository  extends JpaRepository<ProductEntity, Long> {

    @EntityGraph(attributePaths = "usuario")
    List<ProductEntity> findAll();
    List<ProductEntity> findByNombreContainingIgnoreCase(String nombre);
    List<ProductEntity> findByIdOrNombreContainingIgnoreCase(Long id, String nombre);

}
