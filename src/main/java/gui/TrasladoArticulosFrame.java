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
        btnTrasladar = new JButton("Trasladar todos los artículos ➡️");

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
        btnTrasladar.addActionListener(e -> trasladarArticulos());

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

    private void trasladarArticulos() {
        // Validaciones
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
        if (origen.getArticulos() == null || origen.getArticulos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El departamento origen no tiene artículos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int disponiblesDestino = estructura.ColaArticulos.MAX_SIZE - destino.getArticulos().size();
        if (origen.getArticulos().size() > disponiblesDestino) {
            JOptionPane.showMessageDialog(this, "No hay espacio suficiente en el destino para todos los artículos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Trasladar todos los artículos de origen a destino
        while (!origen.getArticulos().isEmpty()) {
            Articulo art = origen.getArticulos().desencolar();
            destino.getArticulos().encolar(art);
        }
        mostrarArticulosOrigen();
        mostrarArticulosDestino();
        JOptionPane.showMessageDialog(this, "Artículos trasladados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
