# Application Layer (Capa de Aplicación)

Orquesta los casos de uso de la aplicación.

## Contenido

- **usecase/**: Casos de uso que implementan la lógica de aplicación
- **dto/**: Data Transfer Objects para comunicación entre capas
- **mapper/**: Mappers para convertir entre entidades y DTOs (MapStruct)
- **validator/**: Validadores de reglas de negocio
- **query/**: Queries para consultas (patrón CQRS)

## Reglas

- ✅ Depende solo del Domain Layer
- ✅ Usa interfaces del dominio, no implementaciones
- ✅ Orquesta casos de uso
- ❌ NO conoce detalles de infraestructura (JPA, HTTP, etc.)
