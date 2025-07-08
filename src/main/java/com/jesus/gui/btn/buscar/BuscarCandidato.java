package com.jesus.gui.btn.buscar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCandidato;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BuscarCandidato extends JFrame {
    private JTextField idField, nombreField, apellidoField, ciudadField, telefonoField, fechaNacimientoField, fotografiaField, sexoField, alturaField, colorPeloField, colorOjosField, especialidadField, añosExpField;
    private VistaCandidato padre;

    public BuscarCandidato(VistaCandidato padre) {
        this.padre = padre;
        setTitle("Buscar Candidato");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        idField = crearCampo("ID:");
        nombreField = crearCampo("Nombre:");
        apellidoField = crearCampo("Apellido:");
        ciudadField = crearCampo("Ciudad:");
        telefonoField = crearCampo("Teléfono:");
        fechaNacimientoField = crearCampo("Fecha Nacimiento:");
        fotografiaField = crearCampo("Fotografía:");
        sexoField = crearCampo("Sexo:");
        alturaField = crearCampo("Altura:");
        colorPeloField = crearCampo("Color de Pelo:");
        colorOjosField = crearCampo("Color de Ojos:");
        especialidadField = crearCampo("Especialidad:");
        añosExpField = crearCampo("Años de Experiencia:");

        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> buscarCandidato());
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

    private void buscarCandidato() {
        StringBuilder query = new StringBuilder("SELECT * FROM Candidato WHERE 1=1");
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

        if (!fechaNacimientoField.getText().trim().isEmpty()) {
            query.append(" AND fechaNacimiento LIKE '%").append(fechaNacimientoField.getText().trim()).append("%'");
        }

        if (!fotografiaField.getText().trim().isEmpty()) {
            query.append(" AND fotografia LIKE '%").append(fotografiaField.getText().trim()).append("%'");
        }

        if (!sexoField.getText().trim().isEmpty()) {
            query.append(" AND sexo LIKE '%").append(sexoField.getText().trim()).append("%'");
        }

        if (!alturaField.getText().trim().isEmpty()) {
            query.append(" AND altura = ").append(alturaField.getText().trim());
        }

        if (!colorPeloField.getText().trim().isEmpty()) {
            query.append(" AND colorPelo LIKE '%").append(colorPeloField.getText().trim()).append("%'");
        }

        if (!colorOjosField.getText().trim().isEmpty()) {
            query.append(" AND colorOjos LIKE '%").append(colorOjosField.getText().trim()).append("%'");
        }

        if (!especialidadField.getText().trim().isEmpty()) {
            query.append(" AND especialidad LIKE '%").append(especialidadField.getText().trim()).append("%'");
        }

        if (!añosExpField.getText().trim().isEmpty()) {
            query.append(" AND añosExp = ").append(añosExpField.getText().trim());
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
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
        }
    }
}

