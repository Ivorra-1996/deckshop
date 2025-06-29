package deckshop.spring.infrastructure.in.rest;

import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.application.mapper.UserMapper;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            UserDTO dto = UserMapper.toDTO(userUseCase.searchbyID(id));
            if (dto.getId() == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
            }
            return ResponseEntity.status(HttpStatus.FOUND).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userUseCase.getAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userUseCase.createUser(UserMapper.toDomain(userDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();

            // Regex para extraer una parte del texto del error
            Pattern pattern = Pattern.compile("\\(([^)]+)\\)=\\(([^)]+)\\)");
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String campo = matcher.group(1);
                char mayuscula = campo.toUpperCase().charAt(0);
                String mensajeLimpio = mayuscula + campo.substring(1, campo.length()).toLowerCase()  + " en uso."  ;
                return ResponseEntity.status(HttpStatus.CONFLICT).body(mensajeLimpio);
            }

            // Si no se puede extraer, muestra el mensaje general
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Body incompleto.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            userUseCase.verifyAccount(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userUseCase.userExistenceVerification(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el usuario!");
        }
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
