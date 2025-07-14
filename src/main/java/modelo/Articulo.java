package modelo;

/**
 * Representa un artículo dentro de un departamento de la tienda.
 * Cada artículo tiene un ID único, un nombre y una categoría.
 */
public class Articulo {
    // Atributos
    private int id;
    private String nombre;
    private String categoria;

    // Constructor
    public Articulo(int id, String nombre, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    // Métodos getter
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    // Métodos setter 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // test
    @Override
    public String toString() {
        return "Articulo [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + "]";
    }
}
