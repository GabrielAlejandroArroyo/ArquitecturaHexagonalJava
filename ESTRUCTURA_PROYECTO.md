# Estructura de Carpetas - Clean Architecture en Spring Boot

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── tuempresa/
│   │           └── proyecto/
│   │               ├── domain/                          # CAPA DE DOMINIO (Núcleo)
│   │               │   ├── model/                       # Entidades de dominio
│   │               │   │   ├── User.java
│   │               │   │   ├── Product.java
│   │               │   │   └── Order.java
│   │               │   ├── valueobject/                 # Value Objects
│   │               │   │   ├── Email.java
│   │               │   │   ├── Money.java
│   │               │   │   └── Address.java
│   │               │   ├── event/                       # Domain Events
│   │               │   │   ├── UserCreatedEvent.java
│   │               │   │   └── OrderPlacedEvent.java
│   │               │   ├── repository/                  # Interfaces de repositorio
│   │               │   │   ├── UserRepository.java
│   │               │   │   ├── ProductRepository.java
│   │               │   │   └── OrderRepository.java
│   │               │   └── service/                     # Domain Services
│   │               │       ├── UserDomainService.java
│   │               │       └── OrderDomainService.java
│   │               │
│   │               ├── application/                     # CAPA DE APLICACIÓN
│   │               │   ├── usecase/                     # Casos de uso
│   │               │   │   ├── user/
│   │               │   │   │   ├── CreateUserUseCase.java
│   │               │   │   │   ├── GetUserUseCase.java
│   │               │   │   │   └── UpdateUserUseCase.java
│   │               │   │   ├── product/
│   │               │   │   │   └── ...
│   │               │   │   └── order/
│   │               │   │       └── ...
│   │               │   ├── dto/                        # Data Transfer Objects
│   │               │   │   ├── request/
│   │               │   │   │   ├── CreateUserRequest.java
│   │               │   │   │   └── UpdateUserRequest.java
│   │               │   │   ├── response/
│   │               │   │   │   ├── UserResponse.java
│   │               │   │   │   └── ProductResponse.java
│   │               │   │   └── command/                 # Commands (CQRS)
│   │               │   │       ├── CreateUserCommand.java
│   │               │   │       └── UpdateUserCommand.java
│   │               │   ├── query/                      # Queries (CQRS)
│   │               │   │   ├── GetUserQuery.java
│   │               │   │   └── GetAllUsersQuery.java
│   │               │   ├── mapper/                     # Mappers (MapStruct)
│   │               │   │   ├── UserMapper.java
│   │               │   │   └── ProductMapper.java
│   │               │   └── validator/                  # Validadores
│   │               │       ├── UserValidator.java
│   │               │       └── OrderValidator.java
│   │               │
│   │               ├── infrastructure/                 # CAPA DE INFRAESTRUCTURA
│   │               │   ├── persistence/                # Persistencia
│   │               │   │   ├── entity/                 # Entidades JPA
│   │               │   │   │   ├── UserEntity.java
│   │               │   │   │   ├── ProductEntity.java
│   │               │   │   │   └── OrderEntity.java
│   │               │   │   ├── repository/             # Implementaciones de repositorios
│   │               │   │   │   ├── JpaUserRepository.java
│   │               │   │   │   ├── JpaProductRepository.java
│   │               │   │   │   └── JpaOrderRepository.java
│   │               │   │   ├── adapter/                # Adapters (implementan interfaces del dominio)
│   │               │   │   │   ├── UserRepositoryAdapter.java
│   │               │   │   │   ├── ProductRepositoryAdapter.java
│   │               │   │   │   └── OrderRepositoryAdapter.java
│   │               │   │   └── config/                 # Configuración JPA
│   │               │   │       └── JpaConfig.java
│   │               │   ├── external/                   # Servicios externos
│   │               │   │   ├── email/
│   │               │   │   │   ├── EmailService.java
│   │               │   │   │   └── EmailServiceImpl.java
│   │               │   │   ├── payment/
│   │               │   │   │   └── PaymentGatewayAdapter.java
│   │               │   │   └── notification/
│   │               │   │       └── NotificationService.java
│   │               │   ├── logging/                    # Logging
│   │               │   │   └── LoggingService.java
│   │               │   └── config/                    # Configuración de Spring
│   │               │       ├── BeanConfig.java
│   │               │       ├── SecurityConfig.java
│   │               │       └── WebConfig.java
│   │               │
│   │               ├── presentation/                   # CAPA DE PRESENTACIÓN
│   │               │   ├── controller/                 # Controladores REST
│   │               │   │   ├── UserController.java
│   │               │   │   ├── ProductController.java
│   │               │   │   └── OrderController.java
│   │               │   ├── dto/                       # DTOs de request/response HTTP
│   │               │   │   ├── request/
│   │               │   │   │   ├── CreateUserHttpRequest.java
│   │               │   │   │   └── UpdateUserHttpRequest.java
│   │               │   │   └── response/
│   │               │   │       ├── UserHttpResponse.java
│   │               │   │       └── ApiResponse.java
│   │               │   ├── exception/                 # Manejo de excepciones
│   │               │   │   ├── GlobalExceptionHandler.java
│   │               │   │   ├── ResourceNotFoundException.java
│   │               │   │   └── ValidationException.java
│   │               │   ├── mapper/                    # Mappers HTTP (opcional)
│   │               │   │   └── HttpMapper.java
│   │               │   └── security/                  # Seguridad
│   │               │       ├── JwtTokenProvider.java
│   │               │       └── SecurityConfig.java
│   │               │
│   │               └── ProyectoApplication.java        # Clase principal de Spring Boot
│   │
│   └── resources/
│       ├── application.properties                      # Configuración de la aplicación
│       ├── application-dev.properties                 # Configuración desarrollo
│       ├── application-prod.properties                 # Configuración producción
│       └── db/
│           └── migration/                             # Scripts de migración (Flyway/Liquibase)
│               └── V1__initial_schema.sql
│
└── test/
    ├── java/
    │   └── com/
    │       └── tuempresa/
    │           └── proyecto/
    │               ├── domain/                         # Tests de dominio
    │               │   ├── model/
    │               │   └── service/
    │               ├── application/                    # Tests de aplicación
    │               │   └── usecase/
    │               ├── infrastructure/                 # Tests de infraestructura
    │               │   └── persistence/
    │               └── presentation/                  # Tests de presentación
    │                   └── controller/
    └── resources/
        └── application-test.properties                 # Configuración para tests
