package deckshop.spring.application.mapper;

import deckshop.spring.application.dto.product.ProductCreateDTO;
import deckshop.spring.application.dto.product.ProductDTO;
import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.user.model.User;

public class ProductMapper {
    // Convierte un DTO recibido desde la API a un objeto del dominio
    public static Product toDomain(ProductCreateDTO dto, User user) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setUsuario(user);
        product.setPrecio(dto.getPrecio());
        product.setNombre(dto.getNombre());
        product.setModelo(dto.getModelo());
        product.setDescripcion(dto.getDescripcion());
        product.setEstadoUso(dto.getEstadoUso());
        product.setCantidad(dto.getCantidad());
        product.setEstadoDePublicacion(dto.getEstadoDePublicacion());

        return product;
    }

    // Convierte un objeto del dominio a un DTO para devolver al cliente
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        try {
            dto.setId(product.getId());
            dto.setNombre(product.getNombre());
            dto.setUsuario(UserMapper.toDTO(product.getUsuario()));
            dto.setPrecio(product.getPrecio());
            dto.setModelo(product.getModelo());
            dto.setDescripcion(product.getDescripcion());
            dto.setEstadoUso(product.getEstadoUso());
            dto.setCantidad(product.getCantidad());
            dto.setEstadoDePublicacion(product.getEstadoDePublicacion());

            return dto;
        } catch (NullPointerException e) {
            dto.setId(0L);
            return dto;
        }
    }
}
