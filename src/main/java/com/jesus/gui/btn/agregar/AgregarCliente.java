package com.jesus.gui.btn.agregar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarCliente extends JFrame {
    private JTextField idField, nombreField, apellidoField, ciudadField, telefonoField, accionField;
    private VistaCliente padre;

    public AgregarCliente(VistaCliente padre) {
        this.padre = padre;

        setTitle("Nuevo Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

        add(new JLabel("Accion"));
        accionField = new JTextField();
        add(accionField);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarCliente());
        add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        add(cancelarBtn);
    }

    private void guardarCliente() {
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String ciudad = ciudadField.getText().trim();
        String telefono = telefonoField.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || ciudad.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        String sql = "INSERT INTO Cliente(id, nombre, apellido, ciudad, telefono) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, ciudad);
            ps.setString(5, telefono);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            padre.cargarClientes();
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar cliente: " + e.getMessage());
        }
    }
}

