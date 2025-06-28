package deckshop.spring.infrastructure.in.rest;

import deckshop.spring.application.dto.UserDTO;
import deckshop.spring.application.mapper.UserMapper;
import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
// Maneja las petticioens de los usuarios que recibe y que devuelve.
public class UserController {

    private final ManageUserUseCase userUseCase;

    public UserController(ManageUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    // ⚠️ IMPORTANTE:
    // En esta capa (Controller) usamos UserDTO tanto como parámetro de entrada (@RequestBody),
    // como valor de salida (return).
    // Esto es porque el DTO representa la estructura que se expone al exterior (cliente/Frontend),
    // y nos permite:
    // - Controlar qué datos entran y salen (evitar exponer campos sensibles como 'pass', 'rol', etc.).
    // - Desacoplar la lógica de dominio del formato de datos usado en la API.
    // - Mantener la arquitectura hexagonal limpia: el dominio trabaja con 'User', no con DTOs.
    // Los mapeos entre DTO <-> Dominio se hacen usando el UserMapper.


    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return UserMapper.toDTO(userUseCase.searchbyID(id));
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userUseCase.getAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDTO postUser(@RequestBody UserDTO userDTO) {
        return UserMapper.toDTO(userUseCase.createUser(UserMapper.toDomain(userDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userUseCase.updateAll(id, UserMapper.toDomain(userDTO));
    }

//    @PatchMapping("/specific/{id}")
//    public void patchUser(@PathVariable Long id, @RequestBody User user) {
//        //userUseCase.specificUpdate(id);
//    }

}
