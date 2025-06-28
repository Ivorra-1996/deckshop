# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.0/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.5.0/reference/web/reactive.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Maven Parent overrides
```text
Debido al diseño de Maven, los elementos se heredan del POM padre al POM del proyecto.
Si bien la mayor parte de la herencia es correcta, también hereda elementos no deseados como `<license>` y `<developers>` del padre.
Para evitar esto, el POM del proyecto contiene anulaciones vacías para estos elementos.
Si cambia manualmente a un padre diferente y realmente desea la herencia, debe eliminar esas anulaciones.
```

## Estructura del Proyecto(Hexagonal):
> Este proyecto sigue la **arquitectura hexagonal** (Ports and Adapters) para mantener una separación clara entre el dominio, la lógica de aplicación y la infraestructura.  
> Los paquetes están organizados según su responsabilidad dentro del sistema, facilitando testeo, mantenimiento y escalabilidad.
## Estructura del Proyecto

```text
src/main/java/deckshop/spring/
│
├── domain/
│   └── user/
│       ├── model/
│       │   └── User.java                      # Entidad de dominio
│       └── port/
│           ├── in/
│           │   └── ManageUserUseCase.java    # Puerto de entrada (interface)
│           └── out/
│               └── UserRepositoryPort.java   # Puerto de salida (interface)
│
├── application/
│   ├── service/
│   │   └── UserUseCaseService.java           # Implementación de casos de uso
│   │
│   ├── dto/
│   │   └── UserDTO.java                      # Aquí van los DTOs internos si los usás
│   │
│   ├── mapper/
│   │   └── UserMapper.java                   # Mapeadores entre entidades y DTOs
│   │
│   └── port/
│       ├── in/
│       │   └── (vacío por ahora)             # Interfaces internas (opcional)
│       └── out/
│           └── (vacío por ahora)             # Interfaces para acceso externo (opcional)
│
├── infrastructure/
│   ├── in/
│   │   └── rest/
│   │       └── UserController.java           # Controlador REST
│   │
│   └── out/
│       └── db/
│           └── PostgresUserRepository.java   # Interfaz
│       └── user/
│           └── InMemoryUserRepository.java   # Implementación temporal de persistencia
│
├── DeckshopApplication.java                  # Inicio del back :D
```


