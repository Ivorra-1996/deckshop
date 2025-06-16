package deckshop.spring.infrastructure.out.user;

import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository

// Esto simularia que estaria conectado a la base de datos BD....
public class InMemoryUserRepository implements UserRepositoryPort {

    private final Map<Long, User> storage = new HashMap<>();
    private long idCounter = 1;

    @Override
    public User searchbyID(Long id) {
        if (storage.containsValue(storage.get(id))){
            return storage.get(id);
        }
        return (new User(0L,"","","","","","","","","","none"));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User createUser(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
        }
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public void updateAll(Long id, User user) {
        if (storage.containsValue(storage.get(id))){
            storage.put(id, user);
        } else {
            System.out.println("No se encontro el usuario a actualizar");
        }
    }

    @Override
    public void specificUpdate(Long id) {
        System.out.println("Actualizado parcialmente");
    }
}
