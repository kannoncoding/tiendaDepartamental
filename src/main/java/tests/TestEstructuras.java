package tests;

import modelo.Articulo;
import modelo.Departamento;
import estructura.PilaDepartamentos;
import estructura.ColaArticulos;

public class TestEstructuras {

    public static void main(String[] args) {
        // Prueba de la pila de departamentos
        System.out.println("--- Prueba de PilaDepartamentos ---");
        PilaDepartamentos pila = new PilaDepartamentos();
        Departamento dep1 = new Departamento(1, "Electrónica");
        Departamento dep2 = new Departamento(2, "Ropa");
        System.out.println("Pila vacía? " + pila.isEmpty());
        pila.push(dep1);
        pila.push(dep2);
        System.out.println("Tamaño de la pila: " + pila.size());
        System.out.println("Peek: " + pila.peek());
        System.out.println("Pop: " + pila.pop());
        System.out.println("Tamaño después de pop: " + pila.size());
        pila.mostrarTodos();

        // Prueba de la cola de artículos
        System.out.println("\n--- Prueba de ColaArticulos ---");
        ColaArticulos cola = new ColaArticulos();
        Articulo art1 = new Articulo(1, "Laptop", "Electrónica");
        Articulo art2 = new Articulo(2, "Celular", "Electrónica");
        Articulo art3 = new Articulo(3, "Tablet", "Electrónica");
        System.out.println("Cola vacía? " + cola.isEmpty());
        cola.encolar(art1);
        cola.encolar(art2);
        cola.encolar(art3);
        System.out.println("Tamaño de la cola: " + cola.size());
        System.out.println("Peek: " + cola.peek());
        System.out.println("Desencolar: " + cola.desencolar());
        System.out.println("Tamaño después de desencolar: " + cola.size());
        cola.mostrarTodos();

        // Prueba de límites de tamaño
        System.out.println("\n--- Prueba de límite de la pila ---");
        for (int i = 0; i < 20; i++) {
            pila.push(new Departamento(100 + i, "Dep" + i));
        }
        System.out.println("¿Está llena la pila? " + pila.isFull());
        System.out.println("Intento de insertar en pila llena: " + pila.push(new Departamento(999, "Extra")));

        System.out.println("\n--- Prueba de límite de la cola ---");
        for (int i = 0; i < 20; i++) {
            cola.encolar(new Articulo(200 + i, "Art" + i, "Cat"));
        }
        System.out.println("¿Está llena la cola? " + cola.isFull());
        System.out.println("Intento de insertar en cola llena: " + cola.encolar(new Articulo(999, "Extra", "Cat")));
    }
}
