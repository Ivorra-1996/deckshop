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
public class UserController {

    private final ManageUserUseCase userUseCase;

    public UserController(ManageUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

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
    public UserDTO postUser(@RequestBody User user) {
        return UserMapper.toDTO(userUseCase.createUser(UserMapper.toDomain(user)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable Long id, @RequestBody User user) {
        userUseCase.updateAll(id, UserMapper.toDomain(user));
    }

    @PatchMapping("/specific/{id}")
    public void patchUser(@PathVariable Long id) {
        userUseCase.specificUpdate(id);
    }

}
