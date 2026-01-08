# Ejemplo de Implementación - Clean Architecture en Spring Boot

Este documento muestra ejemplos concretos de cómo implementar cada capa siguiendo Clean Architecture.

## Ejemplo: Gestión de Usuarios

### 1. Domain Layer - Entidad de Dominio

```java
// domain/model/User.java
package com.tuempresa.proyecto.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private Email email;
    private LocalDateTime createdAt;
    private boolean active;

    // Constructor privado para forzar uso del factory method
    private User() {}

    public static User create(String name, String email) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.name = name;
        user.email = Email.of(email);
        user.createdAt = LocalDateTime.now();
        user.active = true;
        return user;
    }

    public void deactivate() {
        if (!this.active) {
            throw new IllegalStateException("User is already inactive");
        }
        this.active = false;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Email getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
}
```

### 2. Domain Layer - Value Object

```java
// domain/valueobject/Email.java
package com.tuempresa.proyecto.domain.valueobject;

import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private final String value;

    private Email(String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value.toLowerCase();
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```

### 3. Domain Layer - Repository Interface

```java
// domain/repository/UserRepository.java
package com.tuempresa.proyecto.domain.repository;

import com.tuempresa.proyecto.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    void delete(UUID id);
    boolean existsByEmail(String email);
}
```

### 4. Application Layer - DTO

```java
// application/dto/request/CreateUserRequest.java
package com.tuempresa.proyecto.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

```java
// application/dto/response/UserResponse.java
package com.tuempresa.proyecto.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private boolean active;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
```

### 5. Application Layer - Use Case

```java
// application/usecase/user/CreateUserUseCase.java
package com.tuempresa.proyecto.application.usecase.user;

import com.tuempresa.proyecto.application.dto.request.CreateUserRequest;
import com.tuempresa.proyecto.application.dto.response.UserResponse;
import com.tuempresa.proyecto.application.mapper.UserMapper;
import com.tuempresa.proyecto.domain.model.User;
import com.tuempresa.proyecto.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Crear usuario usando el dominio
        User user = User.create(request.getName(), request.getEmail());

        // Guardar usando el repositorio
        User savedUser = userRepository.save(user);

        // Convertir a DTO de respuesta
        return userMapper.toResponse(savedUser);
    }
}
```

### 6. Application Layer - Mapper

```java
// application/mapper/UserMapper.java
package com.tuempresa.proyecto.application.mapper;

import com.tuempresa.proyecto.application.dto.response.UserResponse;
import com.tuempresa.proyecto.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(source = "email.value", target = "email")
    UserResponse toResponse(User user);
}
```

### 7. Infrastructure Layer - Entidad JPA

```java
// infrastructure/persistence/entity/UserEntity.java
package com.tuempresa.proyecto.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private boolean active;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
```

### 8. Infrastructure Layer - Repositorio JPA

```java
// infrastructure/persistence/repository/JpaUserRepository.java
package com.tuempresa.proyecto.infrastructure.persistence.repository;

import com.tuempresa.proyecto.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

### 9. Infrastructure Layer - Adapter (Implementa interfaz del dominio)

```java
// infrastructure/persistence/adapter/UserRepositoryAdapter.java
package com.tuempresa.proyecto.infrastructure.persistence.adapter;

import com.tuempresa.proyecto.domain.model.User;
import com.tuempresa.proyecto.domain.repository.UserRepository;
import com.tuempresa.proyecto.infrastructure.persistence.entity.UserEntity;
import com.tuempresa.proyecto.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper entityMapper;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, 
                                 UserEntityMapper entityMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = entityMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id)
            .map(entityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
            .map(entityMapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
```

### 10. Presentation Layer - Controller

```java
// presentation/controller/UserController.java
package com.tuempresa.proyecto.presentation.controller;

import com.tuempresa.proyecto.application.dto.request.CreateUserRequest;
import com.tuempresa.proyecto.application.dto.response.UserResponse;
import com.tuempresa.proyecto.application.usecase.user.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UserResponse response = createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

### 11. Presentation Layer - Exception Handler

```java
// presentation/exception/GlobalExceptionHandler.java
package com.tuempresa.proyecto.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
```

## Flujo Completo

1. **Cliente HTTP** → Envía POST `/api/users` con JSON
2. **UserController** → Recibe request, valida con `@Valid`
3. **CreateUserUseCase** → Ejecuta lógica de negocio
4. **User.create()** → Crea entidad de dominio
5. **UserRepository.save()** → Interfaz del dominio
6. **UserRepositoryAdapter** → Implementa la interfaz
7. **JpaUserRepository** → Guarda en base de datos
8. **Respuesta** → Se devuelve como JSON al cliente

## Ventajas de esta Arquitectura

- ✅ **Domain puro**: No depende de JPA ni Spring
- ✅ **Testeable**: Cada capa se testea independientemente
- ✅ **Flexible**: Puedes cambiar JPA por JDBC sin tocar el dominio
- ✅ **Mantenible**: Cambios en una capa no afectan otras
- ✅ **Escalable**: Fácil agregar nuevas funcionalidades
