package com.tuempresa.proyecto.application.usecase.product;

import com.tuempresa.proyecto.application.dto.request.UpdateProductRequest;
import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.application.mapper.ProductMapper;
import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.repository.ProductRepository;
import com.tuempresa.proyecto.domain.valueobject.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;
import java.util.UUID;

@Service
public class UpdateProductUseCase {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public UpdateProductUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponse execute(UUID id, UpdateProductRequest request) {
        // Buscar producto
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        // Crear value object Money
        Money price = Money.of(request.getPrice(), Currency.getInstance(request.getCurrency()));

        // Actualizar usando método de dominio
        product.update(
            request.getName(),
            request.getDescription(),
            price,
            request.getStock(),
            request.getCategory()
        );

        // Guardar cambios
        Product updatedProduct = productRepository.save(product);

        // Convertir a DTO de respuesta
        return productMapper.toResponse(updatedProduct);
    }
}
