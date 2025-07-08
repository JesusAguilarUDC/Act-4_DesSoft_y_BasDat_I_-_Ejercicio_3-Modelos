package com.jesus.gui.btn.vista;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.GUI;
import com.jesus.gui.btn.agregar.AgregarCliente;
import com.jesus.gui.btn.buscar.BuscarCliente;
import com.jesus.gui.btn.editar.EditarCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VistaCliente extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public VistaCliente() {
        setTitle("Clientes");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Ciudad", "Teléfono", "Accion"}, 0);
        table = new JTable(model);
        cargarClientes();

        JButton buscarBtn = new JButton("Buscar");
        JButton agregarBtn = new JButton("Agregar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton editarBtn = new JButton("Editar");
        JButton volverBtn = new JButton("Volver");

        buscarBtn.addActionListener(e -> new BuscarCliente(this).setVisible(true));
        agregarBtn.addActionListener(e -> new AgregarCliente(this).setVisible(true));
        eliminarBtn.addActionListener(e -> eliminarCliente());
        editarBtn.addActionListener(e -> editarCliente());
        volverBtn.addActionListener(e -> {
            new GUI().setVisible(true);
            dispose(); // Cierra esta ventana
        });

        // Panel inferior con botones
        JPanel btnPanel = new JPanel();
        btnPanel.add(buscarBtn);
        btnPanel.add(agregarBtn);
        btnPanel.add(eliminarBtn);
        btnPanel.add(editarBtn);
        btnPanel.add(volverBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void cargarClientes() {
        model.setRowCount(0);
        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Cliente")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("ciudad"),
                        rs.getString("telefono"),
                        rs.getString("accion")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para eliminar.");
            return;
        }

        int id = (int) model.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este cliente?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM Cliente WHERE id = ?")) {

                ps.setInt(1, id);
                ps.executeUpdate();
                cargarClientes();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getModel() {
        return model;
    }

    private void editarCliente() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para editar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        String nombre = (String) model.getValueAt(fila, 1);
        String apellido = (String) model.getValueAt(fila, 2);
        String ciudad = (String) model.getValueAt(fila, 3);
        String telefono = (String) model.getValueAt(fila, 4);
        String accion = (String) model.getValueAt(fila, 5);

        new EditarCliente(this, id, nombre, apellido, ciudad, telefono, accion).setVisible(true);
    }
}
