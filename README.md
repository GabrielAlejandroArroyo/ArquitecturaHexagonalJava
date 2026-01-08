# Arquitectura Hexagonal / Clean Architecture en Spring Boot

## ¿Qué es Clean Architecture?

Clean Architecture (Arquitectura Limpia) es un patrón arquitectónico que organiza el código en capas concéntricas, donde las capas internas no dependen de las externas. Esto garantiza que la lógica de negocio esté aislada de frameworks, bases de datos, interfaces de usuario y otros detalles de implementación.

## Principios Fundamentales

### 1. **Separación de Responsabilidades**
Cada capa tiene una responsabilidad única y bien definida.

### 2. **Inversión de Dependencias**
Las capas internas definen interfaces (contratos), y las capas externas implementan esas interfaces. La dependencia apunta hacia adentro.

### 3. **Independencia de Frameworks**
La lógica de negocio no depende de Spring Boot, JPA, ni ningún framework externo.

### 4. **Testabilidad**
Al estar desacoplada, cada capa puede ser testeada de forma independiente.

## Capas en Spring Boot

### 1. **Domain Layer (Capa de Dominio) - Núcleo**
Esta es la capa más interna y contiene:
- **Entities**: Objetos de negocio con lógica de dominio
- **Value Objects**: Objetos inmutables que representan conceptos del dominio
- **Domain Events**: Eventos que ocurren en el dominio
- **Repository Interfaces**: Contratos que definen cómo acceder a los datos (sin implementación)
- **Domain Services**: Servicios que contienen lógica de negocio compleja

**Características:**
- No depende de ninguna otra capa
- No conoce Spring Boot, JPA, ni frameworks externos
- Contiene la lógica de negocio pura

### 2. **Application Layer (Capa de Aplicación)**
Contiene la lógica de casos de uso:
- **Use Cases / Application Services**: Orquestan las operaciones de negocio
- **DTOs (Data Transfer Objects)**: Objetos para transferir datos entre capas
- **Commands y Queries**: Patrón CQRS para separar lectura y escritura
- **Validators**: Validación de reglas de negocio (Bean Validation)
- **Mappers**: Conversión entre entidades y DTOs (MapStruct)

**Características:**
- Depende solo del Domain Layer
- Orquesta los casos de uso de la aplicación
- No conoce detalles de infraestructura

### 3. **Infrastructure Layer (Capa de Infraestructura)**
Implementa los detalles técnicos:
- **Repositories**: Implementaciones concretas de los repositorios (JPA, JDBC, etc.)
- **Persistence**: Configuración de JPA/Hibernate, entidades JPA
- **External Services**: Integraciones con APIs externas, servicios de email, logging
- **Configuration**: Configuración de Spring, beans, propiedades

**Características:**
- Implementa las interfaces definidas en Domain
- Maneja todos los detalles técnicos
- Depende de Application y Domain

### 4. **Presentation Layer (Capa de Presentación)**
Interfaz con el mundo exterior:
- **Controllers**: Endpoints REST (Spring MVC)
- **DTOs de Request/Response**: Objetos para recibir y enviar datos
- **Exception Handlers**: Manejo global de excepciones
- **Security**: Configuración de Spring Security, JWT

**Características:**
- Depende de Application Layer
- Maneja HTTP, validación de entrada, serialización JSON
- No contiene lógica de negocio

## Flujo de Comunicación

```
Usuario → Controller → Application Service → Domain Service → Repository Interface
                                                                    ↓
                                                            Repository Implementation (Infrastructure)
                                                                    ↓
                                                              Base de Datos
```

### Ejemplo de Flujo:

1. **Usuario envía request** → Controller recibe el request
2. **Controller** → Llama al Application Service (Use Case)
3. **Application Service** → Ejecuta lógica de negocio usando Domain Services y Repository Interfaces
4. **Repository Interface** → Implementado por Infrastructure Layer (JPA Repository)
5. **Infrastructure** → Accede a la base de datos
6. **Respuesta** → Se devuelve a través de las capas como DTO

## Beneficios

- ✅ **Alta testabilidad**: Cada capa puede testearse independientemente
- ✅ **Mantenibilidad**: Cambios en una capa no afectan otras
- ✅ **Despliegue independiente**: Capas pueden evolucionar por separado
- ✅ **Límites claros**: Cada capa tiene responsabilidades bien definidas
- ✅ **Flexibilidad**: Fácil cambiar frameworks o tecnologías sin afectar el dominio

## Herramientas y Librerías en Spring Boot

- **Spring Boot**: Framework base
- **Spring Data JPA**: Para acceso a datos
- **MapStruct**: Para mapeo entre objetos
- **Bean Validation**: Para validación
- **Spring Security**: Para autenticación y autorización
- **Lombok**: Para reducir boilerplate
- **JUnit 5 / Mockito**: Para testing

## Estructura de Carpetas

Ver la estructura completa en el proyecto. Las carpetas están organizadas según las capas descritas arriba.

## Ejecución Rápida

### Con Docker (Recomendado)

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# Acceder a la API
# http://localhost:8080/api/products
# http://localhost:8080/swagger-ui.html
```

### Localmente

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

Para más detalles, consulta:
- `INSTRUCCIONES_EJECUCION.md` - Instrucciones detalladas
- `DOCKER_GUIA.md` - Guía completa de Docker
