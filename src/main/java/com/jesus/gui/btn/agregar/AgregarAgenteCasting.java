package com.jesus.gui.btn.agregar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaAgenteCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarAgenteCasting extends JFrame {
    private JTextField idField, nombreField, apellidoField, ciudadField, telefonoField;
    private VistaAgenteCasting padre;

    public AgregarAgenteCasting(VistaAgenteCasting padre) {
        this.padre = padre;

        setTitle("Agregar Agente de Casting");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        add(apellidoField);

        add(new JLabel("Ciudad:"));
        ciudadField = new JTextField();
        add(ciudadField);

        add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        add(telefonoField);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarAgente());
        add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        add(cancelarBtn);
    }

    private void guardarAgente() {
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String ciudad = ciudadField.getText().trim();
        String telefono = telefonoField.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || ciudad.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO AgenteCasting(id, nombre, apellido, ciudad, telefono) VALUES (?, ?, ?, ?, ?)")) {

            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, ciudad);
            ps.setString(5, telefono);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Agente guardado exitosamente.");
            padre.cargarAgentes();
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar agente: " + e.getMessage());
        }
    }
}
