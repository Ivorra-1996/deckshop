package deckshop.spring.infrastructure.out.db.repository;

import deckshop.spring.infrastructure.out.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    // Podés agregar métodos como findByUsuario si querés
}
