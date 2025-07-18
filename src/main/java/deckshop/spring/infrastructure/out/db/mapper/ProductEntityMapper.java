package deckshop.spring.infrastructure.out.db.mapper;

import deckshop.spring.domain.product.model.Product;
import deckshop.spring.infrastructure.out.db.entity.ProductEntity;

public class ProductEntityMapper {

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                UserEntityMapper.toEntity(product.getUsuario()),
                product.getPrecio(),
                product.getNombre(),
                product.getModelo(),
                product.getDescripcion(),
                product.getEstadoUso(),
                product.getCantidad(),
                product.getEstadoDePublicacion()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setUsuario(UserEntityMapper.toDomain(entity.getUsuario()));
        product.setPrecio(entity.getPrecio());
        product.setNombre(entity.getNombre());
        product.setModelo(entity.getModelo());
        product.setDescripcion(entity.getDescripcion());
        product.setEstadoUso(entity.getEstadoUso());
        product.setCantidad(entity.getCantidad());
        product.setEstadoDePublicacion(entity.getEstadoDePublicacion());

        return product;
    }
}
