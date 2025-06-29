package deckshop.spring.application.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserUseCaseService implements ManageUserUseCase {

    private final UserRepositoryPort repository;

    public UserUseCaseService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public User searchbyID(Long id) {
        return repository.findUserById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    private Boolean emailVerification(String email){
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private Boolean cellularVerification(String numero) {
        // qQuitamos espacios, guiones, paréntesis, etc.
        String limpio = numero.replaceAll("[^\\d]", "");

        // - Empiece con 11 o 15
        // - Tenga 10 u 11 dígitos en total (ej: 11 + 8 dígitos = 10 | 15 + 9 = 11)
        return limpio.matches("^(11|15)\\d{8,9}$");
    }

    @Override
    public void createUser(User user) {
        // Validador de email
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(3, 65536, 2, user.getPass());
        user.setPass(hash);
        repository.save(user);
    }

    @Override
    public void verifyAccount(LoginRequestDTO loginRequest) {
        User user;
        String account = loginRequest.getCuenta();

        // 1. Verifica si es un email ingresado correctamente
        if (emailVerification(account)) {
            // 2. Buscar el usuario por "cuenta" (puede ser email, teléfono)
            user = repository.findByAccount(account);
        } else if (cellularVerification(account)) {
            user = repository.findByAccount(account);
        } else {
            System.out.println("No es un celular o email");
            throw new IllegalArgumentException();
        }

        // 3. Verificar si existe
        if (user == null) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 4. Verificar la contraseña con Argon2
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        boolean passwordOk = argon2.verify(user.getPass(), loginRequest.getPass());

        if (!passwordOk) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateAll(Long id, User user) {
        repository.updateAll(id, user);
    }

//    @Override
//    public void specificUpdate(Long id, User user) {
//        //repository.specificUpdate(id);
//    }
}
