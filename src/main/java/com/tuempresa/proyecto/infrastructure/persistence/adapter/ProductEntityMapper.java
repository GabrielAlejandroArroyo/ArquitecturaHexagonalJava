package com.tuempresa.proyecto.infrastructure.persistence.adapter;

import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.valueobject.Money;
import com.tuempresa.proyecto.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductEntityMapper {
    
    public ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice().getAmount());
        entity.setCurrency(product.getPrice().getCurrencyCode());
        entity.setStock(product.getStock());
        entity.setCategory(product.getCategory());
        entity.setCreatedAt(product.getCreatedAt());
        entity.setUpdatedAt(product.getUpdatedAt());
        entity.setActive(product.isActive());
        return entity;
    }

    public Product toDomain(ProductEntity entity) {
        Money price = Money.of(entity.getPrice(), Currency.getInstance(entity.getCurrency()));
        
        return Product.reconstruct(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            price,
            entity.getStock(),
            entity.getCategory(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.isActive()
        );
    }

    public List<Product> toDomainList(List<ProductEntity> entities) {
        return entities.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
}
