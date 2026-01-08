package com.tuempresa.proyecto.domain.model;

import com.tuempresa.proyecto.domain.valueobject.Money;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private String description;
    private Money price;
    private Integer stock;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    // Constructor privado
    private Product() {}

    // Factory method para crear nuevo producto
    public static Product create(String name, String description, Money price, 
                                 Integer stock, String category) {
        Product product = new Product();
        product.id = UUID.randomUUID();
        product.name = name;
        product.description = description;
        product.price = price;
        product.stock = stock;
        product.category = category;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();
        product.active = true;
        return product;
    }

    // Factory method para reconstruir desde persistencia
    public static Product reconstruct(UUID id, String name, String description, 
                                     Money price, Integer stock, String category,
                                     LocalDateTime createdAt, LocalDateTime updatedAt, 
                                     boolean active) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.description = description;
        product.price = price;
        product.stock = stock;
        product.category = category;
        product.createdAt = createdAt;
        product.updatedAt = updatedAt;
        product.active = active;
        return product;
    }

    // Métodos de dominio
    public void update(String name, String description, Money price, 
                      Integer stock, String category) {
        if (!this.active) {
            throw new IllegalStateException("Cannot update inactive product");
        }
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void addStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stock += quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public void removeStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.stock < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.stock -= quantity;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.active) {
            throw new IllegalStateException("Product is already active");
        }
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (!this.active) {
            throw new IllegalStateException("Product is already inactive");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Money getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getCategory() { return category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isActive() { return active; }
}