```

## Explicación de las Capas

### Domain (Dominio)
- **model/**: Entidades puras de negocio, sin anotaciones JPA
- **valueobject/**: Objetos de valor inmutables
- **event/**: Eventos de dominio para comunicación asíncrona
- **repository/**: Solo interfaces, sin implementación
- **service/**: Lógica de negocio compleja que no pertenece a una entidad

### Application (Aplicación)
- **usecase/**: Casos de uso que orquestan la lógica de negocio
- **dto/**: Objetos para transferir datos entre capas
- **mapper/**: Conversión entre entidades y DTOs
- **validator/**: Validación de reglas de negocio

### Infrastructure (Infraestructura)
- **persistence/entity/**: Entidades JPA (anotadas con @Entity)
- **persistence/repository/**: Implementaciones JPA de repositorios
- **persistence/adapter/**: Adaptadores que implementan interfaces del dominio
- **external/**: Integraciones con servicios externos
- **config/**: Configuración de Spring

### Presentation (Presentación)
- **controller/**: Endpoints REST
- **dto/**: DTOs específicos para HTTP
- **exception/**: Manejo global de excepciones
- **security/**: Configuración de seguridad

## Dependencias entre Capas

```
Presentation → Application → Domain
Infrastructure → Application → Domain
```

**Regla de oro**: Las dependencias siempre apuntan hacia adentro. El dominio no conoce nada de las otras capas.
