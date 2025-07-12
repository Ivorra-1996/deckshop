package deckshop.spring.application.mapper;

import deckshop.spring.application.dto.product.ProductDTO;
import deckshop.spring.application.dto.user.UserDTO;
import deckshop.spring.domain.product.model.Product;
import deckshop.spring.domain.user.model.User;

public class ProductMapper {
    // Convierte un DTO recibido desde la API a un objeto del dominio
    public static Product toDomain(ProductDTO product) {
        Product domainProduct = new Product();

        domainProduct.setId(product.getId());
        domainProduct.setNombre(product.getNombre());
        domainProduct.setVendedorId(product.getVendedorId());
        domainProduct.setPrecio(product.getPrecio());
        domainProduct.setModelo(product.getModelo());
        domainProduct.setDescripcion(product.getDescripcion());
        domainProduct.setEstadoUso(product.getEstadoUso());
        domainProduct.setCantidad(product.getCantidad());
        domainProduct.setCreatedAt(product.getCreatedAt());
        domainProduct.setUpdatedAt(product.getUpdatedAt());

        return domainProduct;
    }

    // Convierte un objeto del dominio a un DTO para devolver al cliente
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        try {
            dto.setId(product.getId());
            dto.setNombre(product.getNombre());
            dto.setVendedorId(product.getVendedorId());
            dto.setPrecio(product.getPrecio());
            dto.setModelo(product.getModelo());
            dto.setDescripcion(product.getDescripcion());
            dto.setEstadoUso(product.getEstadoUso());
            dto.setCantidad(product.getCantidad());
            dto.setCreatedAt(product.getCreatedAt());
            dto.setUpdatedAt(product.getUpdatedAt());

            return dto;
        } catch (NullPointerException e) {
            dto.setId(0L);
            return dto;
        }
    }
}
