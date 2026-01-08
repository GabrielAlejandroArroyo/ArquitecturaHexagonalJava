# Guía Rápida - Clean Architecture en Spring Boot

## Resumen Visual de las Capas

```
┌─────────────────────────────────────────┐
│   PRESENTATION (Controllers, DTOs)      │  ← Capa Externa
├─────────────────────────────────────────┤
│   APPLICATION (Use Cases, DTOs)         │
├─────────────────────────────────────────┤
│   DOMAIN (Entities, Interfaces)         │  ← Capa Interna (Núcleo)
├─────────────────────────────────────────┤
│   INFRASTRUCTURE (JPA, External APIs)   │  ← Capa Externa
└─────────────────────────────────────────┘
```

## Flujo de Dependencias

```
Presentation → Application → Domain
Infrastructure → Application → Domain
```

**Regla de Oro**: Las dependencias siempre apuntan hacia adentro. El dominio es el núcleo y no conoce nada de las otras capas.

## Comparación con .NET Core

| .NET Core | Spring Boot |
|-----------|-------------|
| Entities | Domain Model |
| Value Objects | Value Objects |
| Repository Interfaces | Repository Interfaces |
| Use Cases / Application Services | Use Cases |
| DTOs | DTOs |
| FluentValidation | Bean Validation |
| MediatR (CQRS) | Casos de uso manuales o Spring Events |
| AutoMapper | MapStruct |
| EF Core | Spring Data JPA |
| Controllers | Controllers (Spring MVC) |
| JWT / Identity | Spring Security + JWT |

## Pasos para Implementar una Nueva Funcionalidad

### 1. Domain Layer
1. Crear entidad en `domain/model/`
2. Crear value objects si es necesario en `domain/valueobject/`
3. Definir interfaz de repositorio en `domain/repository/`

### 2. Application Layer
1. Crear DTOs de request/response en `application/dto/`
2. Crear caso de uso en `application/usecase/`
3. Crear mapper en `application/mapper/` (MapStruct)

### 3. Infrastructure Layer
1. Crear entidad JPA en `infrastructure/persistence/entity/`
2. Crear repositorio JPA en `infrastructure/persistence/repository/`
3. Crear adapter que implemente la interfaz del dominio en `infrastructure/persistence/adapter/`

### 4. Presentation Layer
1. Crear controller en `presentation/controller/`
2. Crear DTOs HTTP si es necesario en `presentation/dto/`
3. Configurar endpoints REST

## Ejemplo de Flujo Completo

```
POST /api/users
    ↓
UserController.createUser()
    ↓
CreateUserUseCase.execute()
    ↓
User.create() [Domain]
    ↓
UserRepository.save() [Interface]
    ↓
UserRepositoryAdapter.save() [Infrastructure]
    ↓
JpaUserRepository.save() [JPA]
    ↓
Base de Datos
```

## Dependencias Maven Recomendadas

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- MapStruct para mapeo -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- Base de datos (ejemplo con H2 para desarrollo) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Checklist de Implementación

- [ ] Entidad de dominio creada (sin JPA)
- [ ] Interfaz de repositorio definida en dominio
- [ ] Caso de uso implementado
- [ ] DTOs creados (request/response)
- [ ] Mapper configurado (MapStruct)
- [ ] Entidad JPA creada
- [ ] Repositorio JPA creado
- [ ] Adapter implementado
- [ ] Controller creado
- [ ] Validaciones aplicadas
- [ ] Manejo de excepciones configurado
- [ ] Tests unitarios escritos

## Recursos Adicionales

- Ver `README.md` para explicación detallada
- Ver `ESTRUCTURA_PROYECTO.md` para estructura completa
- Ver `EJEMPLO_IMPLEMENTACION.md` para ejemplos de código
