package com.jesus.gui.btn.editar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarCliente extends JFrame {
    private JTextField nombreField, apellidoField, ciudadField, telefonoField, accionField;
    private final VistaCliente padre;
    private final int id;

    public EditarCliente(VistaCliente padre, int id, String nombre, String apellido, String ciudad, String telefono, String accion) {
        this.padre = padre;
        this.id = id;

        setTitle("Editar Cliente");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Nombre:"));
        nombreField = new JTextField(nombre); add(nombreField);

        add(new JLabel("Apellido:"));
        apellidoField = new JTextField(apellido); add(apellidoField);

        add(new JLabel("Ciudad:"));
        ciudadField = new JTextField(ciudad); add(ciudadField);

        add(new JLabel("Teléfono:"));
        telefonoField = new JTextField(telefono); add(telefonoField);

        add(new JLabel("Accion:"));
        accionField = new JTextField(accion); add(accionField);

        JButton guardarBtn = new JButton("Guardar Cambios");
        guardarBtn.addActionListener(e -> guardarCambios()); add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose()); add(cancelarBtn);
    }

    private void guardarCambios() {
        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE Cliente SET nombre = ?, apellido = ?, ciudad = ?, telefono = ? WHERE id = ?")) {

            ps.setString(1, nombreField.getText().trim());
            ps.setString(2, apellidoField.getText().trim());
            ps.setString(3, ciudadField.getText().trim());
            ps.setString(4, telefonoField.getText().trim());
            ps.setInt(5, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            padre.cargarClientes();
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + ex.getMessage());
        }
    }
}
