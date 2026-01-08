# Infrastructure Layer (Capa de Infraestructura)

Implementa los detalles técnicos y de infraestructura.

## Contenido

- **persistence/**: Implementación de persistencia (JPA, JDBC)
  - **entity/**: Entidades JPA (anotadas con @Entity)
  - **repository/**: Repositorios JPA (Spring Data JPA)
  - **adapter/**: Adaptadores que implementan interfaces del dominio
- **external/**: Integraciones con servicios externos (email, payment, etc.)
- **config/**: Configuración de Spring (beans, seguridad, etc.)
- **logging/**: Servicios de logging

## Reglas

- ✅ Implementa interfaces definidas en Domain Layer
- ✅ Maneja todos los detalles técnicos
- ✅ Depende de Application y Domain
- ✅ Aquí sí se usan anotaciones de Spring y JPA
