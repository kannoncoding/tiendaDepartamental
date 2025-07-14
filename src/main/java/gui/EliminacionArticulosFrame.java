package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Departamento;
import modelo.Articulo;
import util.DatosGlobales;

public class EliminacionArticulosFrame extends JFrame {

    private JTable tablaDepartamentos;
    private DefaultTableModel modeloTablaDepartamentos;
    private JTable tablaArticulos;
    private DefaultTableModel modeloTablaArticulos;
    private JButton btnEliminarArticulo;

    public EliminacionArticulosFrame() {
        setTitle("Eliminación de Artículos");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---- Panel departamentos ----
        modeloTablaDepartamentos = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDepartamentos = new JTable(modeloTablaDepartamentos);
        JScrollPane scrollDepartamentos = new JScrollPane(tablaDepartamentos);
        scrollDepartamentos.setPreferredSize(new Dimension(330, 120));

        JPanel panelDepartamentos = new JPanel(new BorderLayout());
        panelDepartamentos.add(new JLabel("Seleccione un departamento:"), BorderLayout.NORTH);
        panelDepartamentos.add(scrollDepartamentos, BorderLayout.CENTER);

        // ---- Panel artículos ----
        modeloTablaArticulos = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaArticulos = new JTable(modeloTablaArticulos);
        JScrollPane scrollArticulos = new JScrollPane(tablaArticulos);

        JPanel panelArticulos = new JPanel(new BorderLayout());
        panelArticulos.add(new JLabel("Artículos del departamento seleccionado (FIFO):"), BorderLayout.NORTH);
        panelArticulos.add(scrollArticulos, BorderLayout.CENTER);

        // ---- Botón eliminar ----
        btnEliminarArticulo = new JButton("Eliminar artículo (FIFO)");
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.add(btnEliminarArticulo);

        // ---- Organización general ----
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.add(panelDepartamentos, BorderLayout.NORTH);
        panelIzq.add(panelBoton, BorderLayout.SOUTH);

        add(panelIzq, BorderLayout.WEST);
        add(panelArticulos, BorderLayout.CENTER);

        // ---- Listeners ----
        tablaDepartamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarArticulosDepartamento();
            }
        });

        btnEliminarArticulo.addActionListener(e -> eliminarPrimerArticulo());

        // Inicializa las tablas
        cargarTablaDepartamentos();
        mostrarArticulosDepartamento();
    }

    // Cargar departamentos en tabla
    private void cargarTablaDepartamentos() {
        modeloTablaDepartamentos.setRowCount(0);
        Departamento[] departamentos = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento dep : departamentos) {
            if (dep != null) {
                modeloTablaDepartamentos.addRow(new Object[]{dep.getId(), dep.getNombre()});
            }
        }
    }

    // Muestra artículos del departamento seleccionado
    private void mostrarArticulosDepartamento() {
        modeloTablaArticulos.setRowCount(0);

        int fila = tablaDepartamentos.getSelectedRow();
        if (fila == -1) return;

        Departamento dep = buscarDepartamentoPorFila(fila);
        if (dep != null && dep.getArticulos() != null) {
            Articulo[] articulos = dep.getArticulos().getArticulos();
            for (Articulo art : articulos) {
                if (art != null) {
                    modeloTablaArticulos.addRow(new Object[]{art.getId(), art.getNombre(), art.getCategoria()});
                }
            }
        }
    }

    // Buscar departamento según la fila seleccionada
    private Departamento buscarDepartamentoPorFila(int fila) {
        if (fila < 0) return null;
        int id = (int) modeloTablaDepartamentos.getValueAt(fila, 0);
        Departamento[] deps = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento d : deps) {
            if (d != null && d.getId() == id) return d;
        }
        return null;
    }

    // Eliminar el primer artículo de la cola (FIFO)
    private void eliminarPrimerArticulo() {
        int fila = tablaDepartamentos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un departamento.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Departamento dep = buscarDepartamentoPorFila(fila);
        if (dep == null || dep.getArticulos() == null || dep.getArticulos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay artículos para eliminar en este departamento.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Articulo eliminado = dep.getArticulos().desencolar();
        if (eliminado != null) {
            mostrarArticulosDepartamento(); // Refresca la tabla
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el artículo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
