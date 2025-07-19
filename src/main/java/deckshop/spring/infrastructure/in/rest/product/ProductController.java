package deckshop.spring.infrastructure.in.rest.product;

import deckshop.spring.application.dto.product.ProductCreateDTO;
import deckshop.spring.application.dto.product.ProductDTO;
import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.application.mapper.ProductMapper;
import deckshop.spring.application.mapper.UserMapper;
import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.product.port.in.ManageProductUseCase;
import deckshop.spring.domain.user.model.User;
import deckshop.spring.domain.user.port.in.ManageUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private  final ManageUserUseCase user;
    private final ManageProductUseCase productUseCase;

    public ProductController(ManageUserUseCase user, ManageProductUseCase productUseCase) {
        this.user = user;
        this.productUseCase = productUseCase;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> buscar(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre) {

        List<Product> productos;

        if (id != null && nombre != null) {
            productos = productUseCase.searchByIdOrNombre(id, nombre);
        } else if (nombre != null) {
            productos = productUseCase.searchByNombre(nombre);
        } else if (id != null) {
            Product p = productUseCase.searchbyID(id);
            productos = p != null ? List.of(p) : List.of();
        } else {
            return ResponseEntity.badRequest().build();
        }

        List<ProductDTO> result = productos.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @GetMapping
    public List<ProductDTO> getAll() {
        return productUseCase.getAll()
                .stream()
                .map(ProductMapper::toDTO)  // ← función de mapeo válida
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<String> register(@RequestBody ProductCreateDTO productDTO) {
        try {
            Optional<User> userOpt = Optional.ofNullable(user.searchbyID(productDTO.getIdUsuario()));

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Product product = ProductMapper.toDomain(productDTO, userOpt.get());
            return productUseCase.createProduct(product);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        try {
            productUseCase.productExistenceVerification(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto!");
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable Long id) {
        try {
            productUseCase.updateState(id);
            return ResponseEntity.status(HttpStatus.OK).body("Updated product!");
        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();

            if ("No existe el Producto!".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }

            // Otros errores por defecto
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> putProduct(@RequestBody ProductCreateDTO productDTO, @PathVariable Long id) {
        try {
            Optional<User> userOpt = Optional.ofNullable(user.searchbyID(productDTO.getIdUsuario()));

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            return productUseCase.updateAll(ProductMapper.toDomain(productDTO, userOpt.get()), id);

        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();

            if ("No existe el producto!".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }

            // Otros errores por defecto
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

    @PatchMapping("/reserve/{id}")
    public ResponseEntity<?> patchProductreserve(@PathVariable Long id) {
        try {
            productUseCase.updateAmount(id);
            return ResponseEntity.status(HttpStatus.OK).body("Reserve product!");
        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();

            if ("No existe el Producto!".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
            if ("Stock insuficiente!".equals(mensaje)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(mensaje);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

}
