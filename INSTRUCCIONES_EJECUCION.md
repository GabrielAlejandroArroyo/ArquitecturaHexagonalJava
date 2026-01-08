# Instrucciones de Ejecución - Proyecto CRUD

## Requisitos Previos

### Opción A: Ejecución Local
- Java 17 o superior
- Maven 3.6 o superior
- IDE (IntelliJ IDEA, Eclipse, VS Code) recomendado

### Opción B: Ejecución con Docker (Recomendado)
- Docker Desktop instalado
- Docker Compose v2.0 o superior

> **Nota**: Para ejecutar con Docker, ve directamente a la sección "Ejecución con Docker" más abajo.

## Configuración del Proyecto

### 1. Compilar el Proyecto

```bash
mvn clean install
```

### 2. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O desde tu IDE, ejecuta la clase `ProyectoApplication.java`

### 3. Verificar que la Aplicación Está Corriendo

La aplicación estará disponible en: `http://localhost:8080`

Puedes verificar accediendo a: `http://localhost:8080/api/products`

### 4. Acceder a Swagger UI

La documentación interactiva de la API está disponible en:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs JSON**: `http://localhost:8080/api-docs`

Desde Swagger UI puedes:
- Ver todos los endpoints documentados
- Probar los endpoints directamente
- Ver ejemplos de request y response
- Descargar la especificación OpenAPI

## Consola H2 Database

Para ver la base de datos en memoria:

1. Accede a: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:testdb`
3. Usuario: `sa`
4. Contraseña: (vacío)

## Probar los Endpoints

### Opción 1: Usando cURL

#### Crear Producto
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Dell",
    "description": "Laptop Dell Inspiron 15",
    "price": 899.99,
    "currency": "USD",
    "stock": 10,
    "category": "Electronics"
  }'
```

#### Obtener Todos los Productos
```bash
curl http://localhost:8080/api/products
```

#### Obtener Producto por ID
```bash
curl http://localhost:8080/api/products/{id}
```

#### Actualizar Producto
```bash
curl -X PUT http://localhost:8080/api/products/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Dell Updated",
    "description": "Laptop Dell Inspiron 15 - Updated",
    "price": 799.99,
    "currency": "USD",
    "stock": 15,
    "category": "Electronics"
  }'
```

#### Eliminar Producto
```bash
curl -X DELETE http://localhost:8080/api/products/{id}
```

### Opción 2: Usando Postman

1. Importa la colección de Postman (si tienes una)
2. O crea manualmente las siguientes requests:

**POST** `http://localhost:8080/api/products`
```json
{
  "name": "Laptop Dell",
  "description": "Laptop Dell Inspiron 15",
  "price": 899.99,
  "currency": "USD",
  "stock": 10,
  "category": "Electronics"
}
```

**GET** `http://localhost:8080/api/products`

**GET** `http://localhost:8080/api/products/{id}`

**PUT** `http://localhost:8080/api/products/{id}`
```json
{
  "name": "Laptop Dell Updated",
  "description": "Laptop Dell Inspiron 15 - Updated",
  "price": 799.99,
  "currency": "USD",
  "stock": 15,
  "category": "Electronics"
}
```

**DELETE** `http://localhost:8080/api/products/{id}`

### Opción 3: Usando HTTPie

```bash
# Crear
http POST localhost:8080/api/products \
  name="Laptop Dell" \
  description="Laptop Dell Inspiron 15" \
  price:=899.99 \
  currency="USD" \
  stock:=10 \
  category="Electronics"

# Obtener todos
http GET localhost:8080/api/products

# Obtener uno
http GET localhost:8080/api/products/{id}

# Actualizar
http PUT localhost:8080/api/products/{id} \
  name="Laptop Dell Updated" \
  price:=799.99

# Eliminar
http DELETE localhost:8080/api/products/{id}
```

## Ejemplos de Respuestas

### Crear Producto - Respuesta Exitosa (201)
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

### Error de Validación (400)
```json
{
  "message": "Validation failed",
  "details": "{name=Name is required, price=Price must be greater than 0}",
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

## Estructura del Proyecto

```
src/main/java/com/tuempresa/proyecto/
├── domain/
│   ├── model/Product.java
│   ├── valueobject/Money.java
│   └── repository/ProductRepository.java
├── application/
│   ├── dto/
│   │   ├── request/CreateProductRequest.java
│   │   ├── request/UpdateProductRequest.java
│   │   └── response/ProductResponse.java
│   ├── mapper/ProductMapper.java
│   └── usecase/product/
│       ├── CreateProductUseCase.java
│       ├── GetProductUseCase.java
│       ├── GetAllProductsUseCase.java
│       ├── UpdateProductUseCase.java
│       └── DeleteProductUseCase.java
├── infrastructure/
│   └── persistence/
│       ├── entity/ProductEntity.java
│       ├── repository/JpaProductRepository.java
│       └── adapter/
│           ├── ProductEntityMapper.java
│           └── ProductRepositoryAdapter.java
└── presentation/
    ├── controller/ProductController.java
    └── exception/GlobalExceptionHandler.java
└── infrastructure/
    └── config/
        └── OpenApiConfig.java
```

## Troubleshooting

### Error: "Port 8080 already in use"
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Error: "MapStruct no genera los mappers"
Asegúrate de que el proyecto se compile correctamente:
```bash
mvn clean compile
```

### Error: "No se encuentra la clase ProyectoApplication"
Verifica que estés en el directorio raíz del proyecto y ejecuta:
```bash
mvn clean install
```

## Ejecución con Docker

### Requisitos
- Docker Desktop instalado y corriendo

### Comandos Rápidos

```bash
# Construir y levantar todos los servicios (PostgreSQL + App)
docker-compose up --build

# En modo background
docker-compose up -d --build

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down
```

### URLs con Docker

- **API**: http://localhost:8080/api/products
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **PostgreSQL**: localhost:5432

### Configuración Docker

- **Base de datos**: PostgreSQL (en contenedor)
- **Persistencia**: Volumen Docker `postgres_data`
- **Credenciales**: Ver `docker-compose.yml`

Para más detalles, consulta `DOCKER_GUIA.md`

## Próximos Pasos

1. Ejecutar tests unitarios: `mvn test`
2. Agregar más funcionalidades (búsqueda, filtros, paginación)
3. ✅ Configurar una base de datos real (PostgreSQL en Docker - Ya implementado)
4. Agregar autenticación y autorización
5. Implementar logging avanzado
6. ✅ Documentación con Swagger/OpenAPI (Ya implementado - ver `SWAGGER_DOCUMENTACION.md`)
7. ✅ Dockerización (Ya implementado - ver `DOCKER_GUIA.md`)