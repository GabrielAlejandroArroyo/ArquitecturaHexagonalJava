package com.tuempresa.proyecto.application.usecase.product;

import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.application.mapper.ProductMapper;
import com.tuempresa.proyecto.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllProductsUseCase {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetAllProductsUseCase(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> execute() {
        List<com.tuempresa.proyecto.domain.model.Product> products = productRepository.findAll();
        return productMapper.toResponseList(products);
    }
}
