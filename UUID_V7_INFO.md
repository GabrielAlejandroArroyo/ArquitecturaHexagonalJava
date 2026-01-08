# UUID v7 - Información y Uso

## ¿Qué es UUID v7?

UUID v7 es una versión de UUID que incluye un timestamp de Unix (48 bits) en los primeros bits del identificador. Esto proporciona varias ventajas:

### Ventajas de UUID v7

1. **Ordenamiento Cronológico**: Los UUIDs v7 se pueden ordenar cronológicamente sin necesidad de un campo de timestamp adicional
2. **Mejor Rendimiento en Bases de Datos**: Los índices funcionan mejor con valores ordenados
3. **Trazabilidad Temporal**: Puedes inferir cuándo se creó un registro basándote en el UUID
4. **Compatibilidad**: Sigue siendo un UUID estándar (RFC 4122)

## Implementación en el Proyecto

### Dependencia

Se utiliza la librería `uuid-creator` de f4b6a3:clea

```xml
<dependency>
    <groupId>com.github.f4b6a3</groupId>
    <artifactId>uuid-creator</artifactId>
    <version>5.3.7</version>
</dependency>
```

### Clase Utilitaria

Se creó `UuidGenerator` en el dominio para centralizar la generación:

```java
UuidGenerator.generate() // Genera un UUID v7
```

### Cambios Realizados

1. **Domain Layer**:
   - `Product.java`: Usa `UuidGenerator.generate()` en lugar de `UUID.randomUUID()`
   - `UuidGenerator.java`: Nueva clase utilitaria para generar UUIDs v7

2. **Infrastructure Layer**:
   - `ProductEntity.java`: Removido `@GeneratedValue` ya que el UUID se genera en el dominio
   - El ID se asigna antes de persistir desde el dominio

## Ejemplo de UUID v7

```
018d8f3a-7c3e-7000-8000-000000000001
```

Los primeros 48 bits contienen el timestamp, lo que permite ordenamiento.

## Comparación con UUID v4

| Característica | UUID v4 (Random) | UUID v7 (Time-Ordered) |
|----------------|-----------------|----------------------|
| Ordenamiento | No ordenado | Ordenado cronológicamente |
| Timestamp | No incluye | Incluye timestamp |
| Rendimiento DB | Índices menos eficientes | Índices más eficientes |
| Colisiones | Muy raras | Muy raras |
| Privacidad | Mayor | Menor (timestamp visible) |

## Uso en el Código

### Generar un nuevo UUID v7

```java
// En el dominio
Product product = Product.create(...); // Genera UUID v7 automáticamente

// Manualmente
UUID id = UuidGenerator.generate();
```

### Verificar versión

```java
boolean isV7 = UuidGenerator.isVersion7(uuid);
```

## Migración de Datos Existentes

Si tienes datos existentes con UUID v4, puedes:

1. **Opción 1**: Mantener UUIDs v4 existentes y usar v7 solo para nuevos registros
2. **Opción 2**: Migrar todos los UUIDs a v7 (requiere script de migración)

## Notas Importantes

- Los UUIDs v7 son **ordenables cronológicamente**
- El timestamp está en los primeros 48 bits
- Compatible con PostgreSQL UUID type
- No requiere cambios en la base de datos (solo en la generación)

## Referencias

- [RFC 4122 - UUIDs](https://www.rfc-editor.org/rfc/rfc4122)
- [UUID Creator Library](https://github.com/f4b6a3/uuid-creator)
- [UUID v7 Draft](https://datatracker.ietf.org/doc/html/draft-ietf-uuidrev-rfc4122bis)
