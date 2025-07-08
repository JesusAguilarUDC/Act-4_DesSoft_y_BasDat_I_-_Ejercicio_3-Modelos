package com.jesus.gui.btn.vista;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.GUI;
import com.jesus.gui.btn.agregar.AgregarCandidato;
import com.jesus.gui.btn.buscar.BuscarCandidato;
import com.jesus.gui.btn.editar.EditarCandidato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VistaCandidato extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public VistaCandidato() {
        setTitle("Candidatos");
        setSize(1100, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Apellido", "Ciudad", "Teléfono", "Fecha Nacimiento", "Fotografía",
            "Sexo", "Altura", "Color Pelo", "Color Ojos", "Especialidad", "Años Exp"
        }, 0);
        table = new JTable(model);

        cargarCandidatos();

        JButton buscarBtn = new JButton("Buscar");
        JButton agregarBtn = new JButton("Agregar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton editarBtn = new JButton("Editar");
        JButton volverBtn = new JButton("Volver");
        buscarBtn.addActionListener(e -> new BuscarCandidato(this).setVisible(true));
        agregarBtn.addActionListener(e -> new AgregarCandidato(this).setVisible(true));
        eliminarBtn.addActionListener(e -> eliminarCandidato());
        editarBtn.addActionListener(e -> editarCandidato());
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

    public void cargarCandidatos() {
        model.setRowCount(0);
        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Candidato")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("ciudad"),
                        rs.getString("telefono"),
                        rs.getDate("fechaNacimiento"),
                        rs.getBlob("fotografia"),
                        rs.getString("sexo"),
                        rs.getDouble("altura"),
                        rs.getString("colorPelo"),
                        rs.getString("colorOjos"),
                        rs.getString("especialidad"),
                        rs.getInt("añosExp")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar candidatos: " + e.getMessage());
        }
    }

    private void eliminarCandidato() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un candidato para eliminar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este candidato?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM Candidato WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                cargarCandidatos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getModel() {
        return model;
    }

    private void editarCandidato() {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un candidato para editar.");
            return;
        }

        int id = (int) model.getValueAt(fila, 0);
        String nombre = (String) model.getValueAt(fila, 1);
        String apellido = (String) model.getValueAt(fila, 2);
        String ciudad = (String) model.getValueAt(fila, 3);
        String telefono = (String) model.getValueAt(fila, 4);
        String fechaNacimiento = (String) model.getValueAt(fila, 5);
        String fotografia = (String) model.getValueAt(fila, 6);
        String sexo = (String) model.getValueAt(fila, 7);
        double altura = (double) model.getValueAt(fila, 8);
        String colorPelo = (String) model.getValueAt(fila, 9);
        String colorOjos = (String) model.getValueAt(fila, 10);
        String especialidad = (String) model.getValueAt(fila, 11);
        int añosExp = (int) model.getValueAt(fila, 12);

        new EditarCandidato(this, id, nombre, apellido, ciudad, telefono,
                fechaNacimiento, fotografia, sexo, altura, colorPelo, colorOjos, especialidad, añosExp).setVisible(true);
    }
}
