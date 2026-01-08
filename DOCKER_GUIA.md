# Guía de Dockerización - Proyecto Spring Boot

Esta guía explica cómo ejecutar la aplicación usando Docker y Docker Compose.

## Requisitos Previos

- **Docker Desktop** instalado y corriendo
- **Docker Compose** v2.0 o superior (incluido en Docker Desktop)

Verifica la instalación:
```bash
docker --version
docker-compose --version
```

## Estructura de Archivos Docker

```
.
├── Dockerfile                 # Imagen de la aplicación
├── docker-compose.yml        # Orquestación de servicios
├── .dockerignore             # Archivos a ignorar en el build
└── src/main/resources/
    └── application-docker.properties  # Configuración para Docker
```

## Ejecución Rápida

### Opción 1: Docker Compose (Recomendado)

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# En modo detached (background)
docker-compose up -d --build
```

Esto levantará:
- **PostgreSQL** en el puerto 5432
- **Aplicación Spring Boot** en el puerto 8080

### Opción 2: Solo la Aplicación (sin Docker Compose)

```bash
# Construir la imagen
docker build -t proyecto-app .

# Ejecutar el contenedor
docker run -p 8080:8080 proyecto-app
```

## Comandos Útiles

### Ver logs
```bash
# Todos los servicios
docker-compose logs -f

# Solo la aplicación
docker-compose logs -f app

# Solo PostgreSQL
docker-compose logs -f postgres
```

### Detener servicios
```bash
# Detener y eliminar contenedores
docker-compose down

# Detener y eliminar contenedores + volúmenes
docker-compose down -v
```

### Reiniciar servicios
```bash
docker-compose restart
```

### Reconstruir sin cache
```bash
docker-compose build --no-cache
docker-compose up
```

### Acceder a la base de datos
```bash
# Conectarse a PostgreSQL
docker-compose exec postgres psql -U proyecto_user -d proyecto_db

# O desde fuera del contenedor
psql -h localhost -p 5432 -U proyecto_user -d proyecto_db
```

### Acceder al shell del contenedor de la app
```bash
docker-compose exec app sh
```

## URLs de Acceso

Una vez que los servicios estén corriendo:

- **API**: http://localhost:8080/api/products
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **PostgreSQL**: localhost:5432

## Configuración de Base de Datos

### Credenciales por Defecto

Estas están definidas en `docker-compose.yml`:

```yaml
POSTGRES_DB: proyecto_db
POSTGRES_USER: proyecto_user
POSTGRES_PASSWORD: proyecto_pass
```

### Cambiar Credenciales

1. Edita `docker-compose.yml`:
```yaml
environment:
  POSTGRES_DB: tu_base_de_datos
  POSTGRES_USER: tu_usuario
  POSTGRES_PASSWORD: tu_contraseña
```

2. Actualiza `application-docker.properties`:
```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/tu_base_de_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## Persistencia de Datos

Los datos de PostgreSQL se almacenan en un volumen Docker llamado `postgres_data`.

### Ver volúmenes
```bash
docker volume ls
```

### Eliminar volumen (¡CUIDADO! Esto borra los datos)
```bash
docker-compose down -v
```

### Backup de base de datos
```bash
docker-compose exec postgres pg_dump -U proyecto_user proyecto_db > backup.sql
```

### Restaurar base de datos
```bash
docker-compose exec -T postgres psql -U proyecto_user proyecto_db < backup.sql
```

## Desarrollo con Docker

### Hot Reload (Desarrollo)

Para desarrollo con recarga automática, puedes montar el código fuente:

1. Modifica `docker-compose.yml`:
```yaml
app:
  volumes:
    - ./src:/app/src
  environment:
    SPRING_DEVTOOLS_RESTART_ENABLED: "true"
```

2. Reconstruye:
```bash
docker-compose up --build
```

### Debug Remoto

Para debug remoto, agrega al `docker-compose.yml`:
```yaml
app:
  ports:
    - "8080:8080"
    - "5005:5005"  # Puerto de debug
  environment:
    JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

## Troubleshooting

### Error: "Port already in use"
```bash
# Ver qué está usando el puerto
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Cambiar puerto en docker-compose.yml
ports:
  - "8081:8080"  # Puerto externo:puerto interno
```

### Error: "Cannot connect to database"
1. Verifica que PostgreSQL esté corriendo:
```bash
docker-compose ps
```

2. Verifica los logs:
```bash
docker-compose logs postgres
```

3. Verifica las variables de entorno en `docker-compose.yml`

### Error: "Build failed"
```bash
# Limpia y reconstruye
docker-compose down
docker system prune -f
docker-compose build --no-cache
docker-compose up
```

### La aplicación no inicia
```bash
# Ver logs detallados
docker-compose logs app

# Verificar que la aplicación esté esperando a PostgreSQL
docker-compose logs app | grep -i "database"
```

### Limpiar todo Docker
```bash
# Detener todos los contenedores
docker-compose down

# Eliminar imágenes no usadas
docker image prune -a

# Eliminar volúmenes no usados
docker volume prune

# Limpieza completa (¡CUIDADO!)
docker system prune -a --volumes
```

## Optimización de la Imagen

### Multi-stage Build

El `Dockerfile` ya usa multi-stage build para:
- Reducir el tamaño de la imagen final
- Separar el proceso de build del runtime
- Mejorar la seguridad (usuario no root)

### Tamaño de la Imagen

Para ver el tamaño:
```bash
docker images proyecto-app
```

Para optimizar más:
- Usa `.dockerignore` (ya incluido)
- Usa imágenes base más pequeñas (alpine)
- Limpia cache de Maven en el build

## Producción

### Variables de Entorno

Para producción, usa variables de entorno en lugar de hardcodear valores:

```yaml
app:
  environment:
    SPRING_DATASOURCE_URL: ${DATABASE_URL}
    SPRING_DATASOURCE_USERNAME: ${DATABASE_USER}
    SPRING_DATASOURCE_PASSWORD: ${DATABASE_PASSWORD}
```

### Health Checks

El Dockerfile incluye un health check. Para verificar:
```bash
docker ps  # Verá el estado HEALTHY/UNHEALTHY
```

### Logging

Los logs se pueden redirigir a un sistema centralizado:
```yaml
app:
  logging:
    driver: "json-file"
    options:
      max-size: "10m"
      max-file: "3"
```

## Comandos de Producción

```bash
# Build para producción
docker-compose -f docker-compose.yml build

# Levantar en producción
docker-compose -f docker-compose.yml up -d

# Ver estado
docker-compose ps

# Ver logs
docker-compose logs -f app
```

## Integración Continua (CI/CD)

Ejemplo para GitHub Actions:

```yaml
- name: Build Docker image
  run: docker build -t proyecto-app .

- name: Run tests
  run: docker-compose run app mvn test
```

## Recursos Adicionales

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
