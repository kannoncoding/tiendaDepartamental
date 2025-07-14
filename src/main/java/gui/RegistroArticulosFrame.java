package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import modelo.Departamento;
import modelo.Articulo;
import util.DatosGlobales;
import util.GeneradorID;

public class RegistroArticulosFrame extends JFrame {

    private JTable tablaDepartamentos;
    private DefaultTableModel modeloTablaDepartamentos;
    private JTable tablaArticulos;
    private DefaultTableModel modeloTablaArticulos;
    private JTextField txtNombreArticulo;
    private JComboBox<String> comboCategoria;
    private JButton btnAgregarArticulo;

    // Categorías disponibles
    private static final String[] CATEGORIAS = {
        "Ropa y accesorios",
        "Electrónica",
        "Hogar y muebles",
        "Belleza y cuidado personal",
        "Deportes y aire libre",
        "Juguetes y juegos",
        "Alimentos y bebidas"
    };

    public RegistroArticulosFrame() {
        setTitle("Registro de Artículos por Departamento");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---- Panel superior: tabla departamentos ----
        modeloTablaDepartamentos = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDepartamentos = new JTable(modeloTablaDepartamentos);
        JScrollPane scrollDepartamentos = new JScrollPane(tablaDepartamentos);
        scrollDepartamentos.setPreferredSize(new Dimension(350, 130));

        // ---- Panel central: tabla artículos ----
        modeloTablaArticulos = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaArticulos = new JTable(modeloTablaArticulos);
        JScrollPane scrollArticulos = new JScrollPane(tablaArticulos);

        // ---- Panel de registro de artículo ----
        JPanel panelAgregar = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panelAgregar.add(new JLabel("Nombre del Artículo:"), gbc);

        txtNombreArticulo = new JTextField(14);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelAgregar.add(txtNombreArticulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        panelAgregar.add(new JLabel("Categoría:"), gbc);

        comboCategoria = new JComboBox<>(CATEGORIAS);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelAgregar.add(comboCategoria, gbc);

        btnAgregarArticulo = new JButton("Agregar Artículo");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE;
        panelAgregar.add(btnAgregarArticulo, gbc);

        // ---- Layout general ----
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(new JLabel("Seleccione un departamento para registrar artículos:"), BorderLayout.NORTH);
        panelNorte.add(scrollDepartamentos, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelAgregar, BorderLayout.NORTH);
        panelCentro.add(new JLabel("Artículos registrados en el departamento seleccionado:"), BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.WEST);
        add(scrollArticulos, BorderLayout.CENTER);

        // ---- Listeners ----
        // Cuando seleccionas un departamento, mostrar artículos
        tablaDepartamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarArticulosSeleccionados();
            }
        });

        // Botón agregar artículo
        btnAgregarArticulo.addActionListener(e -> agregarArticuloADepartamento());

        // Inicializar tablas
        cargarTablaDepartamentos();
        mostrarArticulosSeleccionados();
    }

    // Carga la tabla de departamentos
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
    private void mostrarArticulosSeleccionados() {
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

    // Busca el objeto Departamento según la fila seleccionada en la tabla
    private Departamento buscarDepartamentoPorFila(int fila) {
        if (fila < 0) return null;
        int id = (int) modeloTablaDepartamentos.getValueAt(fila, 0);
        Departamento[] deps = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento d : deps) {
            if (d != null && d.getId() == id) return d;
        }
        return null;
    }

    // Acción para agregar artículo
    private void agregarArticuloADepartamento() {
        int fila = tablaDepartamentos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un departamento.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Departamento dep = buscarDepartamentoPorFila(fila);
        if (dep == null) return;

        String nombreArticulo = txtNombreArticulo.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();

        if (nombreArticulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del artículo no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar nombre duplicado en la cola del departamento
        Articulo[] articulos = dep.getArticulos().getArticulos();
        for (Articulo art : articulos) {
            if (art != null && art.getNombre().equalsIgnoreCase(nombreArticulo)) {
                JOptionPane.showMessageDialog(this, "Ya existe un artículo con ese nombre en este departamento.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Validar si la cola está llena
        if (dep.getArticulos().isFull()) {
            JOptionPane.showMessageDialog(this, "La cola de artículos del departamento está llena.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear y agregar artículo
        int nuevoId = util.GeneradorID.siguienteIdArticulo();
        Articulo art = new Articulo(nuevoId, nombreArticulo, categoria);
        boolean exito = dep.getArticulos().encolar(art);

        if (exito) {
            txtNombreArticulo.setText("");
            comboCategoria.setSelectedIndex(0);
            mostrarArticulosSeleccionados();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo agregar el artículo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
