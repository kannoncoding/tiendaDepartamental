package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Departamento;
import modelo.Articulo;
import util.DatosGlobales;

public class TrasladoArticulosFrame extends JFrame {

    private JTable tablaOrigen;
    private DefaultTableModel modeloOrigen;
    private JTable tablaDestino;
    private DefaultTableModel modeloDestino;
    private JTable tablaArticulosOrigen;
    private DefaultTableModel modeloArticulosOrigen;
    private JTable tablaArticulosDestino;
    private DefaultTableModel modeloArticulosDestino;
    private JButton btnTrasladar;

    public TrasladoArticulosFrame() {
        setTitle("Traslado de Artículos entre Departamentos");
        setSize(1100, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tabla de departamentos origen
        modeloOrigen = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaOrigen = new JTable(modeloOrigen);
        JScrollPane scrollOrigen = new JScrollPane(tablaOrigen);
        scrollOrigen.setPreferredSize(new Dimension(240, 110));

        // Tabla de departamentos destino
        modeloDestino = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDestino = new JTable(modeloDestino);
        JScrollPane scrollDestino = new JScrollPane(tablaDestino);
        scrollDestino.setPreferredSize(new Dimension(240, 110));

        // Tabla de artículos origen
        modeloArticulosOrigen = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaArticulosOrigen = new JTable(modeloArticulosOrigen);
        JScrollPane scrollArticulosOrigen = new JScrollPane(tablaArticulosOrigen);
        scrollArticulosOrigen.setPreferredSize(new Dimension(300, 110));

        // Tabla de artículos destino
        modeloArticulosDestino = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaArticulosDestino = new JTable(modeloArticulosDestino);
        JScrollPane scrollArticulosDestino = new JScrollPane(tablaArticulosDestino);
        scrollArticulosDestino.setPreferredSize(new Dimension(300, 110));

        // Botón de traslado
        btnTrasladar = new JButton("Trasladar artículo seleccionado ➡️");

        // Paneles
        JPanel panelTablas = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTablas.add(crearPanel("Dept. Origen", scrollOrigen));
        panelTablas.add(crearPanel("Dept. Destino", scrollDestino));
        panelTablas.add(crearPanel("Artículos del Origen", scrollArticulosOrigen));
        panelTablas.add(crearPanel("Artículos del Destino", scrollArticulosDestino));

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnTrasladar);

        add(panelTablas, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        // Listeners
        tablaOrigen.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mostrarArticulosOrigen();
        });
        tablaDestino.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mostrarArticulosDestino();
        });
        btnTrasladar.addActionListener(e -> trasladarArticuloSeleccionado());

        // Inicializa tablas
        cargarTablasDepartamentos();
        mostrarArticulosOrigen();
        mostrarArticulosDestino();
    }

    private JPanel crearPanel(String titulo, JScrollPane scroll) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(titulo, JLabel.CENTER), BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private void cargarTablasDepartamentos() {
        modeloOrigen.setRowCount(0);
        modeloDestino.setRowCount(0);
        Departamento[] departamentos = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento dep : departamentos) {
            if (dep != null) {
                Object[] row = {dep.getId(), dep.getNombre()};
                modeloOrigen.addRow(row);
                modeloDestino.addRow(row);
            }
        }
    }

    private Departamento buscarDepartamento(DefaultTableModel modelo, JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return null;
        int id = (int) modelo.getValueAt(fila, 0);
        Departamento[] departamentos = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento d : departamentos) {
            if (d != null && d.getId() == id) return d;
        }
        return null;
    }

    private void mostrarArticulosOrigen() {
        modeloArticulosOrigen.setRowCount(0);
        Departamento dep = buscarDepartamento(modeloOrigen, tablaOrigen);
        if (dep != null && dep.getArticulos() != null) {
            Articulo[] articulos = dep.getArticulos().getArticulos();
            for (Articulo art : articulos) {
                if (art != null) {
                    modeloArticulosOrigen.addRow(new Object[]{art.getId(), art.getNombre(), art.getCategoria()});
                }
            }
        }
    }

    private void mostrarArticulosDestino() {
        modeloArticulosDestino.setRowCount(0);
        Departamento dep = buscarDepartamento(modeloDestino, tablaDestino);
        if (dep != null && dep.getArticulos() != null) {
            Articulo[] articulos = dep.getArticulos().getArticulos();
            for (Articulo art : articulos) {
                if (art != null) {
                    modeloArticulosDestino.addRow(new Object[]{art.getId(), art.getNombre(), art.getCategoria()});
                }
            }
        }
    }

    private void trasladarArticuloSeleccionado() {
        Departamento origen = buscarDepartamento(modeloOrigen, tablaOrigen);
        Departamento destino = buscarDepartamento(modeloDestino, tablaDestino);

        if (modeloOrigen.getRowCount() < 2) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos dos departamentos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (origen == null || destino == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar ambos departamentos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (origen.getId() == destino.getId()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar departamentos diferentes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int filaArticulo = tablaArticulosOrigen.getSelectedRow();
        if (filaArticulo == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un artículo en el departamento de origen.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (destino.getArticulos().isFull()) {
            JOptionPane.showMessageDialog(this, "El departamento destino no tiene espacio para más artículos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el artículo seleccionado por su ID
        int idArticulo = (int) modeloArticulosOrigen.getValueAt(filaArticulo, 0);
        Articulo articuloATrasladar = null;
        int idxEnCola = -1;

        Articulo[] articulosOrigen = origen.getArticulos().getArticulos();
        for (int i = 0; i < articulosOrigen.length; i++) {
            if (articulosOrigen[i] != null && articulosOrigen[i].getId() == idArticulo) {
                articuloATrasladar = articulosOrigen[i];
                idxEnCola = i;
                break;
            }
        }

        if (articuloATrasladar == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el artículo seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Eliminar el artículo del origen (no hay método directo, se debe desencolar en orden FIFO, así que reconstruimos la cola sin el artículo seleccionado)
        estructura.ColaArticulos colaOriginal = origen.getArticulos();
        estructura.ColaArticulos nuevaCola = new estructura.ColaArticulos();
        for (Articulo art : colaOriginal.getArticulos()) {
            if (art != null && art.getId() != idArticulo) {
                nuevaCola.encolar(art);
            }
        }
        origen.setArticulos(nuevaCola);

        // Agregar el artículo al destino
        destino.getArticulos().encolar(articuloATrasladar);

        mostrarArticulosOrigen();
        mostrarArticulosDestino();
        JOptionPane.showMessageDialog(this, "Artículo trasladado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
