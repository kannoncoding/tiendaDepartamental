package tests;

import util.GeneradorID;

public class TestGeneradorID {
    public static void main(String[] args) {
        System.out.println("--- Prueba de GeneradorID ---");

        // Reiniciamos para pruebas limpias
        GeneradorID.reiniciar();

        // Prueba IDs para Departamentos
        System.out.println("ID Departamento esperado: 1, obtenido: " + GeneradorID.siguienteIdDepartamento());
        System.out.println("ID Departamento esperado: 2, obtenido: " + GeneradorID.siguienteIdDepartamento());
        System.out.println("ID Departamento esperado: 3, obtenido: " + GeneradorID.siguienteIdDepartamento());

        // Prueba IDs para Artículos
        System.out.println("ID Articulo esperado: 1, obtenido: " + GeneradorID.siguienteIdArticulo());
        System.out.println("ID Articulo esperado: 2, obtenido: " + GeneradorID.siguienteIdArticulo());

        // Prueba que siguen incrementando
        System.out.println("ID Departamento esperado: 4, obtenido: " + GeneradorID.siguienteIdDepartamento());
        System.out.println("ID Articulo esperado: 3, obtenido: " + GeneradorID.siguienteIdArticulo());

        // Prueba después de reiniciar
        GeneradorID.reiniciar();
        System.out.println("Tras reiniciar, ID Departamento esperado: 1, obtenido: " + GeneradorID.siguienteIdDepartamento());
        System.out.println("Tras reiniciar, ID Articulo esperado: 1, obtenido: " + GeneradorID.siguienteIdArticulo());
    }
}
