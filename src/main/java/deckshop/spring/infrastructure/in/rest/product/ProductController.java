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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            ProductDTO dto = ProductMapper.toDTO(productUseCase.searchbyID(id));
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
    public ResponseEntity<?> putProduct(@PathVariable Long id) {
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

}
