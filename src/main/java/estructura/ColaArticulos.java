package estructura;

import modelo.Articulo;

/**
 * Cola estática de Articulos (FIFO) con arreglo de tamaño 20.
 */
public class ColaArticulos {
    // Constante para tamaño máximo de la cola
    public static final int MAX_SIZE = 20;

    // Atributos
    private Articulo[] cola;
    private int frente;
    private int fin;
    private int cantidad;

    // Constructor: Inicializa la cola vacía
    public ColaArticulos() {
        cola = new Articulo[MAX_SIZE];
        frente = 0;
        fin = -1;
        cantidad = 0;
    }

    // Encola un artículo al final de la cola
    public boolean encolar(Articulo a) {
        if (isFull()) {
            return false;
        }
        fin = (fin + 1) % MAX_SIZE;
        cola[fin] = a;
        cantidad++;
        return true;
    }

    // Desencola (elimina) el primer artículo y lo retorna
    public Articulo desencolar() {
        if (isEmpty()) {
            return null;
        }
        Articulo temp = cola[frente];
        cola[frente] = null; // Limpiar referencia
        frente = (frente + 1) % MAX_SIZE;
        cantidad--;
        return temp;
    }

    // Devuelve el primer artículo sin eliminarlo
    public Articulo peek() {
        if (isEmpty()) {
            return null;
        }
        return cola[frente];
    }

    // Verifica si la cola está vacía
    public boolean isEmpty() {
        return cantidad == 0;
    }

    // Verifica si la cola está llena
    public boolean isFull() {
        return cantidad == MAX_SIZE;
    }

    // Retorna la cantidad de artículos en la cola
    public int size() {
        return cantidad;
    }

    // Devuelve un arreglo con los artículos actuales (en orden FIFO)
    public Articulo[] getArticulos() {
        Articulo[] articulosActuales = new Articulo[cantidad];
        for (int i = 0; i < cantidad; i++) {
            articulosActuales[i] = cola[(frente + i) % MAX_SIZE];
        }
        return articulosActuales;
    }

    // Muestra todos los artículos en la cola
    public void mostrarTodos() {
        if (isEmpty()) {
            System.out.println("La cola de artículos está vacía.");
            return;
        }
        System.out.println("Artículos en la cola (FIFO):");
        for (int i = 0; i < cantidad; i++) {
            System.out.println(cola[(frente + i) % MAX_SIZE]);
        }
    }
}
