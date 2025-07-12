package deckshop.spring.infrastructure.in.rest.product;

import deckshop.spring.application.dto.product.ProductDTO;
import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.application.mapper.ProductMapper;
import deckshop.spring.application.mapper.UserMapper;
import deckshop.spring.domain.product.port.in.ManageProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ManageProductUseCase productUseCase;

    public ProductController(ManageProductUseCase productUseCase) {
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

}
