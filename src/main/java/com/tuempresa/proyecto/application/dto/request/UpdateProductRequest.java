package com.tuempresa.proyecto.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Datos para actualizar un producto existente")
public class UpdateProductRequest {
    
    @Schema(description = "Nombre del producto", example = "Laptop Dell Updated", required = true, minLength = 2, maxLength = 100)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Schema(description = "Descripción del producto", example = "Laptop Dell Inspiron 15 - Updated", maxLength = 500)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Schema(description = "Precio del producto", example = "799.99", required = true, minimum = "0.01")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price format is invalid")
    private BigDecimal price;

    @Schema(description = "Código de moneda (ISO 4217)", example = "USD", required = true, minLength = 3, maxLength = 3)
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    private String currency;

    @Schema(description = "Cantidad en stock", example = "15", required = true, minimum = "0")
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "Electronics", required = true, minLength = 2, maxLength = 50)
    @NotBlank(message = "Category is required")
    @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    private String category;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
