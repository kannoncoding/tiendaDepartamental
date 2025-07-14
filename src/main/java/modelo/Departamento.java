package modelo;

import estructura.ColaArticulos;

/**
 * Clase que representa un Departamento de la tienda.
 * Cada departamento funciona como un elemento de la pila principal.
 * Contiene una cola de artículos relacionados.
 */
public class Departamento {
    private final int id;                     // ID único del departamento
    private final String nombre;              // Nombre del departamento
    private ColaArticulos articulos;    // Cola de artículos del departamento

    /**
     * Constructor principal
     * @param id ID único del departamento
     * @param nombre Nombre del departamento
     */
    public Departamento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.articulos = new ColaArticulos(); // Inicializa la cola vacía al crear el departamento
    }

    /**
     * Devuelve el ID del departamento
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del departamento
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la cola de artículos asociada al departamento
     * @return ColaArticulos
     */
    public ColaArticulos getArticulos() {
        return articulos;
    }

    /**
     * Permite cambiar la cola de artículos asociada al departamento
     * @param articulos Nueva cola de artículos
     */
    public void setArticulos(ColaArticulos articulos) {
        this.articulos = articulos;
    }
    
    @Override
public String toString() {
    return "Departamento [id=" + id + ", nombre=" + nombre + "]";
}

}
