package com.tuempresa.proyecto.application.usecase.product;

import com.tuempresa.proyecto.application.dto.request.CreateProductRequest;
import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.application.mapper.ProductMapper;
import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.repository.ProductRepository;
import com.tuempresa.proyecto.domain.valueobject.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

@Service
public class CreateProductUseCase {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CreateProductUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponse execute(CreateProductRequest request) {
        // Crear value object Money
        Money price = Money.of(request.getPrice(), Currency.getInstance(request.getCurrency()));

        // Crear producto usando el dominio
        Product product = Product.create(
            request.getName(),
            request.getDescription(),
            price,
            request.getStock(),
            request.getCategory()
        );

        // Guardar usando el repositorio
        Product savedProduct = productRepository.save(product);

        // Convertir a DTO de respuesta
        return productMapper.toResponse(savedProduct);
    }
}
