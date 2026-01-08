# Domain Layer (Capa de Dominio)

Esta es la capa más interna y **NO depende de ninguna otra capa**.

## Contenido

- **model/**: Entidades de dominio puras (sin anotaciones JPA)
- **valueobject/**: Objetos de valor inmutables
- **event/**: Eventos de dominio
- **repository/**: Solo interfaces, sin implementación
- **service/**: Servicios de dominio con lógica de negocio compleja

## Reglas

- ❌ NO usar anotaciones de Spring (@Service, @Component, etc.)
- ❌ NO usar anotaciones JPA (@Entity, @Table, etc.)
- ❌ NO importar clases de otras capas
- ✅ Solo lógica de negocio pura
- ✅ Interfaces para repositorios y servicios externos
