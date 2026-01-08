package com.tuempresa.proyecto.presentation.controller;

import com.tuempresa.proyecto.application.dto.request.CreateProductRequest;
import com.tuempresa.proyecto.application.dto.request.UpdateProductRequest;
import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.application.usecase.product.CreateProductUseCase;
import com.tuempresa.proyecto.application.usecase.product.DeleteProductUseCase;
import com.tuempresa.proyecto.application.usecase.product.GetAllProductsUseCase;
import com.tuempresa.proyecto.application.usecase.product.GetProductUseCase;
import com.tuempresa.proyecto.application.usecase.product.UpdateProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "API para gestión de productos (CRUD completo)")
public class ProductController {
    
    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            GetAllProductsUseCase getAllProductsUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto en el sistema con la información proporcionada"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Datos del producto a crear", required = true)
            @Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = createProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Obtener un producto por ID",
        description = "Retorna la información de un producto específico mediante su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @Parameter(description = "ID único del producto", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        ProductResponse response = getProductUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Obtener todos los productos",
        description = "Retorna una lista con todos los productos disponibles en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = getAllProductsUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza la información de un producto existente mediante su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = ProductResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Producto no encontrado o datos inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "No se puede actualizar un producto inactivo",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID único del producto a actualizar", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del producto", required = true)
            @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = updateProductUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto del sistema mediante su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID único del producto a eliminar", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
