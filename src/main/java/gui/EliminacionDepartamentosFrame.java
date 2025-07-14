package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Departamento;
import util.DatosGlobales;

public class EliminacionDepartamentosFrame extends JFrame {

    private JTable tablaDepartamentos;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminarDepto;

    public EliminacionDepartamentosFrame() {
        setTitle("Eliminación de Departamentos");
        setSize(540, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tabla de departamentos
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Artículos en Cola"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDepartamentos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaDepartamentos);

        // Botón eliminar
        btnEliminarDepto = new JButton("Eliminar último departamento registrado");

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnEliminarDepto);

        add(new JLabel("Departamentos registrados (se eliminará el último):", JLabel.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        // Listener del botón
        btnEliminarDepto.addActionListener(e -> eliminarUltimoDepartamento());

        cargarTablaDepartamentos();
    }

    // Refresca la tabla de departamentos
    private void cargarTablaDepartamentos() {
        modeloTabla.setRowCount(0);
        Departamento[] departamentos = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        for (Departamento dep : departamentos) {
            if (dep != null) {
                int cantidadArticulos = (dep.getArticulos() != null) ? dep.getArticulos().size() : 0;
                modeloTabla.addRow(new Object[]{dep.getId(), dep.getNombre(), cantidadArticulos});
            }
        }
    }

    // Lógica para eliminar el último departamento registrado (LIFO)
    private void eliminarUltimoDepartamento() {
        if (util.DatosGlobales.pilaDepartamentos.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay departamentos para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Departamento[] departamentos = util.DatosGlobales.pilaDepartamentos.getDepartamentos();
        int ultimoIdx = util.DatosGlobales.pilaDepartamentos.size() - 1;
        Departamento ultimo = departamentos[ultimoIdx];

        // Validar que no tenga artículos asociados
        if (ultimo.getArticulos() != null && !ultimo.getArticulos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se puede eliminar el departamento. Debe estar vacío de artículos.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Eliminar (pop)
        util.DatosGlobales.pilaDepartamentos.pop();
        cargarTablaDepartamentos();
        JOptionPane.showMessageDialog(this,
            "Departamento eliminado correctamente.",
            "Eliminado", JOptionPane.INFORMATION_MESSAGE);
    }
}
