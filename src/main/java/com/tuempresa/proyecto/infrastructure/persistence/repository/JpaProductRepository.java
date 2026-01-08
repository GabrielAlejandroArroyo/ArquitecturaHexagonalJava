package com.tuempresa.proyecto.infrastructure.persistence.repository;

import com.tuempresa.proyecto.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategory(String category);
    List<ProductEntity> findByActiveTrue();
    boolean existsById(UUID id);
}
