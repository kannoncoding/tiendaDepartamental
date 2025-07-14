package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    // Botones de navegación
    private JButton btnRegistroDepartamentos;
    private JButton btnRegistroArticulos;
    private JButton btnEliminacionArticulos;
    private JButton btnTrasladoArticulos;
    private JButton btnEliminacionDepartamentos;

    public VentanaPrincipal() {
        setTitle("Gestión de Tienda por Departamentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Centrar ventana

        // Layout y panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Inicialización de botones
        btnRegistroDepartamentos = new JButton("Registro de Departamentos");
        btnRegistroArticulos = new JButton("Registro de Artículos");
        btnEliminacionArticulos = new JButton("Eliminación de Artículos");
        btnTrasladoArticulos = new JButton("Traslado de Artículos");
        btnEliminacionDepartamentos = new JButton("Eliminación de Departamentos");

        // Agregar botones al panel
        panel.add(btnRegistroDepartamentos);
        panel.add(btnRegistroArticulos);
        panel.add(btnEliminacionArticulos);
        panel.add(btnTrasladoArticulos);
        panel.add(btnEliminacionDepartamentos);

        // Agregar panel al JFrame
        add(panel);

        // Configurar eventos de los botones
        configurarEventos();
    }

    private void configurarEventos() {
        btnRegistroDepartamentos.addActionListener(e -> {
            RegistroDepartamentosFrame ventana = new RegistroDepartamentosFrame();
            ventana.setVisible(true);
        });

        btnRegistroArticulos.addActionListener(e -> {
            RegistroArticulosFrame ventana = new RegistroArticulosFrame();
            ventana.setVisible(true);
        });

        btnEliminacionArticulos.addActionListener(e -> {
            EliminacionArticulosFrame ventana = new EliminacionArticulosFrame();
            ventana.setVisible(true);
        });

        btnTrasladoArticulos.addActionListener(e -> {
            TrasladoArticulosFrame ventana = new TrasladoArticulosFrame();
            ventana.setVisible(true);
        });

        btnEliminacionDepartamentos.addActionListener(e -> {
            EliminacionDepartamentosFrame ventana = new EliminacionDepartamentosFrame();
            ventana.setVisible(true);
        });
    }

    // Método main para lanzar la app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}