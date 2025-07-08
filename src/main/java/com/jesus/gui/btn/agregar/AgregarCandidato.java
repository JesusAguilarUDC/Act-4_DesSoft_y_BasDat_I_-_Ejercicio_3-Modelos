package com.jesus.gui.btn.agregar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCandidato;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarCandidato extends JFrame {
    private JTextField idField, nombreField, apellidoField, ciudadField, telefonoField,
            fechaNacimientoField, fotografiaField, sexoField, alturaField, colorPeloField, colorOjosField, especialidadField, añosExpField;

    private VistaCandidato padre;

    public AgregarCandidato(VistaCandidato padre) {
        this.padre = padre;

        setTitle("Agregar Candidato");
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
        especialidadField = crearCampo("Especialidad");
        añosExpField = crearCampo("Años de Experiencia:");

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarCandidato());
        add(guardarBtn);

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

    private void guardarCandidato() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            String ciudad = ciudadField.getText().trim();
            String telefono = telefonoField.getText().trim();
            String fechaNacimiento = fechaNacimientoField.getText().trim();
            String fotografia = fotografiaField.getText().trim();
            String sexo = sexoField.getText().trim();
            double altura = Double.parseDouble(alturaField.getText().trim());
            String colorPelo = colorPeloField.getText().trim();
            String colorOjos = colorOjosField.getText().trim();
            String especialidad = especialidadField.getText().trim();
            int añosExp = Integer.parseInt(añosExpField.getText().trim());

            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO Candidato VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.setString(3, apellido);
                ps.setString(4, ciudad);
                ps.setString(5, telefono);
                ps.setString(6, fechaNacimiento);
                ps.setString(7, fotografia);
                ps.setString(8, sexo);
                ps.setDouble(9, altura);
                ps.setString(10, colorPelo);
                ps.setString(11, colorOjos);
                ps.setString(12, especialidad);
                ps.setInt(13, añosExp);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Candidato guardado correctamente.");
                padre.cargarCandidatos();
                dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verifica los campos. Error: " + e.getMessage());
        }
    }
}

