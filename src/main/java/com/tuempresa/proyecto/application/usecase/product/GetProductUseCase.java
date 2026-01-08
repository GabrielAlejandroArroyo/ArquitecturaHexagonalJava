package com.tuempresa.proyecto.application.usecase.product;

import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.application.mapper.ProductMapper;
import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetProductUseCase {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetProductUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public ProductResponse execute(UUID id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        
        return productMapper.toResponse(product);
    }
}
