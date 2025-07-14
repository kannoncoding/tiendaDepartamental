package estructura;

import modelo.Departamento;

/**
 * Pila estática de Departamentos (máximo 20).
 * Implementa el comportamiento LIFO usando un arreglo.
 */
public class PilaDepartamentos {
    // Constante para el tamaño máximo de la pila
    private static final int MAX_SIZE = 20;

    // Atributos
    private final Departamento[] pila;
    private int tope;

    // Constructor: inicializa la pila vacía
    public PilaDepartamentos() {
        pila = new Departamento[MAX_SIZE];
        tope = -1;
    }

    // Agrega un departamento a la pila (push)
    public boolean push(Departamento d) {
        if (isFull()) {
            return false;
        }
        pila[++tope] = d;
        return true;
    }

    // Elimina y retorna el departamento en la cima de la pila (pop)
    public Departamento pop() {
        if (isEmpty()) {
            return null;
        }
        Departamento temp = pila[tope];
        pila[tope--] = null; // Limpia referencia
        return temp;
    }

    // Retorna el departamento en la cima de la pila sin eliminarlo (peek)
    public Departamento peek() {
        if (isEmpty()) {
            return null;
        }
        return pila[tope];
    }

    // Verifica si la pila está vacía
    public boolean isEmpty() {
        return tope == -1;
    }

    // Verifica si la pila está llena
    public boolean isFull() {
        return tope == MAX_SIZE - 1;
    }

    // Retorna la cantidad de elementos en la pila
    public int size() {
        return tope + 1;
    }

    // Devuelve un arreglo con los departamentos actuales (de abajo hacia arriba)
    public Departamento[] getDepartamentos() {
    Departamento[] departamentosActuales = new Departamento[size()];
    System.arraycopy(pila, 0, departamentosActuales, 0, size());
    return departamentosActuales;
}


    // Muestra todos los departamentos en la pila
    public void mostrarTodos() {
        if (isEmpty()) {
            System.out.println("La pila de departamentos está vacía.");
            return;
        }
        System.out.println("Departamentos en la pila (de abajo hacia arriba):");
        for (int i = 0; i <= tope; i++) {
            System.out.println(pila[i]);
        }
    }
}
