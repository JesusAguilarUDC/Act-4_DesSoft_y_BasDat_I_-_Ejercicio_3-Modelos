package com.jesus.gui.btn.editar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaAgenteCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarAgenteCasting extends JFrame {
    private JTextField nombreField, apellidoField, ciudadField, telefonoField;
    private final VistaAgenteCasting padre;
    private final int id;

    public EditarAgenteCasting(VistaAgenteCasting padre, int id, String nombre, String apellido, String ciudad, String telefono) {
        this.padre = padre;
        this.id = id;

        setTitle("Editar Agente");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Nombre:")); nombreField = new JTextField(nombre); add(nombreField);
        add(new JLabel("Apellido:")); apellidoField = new JTextField(apellido); add(apellidoField);
        add(new JLabel("Ciudad:")); ciudadField = new JTextField(ciudad); add(ciudadField);
        add(new JLabel("Teléfono:")); telefonoField = new JTextField(telefono); add(telefonoField);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarCambios()); add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose()); add(cancelarBtn);
    }

    private void guardarCambios() {
        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE AgenteCasting SET nombre=?, apellido=?, ciudad=?, telefono=? WHERE id=?")) {
            ps.setString(1, nombreField.getText().trim());
            ps.setString(2, apellidoField.getText().trim());
            ps.setString(3, ciudadField.getText().trim());
            ps.setString(4, telefonoField.getText().trim());
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Agente actualizado correctamente.");
            padre.cargarAgentes();
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }
}

