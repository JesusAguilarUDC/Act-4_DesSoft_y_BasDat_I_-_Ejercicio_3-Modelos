package com.jesus.gui.btn.vista;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.GUI;
import com.jesus.gui.btn.agregar.AgregarCasting;
import com.jesus.gui.btn.buscar.BuscarCasting;
import com.jesus.gui.btn.editar.EditarCasting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VistaCasting extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public VistaCasting() {
        setTitle("Castings");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción" , "ID Cliente", "ID Agente", "ID Candidato"}, 0);
        table = new JTable(model);
        cargarCastings();

        JButton buscarBtn = new JButton("Buscar");
        JButton agregarBtn = new JButton("Agregar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton editarBtn = new JButton("Editar");
        JButton volverBtn = new JButton("Volver");

        buscarBtn.addActionListener(e -> new BuscarCasting(this).setVisible(true));
        agregarBtn.addActionListener(e -> new AgregarCasting(this).setVisible(true));
        eliminarBtn.addActionListener(e -> eliminarCasting());
        editarBtn.addActionListener(e -> editarCasting());
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

    public void cargarCastings() {
        model.setRowCount(0);
        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Casting")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_agente"),
                        rs.getInt("id_candidato")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar castings: " + e.getMessage());
        }
    }

    public DefaultTableModel getModel() {
        return model;
    }

    private void eliminarCasting() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un casting para eliminar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este casting?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM Casting WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarCastings();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void editarCasting() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un casting para editar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        String nombre = (String) model.getValueAt(fila, 1);
        String descripcion = (String) model.getValueAt(fila, 2);
        int idCliente = (int) model.getValueAt(fila, 3);
        int idAgente = (int) model.getValueAt(fila, 4);
        int idCandidato = (int) model.getValueAt(fila, 5);

        new EditarCasting(this, id, nombre, descripcion, idCliente, idAgente, idCandidato).setVisible(true);
    }
}
