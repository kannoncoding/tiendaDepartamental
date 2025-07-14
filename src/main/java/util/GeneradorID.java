package util;

/**
 * Clase de utilidad para generación automática de IDs consecutivos.
 * Se usa para Departamentos y Artículos.
 */
public class GeneradorID {
    // ID inicial para cada tipo (puedes cambiar si lo deseas)
    private static int idDepartamento = 1;
    private static int idArticulo = 1;

    /**
     * Retorna el siguiente ID único para un Departamento y lo incrementa para el próximo uso.
     * @return int ID consecutivo para Departamento
     */
    public static int siguienteIdDepartamento() {
        return idDepartamento++;
    }

    /**
     * Retorna el siguiente ID único para un Artículo y lo incrementa para el próximo uso.
     * @return int ID consecutivo para Artículo
     */
    public static int siguienteIdArticulo() {
        return idArticulo++;
    }

    /**
     * (Opcional) Reinicia los contadores de ID. Útil solo para pruebas o reseteos completos.
     */
    public static void reiniciar() {
        idDepartamento = 1;
        idArticulo = 1;
    }
}
