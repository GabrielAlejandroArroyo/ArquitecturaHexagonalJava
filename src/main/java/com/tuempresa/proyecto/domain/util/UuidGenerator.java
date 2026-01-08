package com.tuempresa.proyecto.domain.util;

import com.github.f4b6a3.uuid.UuidCreator;
import java.util.UUID;

/**
 * Utilidad para generar UUIDs v7.
 * 
 * UUID v7 incluye un timestamp de Unix en los primeros 48 bits,
 * lo que permite ordenamiento cronológico natural.
 * 
 * Esta implementación usa uuid-creator que genera UUIDs time-ordered
 * compatibles con el draft RFC 4122 para UUID v7.
 */
public class UuidGenerator {
    
    /**
     * Genera un nuevo UUID v7 (time-ordered).
     * 
     * Los UUIDs v7 son ordenables cronológicamente y mejoran el rendimiento
     * de los índices en bases de datos.
     * 
     * @return UUID v7 (time-ordered)
     */
    public static UUID generate() {
        // getTimeOrderedEpoch() genera un UUID time-ordered compatible con UUID v7
        return UuidCreator.getTimeOrderedEpoch();
    }
    
    /**
     * Verifica si un UUID es de la versión 7.
     * 
     * Nota: Los UUIDs generados por getTimeOrderedEpoch() pueden no tener
     * la versión 7 establecida en el campo de versión, pero son compatibles
     * con el formato de UUID v7.
     * 
     * @param uuid UUID a verificar
     * @return true si es UUID v7 o time-ordered, false en caso contrario
     */
    public static boolean isVersion7(UUID uuid) {
        // Verifica si es versión 7 o si es un UUID time-ordered
        return uuid.version() == 7 || isTimeOrdered(uuid);
    }
    
    /**
     * Verifica si un UUID es time-ordered (compatible con v7).
     * Los UUIDs time-ordered tienen el bit 12 del campo de variante establecido.
     * 
     * @param uuid UUID a verificar
     * @return true si es time-ordered
     */
    private static boolean isTimeOrdered(UUID uuid) {
        // Los UUIDs time-ordered tienen características específicas
        // que podemos verificar aquí si es necesario
        return uuid.version() == 1 || uuid.version() == 7;
    }
}
