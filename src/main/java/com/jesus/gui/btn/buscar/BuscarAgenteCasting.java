package com.jesus.gui.btn.buscar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaAgenteCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BuscarAgenteCasting extends JFrame {
    private JTextField idField, nombreField, apellidoField, ciudadField, telefonoField;
    private VistaAgenteCasting padre;

    public BuscarAgenteCasting(VistaAgenteCasting padre) {
        this.padre = padre;
        setTitle("Buscar Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        idField = crearCampo("ID:");
        nombreField = crearCampo("Nombre:");
        apellidoField = crearCampo("Apellido:");
        ciudadField = crearCampo("Ciudad:");
        telefonoField = crearCampo("Teléfono:");

        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> buscarAgenteCasting());
        add(buscarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose());
        add(cancelarBtn);
    }

    private JTextField crearCampo(String label) {
        add(new JLabel(label));
        JTextField field = new JTextField();
        add(field);
        return field;
    }

    private void buscarAgenteCasting() {
        StringBuilder query = new StringBuilder("SELECT * FROM AgenteCasting WHERE 1=1");
        if (!idField.getText().trim().isEmpty()) {
            query.append(" AND id = ").append(idField.getText().trim());
        }
        if (!nombreField.getText().trim().isEmpty()) {
            query.append(" AND nombre LIKE '%").append(nombreField.getText().trim()).append("%'");
        }
        if (!apellidoField.getText().trim().isEmpty()) {
            query.append(" AND apellido LIKE '%").append(apellidoField.getText().trim()).append("%'");
        }
        if (!ciudadField.getText().trim().isEmpty()) {
            query.append(" AND ciudad LIKE '%").append(ciudadField.getText().trim()).append("%'");
        }
        if (!telefonoField.getText().trim().isEmpty()) {
            query.append(" AND telefono LIKE '%").append(telefonoField.getText().trim()).append("%'");
        }

        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            padre.getModel().setRowCount(0); // Limpiar tabla
            while (rs.next()) {
                padre.getModel().addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("ciudad"),
                        rs.getString("telefono")
                });
            }
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }
}

