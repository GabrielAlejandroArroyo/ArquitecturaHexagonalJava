# Solución: Error de Versión de PostgreSQL

## Problema

El error indica que hay un volumen Docker con datos de PostgreSQL 15, pero el contenedor está intentando usar PostgreSQL 16, que no es compatible.

```
The data directory was initialized by PostgreSQL version 15, which is not compatible with this version 16.11.
```

## Solución Aplicada

Se cambió la versión de PostgreSQL de 16 a 15 en ambos archivos:
- `docker-compose.yml`
- `docker-compose.dev.yml`

## Opciones de Solución

### Opción 1: Usar PostgreSQL 15 (Ya aplicada)

Los archivos ya están configurados para usar PostgreSQL 15, que es compatible con tus datos existentes.

```bash
docker-compose up --build
```

### Opción 2: Eliminar el volumen y usar PostgreSQL 16

Si quieres empezar limpio con PostgreSQL 16 (y no te importa perder los datos):

1. **Detener y eliminar el volumen:**
```bash
docker-compose down -v
```

2. **Cambiar la versión en docker-compose.yml:**
```yaml
image: postgres:16-alpine
```

3. **Levantar de nuevo:**
```bash
docker-compose up --build
```

### Opción 3: Migrar datos de PostgreSQL 15 a 16

Si necesitas mantener los datos y usar PostgreSQL 16:

1. **Hacer backup de los datos:**
```bash
docker-compose exec postgres pg_dump -U proyecto_user proyecto_db > backup.sql
```

2. **Eliminar el volumen:**
```bash
docker-compose down -v
```

3. **Cambiar a PostgreSQL 16 en docker-compose.yml**

4. **Levantar PostgreSQL 16:**
```bash
docker-compose up -d postgres
```

5. **Restaurar los datos:**
```bash
docker-compose exec -T postgres psql -U proyecto_user proyecto_db < backup.sql
```

## Verificar la Versión

Para verificar qué versión de PostgreSQL está corriendo:

```bash
docker-compose exec postgres psql --version
```

## Recomendación

- **Para desarrollo**: Usa PostgreSQL 15 (ya configurado) si tienes datos que quieres mantener
- **Para empezar limpio**: Elimina el volumen y usa PostgreSQL 16
- **Para producción**: Usa la versión más reciente estable (16) con un proceso de migración adecuado
