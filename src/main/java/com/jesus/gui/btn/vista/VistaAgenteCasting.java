package com.jesus.gui.btn.vista;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.GUI;
import com.jesus.gui.btn.agregar.AgregarAgenteCasting;
import com.jesus.gui.btn.buscar.BuscarAgenteCasting;
import com.jesus.gui.btn.editar.EditarAgenteCasting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VistaAgenteCasting extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public VistaAgenteCasting() {
        setTitle("Agentes de Casting");
        setSize(850, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Ciudad", "Teléfono"}, 0);
        table = new JTable(model);
        cargarAgentes();

        JButton buscarBtn = new JButton("Buscar");
        JButton agregarBtn = new JButton("Agregar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton editarBtn = new JButton("Editar");
        JButton volverBtn = new JButton("Volver");

        buscarBtn.addActionListener(e -> new BuscarAgenteCasting(this).setVisible(true));
        agregarBtn.addActionListener(e -> new AgregarAgenteCasting(this).setVisible(true));
        eliminarBtn.addActionListener(e -> eliminarAgente());
        editarBtn.addActionListener(e -> editarAgente());
        volverBtn.addActionListener(e -> {
            new GUI().setVisible(true);
            dispose();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(buscarBtn);
        btnPanel.add(agregarBtn);
        btnPanel.add(eliminarBtn);
        btnPanel.add(editarBtn);
        btnPanel.add(volverBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void cargarAgentes() {
        model.setRowCount(0);
        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM AgenteCasting")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("ciudad"),
                        rs.getString("telefono")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar agentes: " + e.getMessage());
        }
    }

    private void eliminarAgente() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un agente para eliminar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este agente?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM AgenteCasting WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarAgentes();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getModel() {
        return model;
    }

    private void editarAgente() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un agente para editar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        String nombre = (String) model.getValueAt(fila, 1);
        String apellido = (String) model.getValueAt(fila, 2);
        String ciudad = (String) model.getValueAt(fila, 3);
        String telefono = (String) model.getValueAt(fila, 4);

        new EditarAgenteCasting(this, id, nombre, apellido, ciudad, telefono).setVisible(true);
    }

}
