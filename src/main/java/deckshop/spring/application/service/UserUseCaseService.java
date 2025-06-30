package deckshop.spring.application.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import deckshop.spring.application.dto.user.LoginRequestDTO;
import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import deckshop.spring.domain.user.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
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

    private Boolean emailValidator(String email){
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private Boolean cellularValidation(String numeroCelular) {
        // qQuitamos espacios, guiones, paréntesis, etc.
        String limpio = numeroCelular.replaceAll("[^\\d]", "");

        // - Empiece con 11 o 15
        // - Tenga 10 u 11 dígitos en total (ej: 11 + 8 dígitos = 10 | 15 + 9 = 11)
        return limpio.matches("^(11|15)\\d{8,9}$");
    }

    private boolean dniValidation(String numeroDNI) {
        if (numeroDNI == null) return false;

        return numeroDNI.matches("^\\d{7,9}$"); // Solo números entre 7 y 9 dígitos
    }

    private boolean directionValidation(String direccion) {
        if (direccion == null) return false;

        return direccion.length() >= 5 &&
                direccion.matches(".*[A-Za-z].*") && // contiene letras
                direccion.matches(".*\\d.*");       // contiene números
    }

    private boolean validationDateOfBirth(String fecha) {
        try {
            LocalDate birthDate = LocalDate.parse(fecha); // formato ISO
            LocalDate hoy = LocalDate.now();

            // Que no sea futura y que sea mayor a 13 años
            return !birthDate.isAfter(hoy) &&
                    Period.between(birthDate, hoy).getYears() >= 13;

        } catch (DateTimeParseException e) {
            return false; // fecha mal escrita
        }
    }

    private boolean ageValidation(String edadStr) {
        if (edadStr == null || !edadStr.matches("\\d+")) {
            return false;
        }

        int edad = Integer.parseInt(edadStr);

        // Edad válida entre 13 y 120, por ejemplo xd
        return edad >= 13 && edad <= 120;
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
        if (emailValidator(account)) {
            // 2. Buscar el usuario por "cuenta" (puede ser email, teléfono)
            user = repository.findByAccount(account);
        } else if (cellularValidation(account)) {
            user = repository.findByAccount(account);
        } else {
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

    public void userExistenceVerification(Long id) {
        User user = searchbyID(id);
        if (user != null){
            repository.removeUserBD(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void updateAll(User userBody) {
        if (!dniValidation(userBody.getDni())) {
            throw new IllegalArgumentException("DNI inválido. Debe contener solo números y tener entre 7 y 9 dígitos.");
        }

        if (!directionValidation(userBody.getDireccion())) {
            throw new IllegalArgumentException("Minimo 5 caracteres o dirección invalidad!");
        }

        if (!validationDateOfBirth(userBody.getFechaDeNacimiento())) {
            throw new IllegalArgumentException("Fecha de nacimiento no valida!");
        }

        if (!ageValidation(userBody.getEdad())){
            throw new IllegalArgumentException("Edad no valida!");
        }

        User existingUserBD = searchbyID(userBody.getId());
        boolean existe = existingUserBD != null;

        if (existe){
            // Solo deja actualizar usuarios que no fueron eliminados
            boolean condicion = !Objects.equals(existingUserBD.getEstado(), "ELIMINADO") &
                    !Objects.equals(existingUserBD.getMail(), "") &
                    existingUserBD.getTelefono() != null;

            try {
                if (condicion){
                    repository.updateAll(userBody, existingUserBD);
                } else {
                    throw new IllegalArgumentException("No tenés permitido realizar esta acción!");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error al actualizar!");
            }
        } else {
            throw new IllegalArgumentException("No existe el usuario!");
        }
    }
}
