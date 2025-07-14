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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel de entrada (Norte)
        JPanel panelEntrada = new JPanel(new FlowLayout());
        panelEntrada.add(new JLabel("Nombre del Departamento:"));
        txtNombre = new JTextField(18);
        panelEntrada.add(txtNombre);

        btnAgregar = new JButton("Agregar Departamento");
        panelEntrada.add(btnAgregar);

        add(panelEntrada, BorderLayout.NORTH);

        // Tabla (Centro)
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla solo lectura
            }
        };
        tablaDepartamentos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaDepartamentos);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón
        btnAgregar.addActionListener(e -> agregarDepartamento());

        // Al abrir la ventana, cargar la tabla
        cargarTablaDepartamentos();
    }

    // Método para agregar departamento
    private void agregarDepartamento() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear nuevo departamento
        int nuevoId = GeneradorID.siguienteIdDepartamento();
        Departamento dep = new Departamento(nuevoId, nombre);

        // Intentar agregarlo a la pila global
        boolean exito = DatosGlobales.pilaDepartamentos.push(dep);

        if (exito) {
            txtNombre.setText(""); // Limpiar campo
            cargarTablaDepartamentos();
        } else {
            JOptionPane.showMessageDialog(this, "No se puede agregar más departamentos (pila llena).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar la tabla con los departamentos de la pila
    private void cargarTablaDepartamentos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        Departamento[] arreglo = DatosGlobales.pilaDepartamentos.getDepartamentos();

        for (Departamento dep : arreglo) {
            if (dep != null) {
                modeloTabla.addRow(new Object[]{dep.getId(), dep.getNombre()});
            }
        }
    }
}
