# Ejemplo CRUD Completo - Product

Este documento describe el ejemplo CRUD completo implementado siguiendo Clean Architecture.

## Estructura del Ejemplo

### Domain Layer
- **Product.java**: Entidad de dominio con lógica de negocio
- **Money.java**: Value Object para manejar dinero
- **ProductRepository.java**: Interfaz del repositorio

### Application Layer
- **CreateProductRequest.java**: DTO para crear producto
- **UpdateProductRequest.java**: DTO para actualizar producto
- **ProductResponse.java**: DTO de respuesta
- **ProductMapper.java**: Mapper MapStruct
- **CreateProductUseCase.java**: Caso de uso para crear
- **GetProductUseCase.java**: Caso de uso para obtener uno
- **GetAllProductsUseCase.java**: Caso de uso para obtener todos
- **UpdateProductUseCase.java**: Caso de uso para actualizar
- **DeleteProductUseCase.java**: Caso de uso para eliminar

### Infrastructure Layer
- **ProductEntity.java**: Entidad JPA
- **JpaProductRepository.java**: Repositorio Spring Data JPA
- **ProductEntityMapper.java**: Mapper entre entidad JPA y dominio
- **ProductRepositoryAdapter.java**: Adaptador que implementa la interfaz del dominio

### Presentation Layer
- **ProductController.java**: Controller REST con todos los endpoints CRUD
- **GlobalExceptionHandler.java**: Manejo global de excepciones

## Endpoints REST

### 1. Crear Producto (CREATE)
```http
POST /api/products
Content-Type: application/json

{
  "name": "Laptop Dell",
  "description": "Laptop Dell Inspiron 15",
  "price": 899.99,
  "currency": "USD",
  "stock": 10,
  "category": "Electronics"
}
```

**Respuesta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Laptop Dell",
  "description": "Laptop Dell Inspiron 15",
  "price": 899.99,
  "currency": "USD",
  "stock": 10,
  "category": "Electronics",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "active": true
}
```

### 2. Obtener Producto por ID (READ)
```http
GET /api/products/{id}
```

**Respuesta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Laptop Dell",
  "description": "Laptop Dell Inspiron 15",
  "price": 899.99,
  "currency": "USD",
  "stock": 10,
  "category": "Electronics",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "active": true
}
```

### 3. Obtener Todos los Productos (READ ALL)
```http
GET /api/products
```

**Respuesta:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Laptop Dell",
    "description": "Laptop Dell Inspiron 15",
    "price": 899.99,
    "currency": "USD",
    "stock": 10,
    "category": "Electronics",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "active": true
  },
  {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "name": "Mouse Logitech",
    "description": "Mouse inalámbrico Logitech",
    "price": 29.99,
    "currency": "USD",
    "stock": 50,
    "category": "Accessories",
    "createdAt": "2024-01-15T11:00:00",
    "updatedAt": "2024-01-15T11:00:00",
    "active": true
  }
]
```

### 4. Actualizar Producto (UPDATE)
```http
PUT /api/products/{id}
Content-Type: application/json

{
  "name": "Laptop Dell Updated",
  "description": "Laptop Dell Inspiron 15 - Updated",
  "price": 799.99,
  "currency": "USD",
  "stock": 15,
  "category": "Electronics"
}
```

**Respuesta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Laptop Dell Updated",
  "description": "Laptop Dell Inspiron 15 - Updated",
  "price": 799.99,
  "currency": "USD",
  "stock": 15,
  "category": "Electronics",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T12:00:00",
  "active": true
}
```

### 5. Eliminar Producto (DELETE)
```http
DELETE /api/products/{id}
```

**Respuesta:** 204 No Content

## Flujo de Ejecución

### Crear Producto
```
1. Cliente → POST /api/products
2. ProductController.createProduct()
3. CreateProductUseCase.execute()
4. Product.create() [Domain]
5. ProductRepository.save() [Interface]
6. ProductRepositoryAdapter.save() [Infrastructure]
7. JpaProductRepository.save() [JPA]
8. Base de Datos
9. Respuesta → Cliente
```

### Obtener Producto
```
1. Cliente → GET /api/products/{id}
2. ProductController.getProduct()
3. GetProductUseCase.execute()
4. ProductRepository.findById() [Interface]
5. ProductRepositoryAdapter.findById() [Infrastructure]
6. JpaProductRepository.findById() [JPA]
7. Base de Datos
8. ProductMapper.toResponse() [Application]
9. Respuesta → Cliente
```

## Validaciones

### Request Validation
- **name**: Requerido, entre 2 y 100 caracteres
- **description**: Opcional, máximo 500 caracteres
- **price**: Requerido, mayor que 0.01, formato decimal válido
- **currency**: Requerido, código de 3 letras
- **stock**: Requerido, no negativo
- **category**: Requerido, entre 2 y 50 caracteres

### Domain Validation
- El precio debe ser no negativo (validado en Money)
- No se puede actualizar un producto inactivo
- No se puede eliminar stock si no hay suficiente

## Manejo de Excepciones

### Errores de Validación (400)
```json
{
  "message": "Validation failed",
  "details": "{name=Name is required, price=Price is required}",
  "status": 400,
  "timestamp": "2024-01-15T10:30:00"
}
```

### Producto No Encontrado (400)
```json
{
  "message": "Product not found with id: 550e8400-e29b-41d4-a716-446655440000",
  "details": null,
  "status": 400,
  "timestamp": "2024-01-15T10:30:00"
}
```

### Error de Estado (409)
```json
{
  "message": "Cannot update inactive product",
  "details": null,
  "status": 409,
  "timestamp": "2024-01-15T10:30:00"
}
```

## Características Implementadas

✅ **CRUD Completo**: Create, Read, Update, Delete
✅ **Validación de Entrada**: Bean Validation en DTOs
✅ **Validación de Dominio**: Lógica de negocio en entidades
✅ **Value Objects**: Money para manejar dinero
✅ **Mappers**: MapStruct para conversión entre capas
✅ **Manejo de Excepciones**: Global exception handler
✅ **Separación de Capas**: Clean Architecture respetada
✅ **Testeable**: Cada capa puede testearse independientemente

## Próximos Pasos

1. Agregar tests unitarios para cada capa
2. Agregar tests de integración para los endpoints
3. Implementar paginación en GetAllProducts
4. Agregar filtros (por categoría, activos, etc.)
5. Implementar soft delete en lugar de delete físico
6. Agregar auditoría de cambios
7. Implementar búsqueda por nombre/descripción
