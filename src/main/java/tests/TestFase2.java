package tests;

import modelo.Articulo;
import modelo.Departamento;
import estructura.PilaDepartamentos;
import estructura.ColaArticulos;
import util.GeneradorID;

/**
 * Pruebas unitarias de estructuras de datos y modelo, en modo texto.
 * No deben ser incluidas ni llamadas desde la GUI final.
 */
public class TestFase2 {
    public static void main(String[] args) {
        // Prueba de pila de departamentos
        PilaDepartamentos pila = new PilaDepartamentos();
        Departamento d1 = new Departamento(GeneradorID.siguienteIdDepartamento(), "Electrónica");
        Departamento d2 = new Departamento(GeneradorID.siguienteIdDepartamento(), "Ropa");
        assert pila.isEmpty() : "La pila debe estar vacía inicialmente";
        pila.push(d1);
        pila.push(d2);
        assert pila.size() == 2 : "La pila debe tener 2 elementos";
        assert pila.peek().getNombre().equals("Ropa") : "El tope debe ser 'Ropa'";
        Departamento eliminado = pila.pop();
        assert eliminado.getNombre().equals("Ropa") : "Se debe eliminar 'Ropa'";
        assert pila.size() == 1 : "Solo queda 1 elemento";

        // Prueba de cola de artículos
        ColaArticulos cola = new ColaArticulos();
        Articulo a1 = new Articulo(GeneradorID.siguienteIdArticulo(), "Laptop", "Electrónica");
        Articulo a2 = new Articulo(GeneradorID.siguienteIdArticulo(), "Celular", "Electrónica");
        Articulo a3 = new Articulo(GeneradorID.siguienteIdArticulo(), "Tablet", "Electrónica");
        assert cola.isEmpty() : "La cola debe estar vacía inicialmente";
        cola.encolar(a1);
        cola.encolar(a2);
        cola.encolar(a3);
        assert cola.size() == 3 : "La cola debe tener 3 elementos";
        assert cola.peek().getNombre().equals("Laptop") : "El primero debe ser 'Laptop'";
        Articulo desencolado = cola.desencolar();
        assert desencolado.getNombre().equals("Laptop") : "Debe desencolarse 'Laptop'";
        assert cola.size() == 2 : "Quedan 2 artículos en la cola";

        // Prueba GeneradorID
        int idDepto = GeneradorID.siguienteIdDepartamento();
        int idArt = GeneradorID.siguienteIdArticulo();
        assert idDepto > 0 : "ID de departamento debe ser positivo";
        assert idArt > 0 : "ID de artículo debe ser positivo";

        System.out.println("TODAS LAS PRUEBAS UNITARIAS PASARON CORRECTAMENTE.");
    }
}
