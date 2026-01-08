package com.tuempresa.proyecto.domain.repository;

import com.tuempresa.proyecto.domain.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findActiveProducts();
    void delete(UUID id);
    boolean existsById(UUID id);
}
