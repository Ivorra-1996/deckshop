package deckshop.spring.infrastructure.out.db.repository;

import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.infrastructure.out.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    // Podés agregar métodos como findByUsuario si querés

    // ⚠️ Dejo este comentario para no olvidarme:

    // A primera vista, parece raro entender cómo funciona esto. ¿Cómo sabe Spring qué buscar? AAAAAA!!! NO ENTIENDO !!! ESO PENSE!
    // Vos aca pensas en como carajo busca en la base de datos con estas funciones o metodos que no tienen logica a simple vista..
    // Bueno, ¿Cómo sabe Spring que buscar o qué campo buscar?

    // Usamos Optional<UserEntity> como retorno, porque ese usuario puede existir o no. Es una forma segura de manejar valores nulos.
    // Spring lo que hace en la clase "UserEntity" busca un atributo que coincida con el nombre, es decir,
    // cuando ve "findByMail", sabe que tiene que buscar un campo llamado mail, porque? Porque se lo estás pasando por parametro ahora mismo en la función.
    // Por otro lado a la vez, busca si ese atributo "mail" está en la entidad "UserEntity". Y por ultímo
    // Si ese atributo "mail", es igual al de la base, es decir coinciden. Lo que hace Spring es lo siguiente:

    // SELECT * FROM users WHERE mail = ?;
    // Donde ? se reemplaza por el valor que vos le pasás en el parámetro.

    // Como diciendo ahhh lo que me pasas por parametro es un mail, coincide con el atributo mail de "UserEntity" y además con el de la base, leesto toma tu Query!

    Optional<UserEntity> findByMail(String mail);
    Optional<UserEntity> findByTelefono(String telefono);
}
