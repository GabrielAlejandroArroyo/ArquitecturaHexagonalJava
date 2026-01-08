# Documentación Swagger/OpenAPI

## ¿Qué es Swagger?

Swagger (ahora OpenAPI) es una herramienta que genera documentación interactiva de tu API REST automáticamente. Permite visualizar, probar y entender todos los endpoints de tu API sin necesidad de escribir documentación manual.

## Acceso a Swagger UI

Una vez que ejecutes la aplicación, puedes acceder a la interfaz de Swagger en:

### URL Principal
```
http://localhost:8080/swagger-ui.html
```

### URLs Alternativas
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- API Docs JSON: `http://localhost:8080/api-docs`
- API Docs YAML: `http://localhost:8080/api-docs.yaml`

## Características Implementadas

### 1. Configuración Personalizada
- **Título**: "API de Productos - Clean Architecture"
- **Versión**: 1.0.0
- **Descripción**: Documentación completa de la API
- **Contacto**: Información de contacto configurable
- **Servidores**: Desarrollo y Producción

### 2. Documentación de Endpoints

Todos los endpoints están documentados con:
- **Descripción**: Qué hace cada endpoint
- **Parámetros**: Descripción de cada parámetro con ejemplos
- **Respuestas**: Códigos de estado HTTP y ejemplos de respuestas
- **Ejemplos**: Ejemplos de request y response

### 3. Documentación de DTOs

Los DTOs incluyen:
- **Descripción**: Qué representa cada campo
- **Ejemplos**: Valores de ejemplo
- **Validaciones**: Restricciones y reglas de validación
- **Tipos**: Tipos de datos y formatos

## Endpoints Documentados

### 1. POST /api/products
- **Descripción**: Crear un nuevo producto
- **Request Body**: CreateProductRequest
- **Response**: ProductResponse (201 Created)
- **Errores**: 400 Bad Request (validación)

### 2. GET /api/products/{id}
- **Descripción**: Obtener un producto por ID
- **Path Parameter**: id (UUID)
- **Response**: ProductResponse (200 OK)
- **Errores**: 400 Bad Request (no encontrado)

### 3. GET /api/products
- **Descripción**: Obtener todos los productos
- **Response**: List<ProductResponse> (200 OK)

### 4. PUT /api/products/{id}
- **Descripción**: Actualizar un producto existente
- **Path Parameter**: id (UUID)
- **Request Body**: UpdateProductRequest
- **Response**: ProductResponse (200 OK)
- **Errores**: 400 Bad Request, 409 Conflict

### 5. DELETE /api/products/{id}
- **Descripción**: Eliminar un producto
- **Path Parameter**: id (UUID)
- **Response**: 204 No Content
- **Errores**: 400 Bad Request

## Cómo Usar Swagger UI

### 1. Explorar la API
1. Abre `http://localhost:8080/swagger-ui.html`
2. Verás todos los endpoints organizados por tags
3. Expande cada endpoint para ver detalles

### 2. Probar Endpoints
1. Haz clic en un endpoint para expandirlo
2. Haz clic en "Try it out"
3. Completa los parámetros necesarios
4. Haz clic en "Execute"
5. Verás la respuesta del servidor

### 3. Ver Ejemplos
- Cada campo tiene ejemplos predefinidos
- Los DTOs muestran la estructura completa
- Las respuestas incluyen ejemplos de éxito y error

### 4. Descargar Especificación
- Puedes descargar la especificación OpenAPI en formato JSON o YAML
- Útil para generar clientes SDK o importar en otras herramientas

## Ejemplo de Uso

### Crear un Producto desde Swagger

1. Abre Swagger UI: `http://localhost:8080/swagger-ui.html`
2. Expande el endpoint `POST /api/products`
3. Haz clic en "Try it out"
4. Completa el Request Body:
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
5. Haz clic en "Execute"
6. Verás la respuesta con el producto creado

## Configuración

### Personalizar Información

Edita `OpenApiConfig.java` para cambiar:
- Título y descripción
- Información de contacto
- URLs de servidores
- Licencia

### Personalizar UI

Edita `application.properties` para cambiar:
- Ruta de Swagger UI
- Ruta de API Docs
- Ordenamiento de operaciones
- Expansión de documentación

## Ventajas de Swagger

✅ **Documentación Automática**: Se genera automáticamente desde el código
✅ **Siempre Actualizada**: Refleja los cambios en tiempo real
✅ **Interactiva**: Puedes probar endpoints directamente
✅ **Estándar OpenAPI**: Compatible con muchas herramientas
✅ **Fácil de Compartir**: Exportable en JSON/YAML
✅ **Reduce Errores**: Los desarrolladores ven exactamente qué esperar

## Integración con Otras Herramientas

### Postman
- Importa el JSON de `/api-docs` en Postman
- Genera una colección completa automáticamente

### Insomnia
- Importa el JSON de `/api-docs` en Insomnia
- Crea requests automáticamente

### Generación de Clientes
- Usa herramientas como `openapi-generator` para generar clientes SDK
- Soporta múltiples lenguajes (Java, Python, JavaScript, etc.)

## Troubleshooting

### Swagger UI no se muestra
- Verifica que la aplicación esté corriendo
- Revisa la consola por errores
- Asegúrate de que el puerto sea 8080

### Endpoints no aparecen
- Verifica que los controllers tengan `@RestController`
- Revisa que los métodos tengan anotaciones HTTP correctas
- Verifica que no haya errores de compilación

### Errores 404 en Swagger UI
- Verifica la ruta en `application.properties`
- Asegúrate de que `springdoc.swagger-ui.path` esté correcto

## Próximos Pasos

1. Agregar autenticación JWT a Swagger
2. Agregar más ejemplos de respuestas
3. Documentar códigos de error personalizados
4. Agregar tags adicionales para organizar mejor
5. Configurar múltiples versiones de API
