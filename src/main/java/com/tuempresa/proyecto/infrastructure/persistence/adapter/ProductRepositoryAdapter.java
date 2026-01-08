package com.tuempresa.proyecto.infrastructure.persistence.adapter;

import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.repository.ProductRepository;
import com.tuempresa.proyecto.infrastructure.persistence.entity.ProductEntity;
import com.tuempresa.proyecto.infrastructure.persistence.repository.JpaProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryAdapter implements ProductRepository {
    
    private final JpaProductRepository jpaProductRepository;
    private final ProductEntityMapper entityMapper;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository, 
                                   ProductEntityMapper entityMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = entityMapper.toEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(entity);
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaProductRepository.findById(id)
            .map(entityMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = jpaProductRepository.findAll();
        return entityMapper.toDomainList(entities);
    }

    @Override
    public List<Product> findByCategory(String category) {
        List<ProductEntity> entities = jpaProductRepository.findByCategory(category);
        return entityMapper.toDomainList(entities);
    }

    @Override
    public List<Product> findActiveProducts() {
        List<ProductEntity> entities = jpaProductRepository.findByActiveTrue();
        return entityMapper.toDomainList(entities);
    }

    @Override
    public void delete(UUID id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaProductRepository.existsById(id);
    }
}
