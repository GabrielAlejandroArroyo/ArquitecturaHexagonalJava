# Comandos Docker - Referencia Rápida

## Inicio Rápido

```bash
# Construir y levantar todo
docker-compose up --build

# En segundo plano
docker-compose up -d --build

# Detener todo
docker-compose down
```

## Comandos Útiles

### Gestión de Servicios

```bash
# Ver estado
docker-compose ps

# Ver logs
docker-compose logs -f app
docker-compose logs -f postgres

# Reiniciar
docker-compose restart app

# Detener sin eliminar
docker-compose stop

# Detener y eliminar volúmenes
docker-compose down -v
```

### Base de Datos

```bash
# Conectarse a PostgreSQL
docker-compose exec postgres psql -U proyecto_user -d proyecto_db

# Backup
docker-compose exec postgres pg_dump -U proyecto_user proyecto_db > backup.sql

# Restaurar
docker-compose exec -T postgres psql -U proyecto_user proyecto_db < backup.sql
```

### Desarrollo

```bash
# Modo desarrollo (con debug y hot reload)
docker-compose -f docker-compose.dev.yml up --build

# Reconstruir sin cache
docker-compose build --no-cache

# Ver logs en tiempo real
docker-compose logs -f
```

### Limpieza

```bash
# Eliminar contenedores parados
docker-compose down

# Eliminar imágenes no usadas
docker image prune

# Limpieza completa
docker system prune -a --volumes
```

## URLs

- API: http://localhost:8080/api/products
- Swagger: http://localhost:8080/swagger-ui.html
- PostgreSQL: localhost:5432

## Troubleshooting

```bash
# Ver qué está usando el puerto
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Ver logs de errores
docker-compose logs app | grep -i error

# Verificar conectividad
docker-compose exec app ping postgres
```
