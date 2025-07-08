package com.jesus.gui.btn.buscar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BuscarCasting extends JFrame {
    private JTextField idField, nombreField, descripcionField, id_clienteField, id_agenteField, id_candidatoField;
    private VistaCasting padre;

    public BuscarCasting(VistaCasting padre) {
        this.padre = padre;
        setTitle("Buscar Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        idField = crearCampo("ID:");
        nombreField = crearCampo("Nombre:");
        descripcionField = crearCampo("Descripción:");
        id_clienteField = crearCampo("DNI del Cliente:");
        id_agenteField = crearCampo("DNI del Agente:");
        id_candidatoField = crearCampo("DNI del Candidato");

        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> buscarCasting());
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

    private void buscarCasting() {
        StringBuilder query = new StringBuilder("SELECT * FROM Casting WHERE 1=1");
        if (!idField.getText().trim().isEmpty()) {
            query.append(" AND id = ").append(idField.getText().trim());
        }
        if (!nombreField.getText().trim().isEmpty()) {
            query.append(" AND nombre LIKE '%").append(nombreField.getText().trim()).append("%'");
        }
        if (!descripcionField.getText().trim().isEmpty()) {
            query.append(" AND descripcion LIKE '%").append(descripcionField.getText().trim()).append("%'");
        }
        if (!id_clienteField.getText().trim().isEmpty()) {
            query.append(" AND id_cliente LIKE '%").append(id_clienteField.getText().trim()).append("%'");
        }
        if (!id_agenteField.getText().trim().isEmpty()) {
            query.append(" AND id_agente LIKE '%").append(id_agenteField.getText().trim()).append("%'");
        }

        if (!id_candidatoField.getText().trim().isEmpty()) {
            query.append("AND id_candidato LIKE '%").append(id_candidatoField.getText().trim()).append("%'");
        }

        try (Connection conn = MySQLConexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            padre.getModel().setRowCount(0); // Limpiar tabla
            while (rs.next()) {
                padre.getModel().addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_agente"),
                        rs.getInt("id_candidato")
                });
            }
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }
}

