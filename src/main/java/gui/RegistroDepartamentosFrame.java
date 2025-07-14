package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import util.DatosGlobales;
import modelo.Departamento;
import util.GeneradorID;

public class RegistroDepartamentosFrame extends JFrame {

    private JTextField txtNombre;
    private JButton btnAgregar;
    private JTable tablaDepartamentos;
    private DefaultTableModel modeloTabla;

    public RegistroDepartamentosFrame() {
        setTitle("Registro de Departamentos");
        setSize(520, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // PANEL DE ENTRADA USANDO GridBagLayout
        JPanel panelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8); // margen entre componentes

        JLabel lblNombre = new JLabel("Nombre del Departamento:");
        txtNombre = new JTextField(20);
        btnAgregar = new JButton("Agregar Departamento");

        // Etiqueta
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panelEntrada.add(lblNombre, gbc);

        // Campo de texto
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelEntrada.add(txtNombre, gbc);

        // Botón (ocupa dos columnas)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        panelEntrada.add(btnAgregar, gbc);

        add(panelEntrada, BorderLayout.NORTH);

        // TABLA (JTable) PARA DEPARTAMENTOS
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla solo lectura
            }
        };
        tablaDepartamentos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaDepartamentos);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón para agregar departamento
        btnAgregar.addActionListener(e -> agregarDepartamento());

        // Al abrir la ventana, cargar la tabla con los departamentos actuales
        cargarTablaDepartamentos();
    }

    // Método para agregar un departamento con validación
    private void agregarDepartamento() {
    String nombre = txtNombre.getText().trim();
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Validar que NO exista otro departamento con el mismo nombre (ignorando mayúsculas/minúsculas)
    Departamento[] existentes = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
    for (Departamento dep : existentes) {
        if (dep != null && dep.getNombre().equalsIgnoreCase(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe un departamento con ese nombre.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    // Crear el departamento con ID autogenerado
    int nuevoId = util.GeneradorID.siguienteIdDepartamento();
    Departamento dep = new Departamento(nuevoId, nombre);

    // Intentar agregarlo a la pila global
    boolean exito = util.DatosGlobales.pilaDepartamentos.push(dep);

    if (exito) {
        txtNombre.setText(""); // Limpiar campo
        cargarTablaDepartamentos(); // Refrescar tabla
    } else {
        JOptionPane.showMessageDialog(this, "No se puede agregar más departamentos (pila llena).", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Método para cargar/refrescar la tabla desde la pila
    private void cargarTablaDepartamentos() {
        modeloTabla.setRowCount(0); // Limpiar tabla

        Departamento[] arreglo = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento dep : arreglo) {
            if (dep != null) {
                modeloTabla.addRow(new Object[]{dep.getId(), dep.getNombre()});
            }
        }
    }
}
