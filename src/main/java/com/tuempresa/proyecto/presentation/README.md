# Presentation Layer (Capa de Presentación)

Interfaz con el mundo exterior (HTTP, REST API).

## Contenido

- **controller/**: Controladores REST (Spring MVC)
- **dto/**: DTOs específicos para HTTP (request/response)
- **exception/**: Manejo global de excepciones
- **mapper/**: Mappers para convertir entre DTOs de aplicación y HTTP
- **security/**: Configuración de seguridad (Spring Security, JWT)

## Reglas

- ✅ Depende de Application Layer
- ✅ Maneja HTTP, validación de entrada, serialización JSON
- ❌ NO contiene lógica de negocio
- ✅ Usa casos de uso de Application Layer
