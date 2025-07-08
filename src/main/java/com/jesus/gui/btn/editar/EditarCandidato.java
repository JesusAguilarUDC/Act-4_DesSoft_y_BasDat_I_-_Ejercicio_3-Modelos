package com.jesus.gui.btn.editar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCandidato;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarCandidato extends JFrame {
    private JTextField nombreField, apellidoField, ciudadField, telefonoField,
            fechaNacimientoField, fotografiaField, sexoField, alturaField,
            colorPeloField, colorOjosField, especialidadField, añosExpField;
    private final VistaCandidato padre;
    private final int id;

    public EditarCandidato(VistaCandidato padre, int id, String nombre, String apellido, String ciudad,
                           String telefono, String fechaNacimiento, String fotografia, String sexo,
                           double altura, String colorPelo, String colorOjos, String especialidad, int añosExp) {
        this.padre = padre;
        this.id = id;

        setTitle("Editar Candidato");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(13, 2, 5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Nombre:"));
        nombreField = new JTextField(nombre);
        add(nombreField);

        add(new JLabel("Apellido:"));
        apellidoField = new JTextField(apellido);
        add(apellidoField);

        add(new JLabel("Ciudad:"));
        ciudadField = new JTextField(ciudad);
        add(ciudadField);

        add(new JLabel("Teléfono:"));
        telefonoField = new JTextField(telefono);
        add(telefonoField);

        add(new JLabel("Fecha Nacimiento:"));
        fechaNacimientoField = new JTextField(fechaNacimiento);
        add(fechaNacimientoField);

        add(new JLabel("Fotografía:"));
        fotografiaField = new JTextField(fotografia);
        add(fotografiaField);

        add(new JLabel("Sexo:"));
        sexoField = new JTextField(sexo);
        add(sexoField);

        add(new JLabel("Altura:"));
        alturaField = new JTextField(String.valueOf(altura));
        add(alturaField);

        add(new JLabel("Color Pelo:"));
        colorPeloField = new JTextField(colorPelo);
        add(colorPeloField);

        add(new JLabel("Color Ojos:"));
        colorOjosField = new JTextField(colorOjos);
        add(colorOjosField);

        add(new JLabel("Especialidad"));
        especialidadField = new JTextField(especialidad);
        add(especialidadField);

        add(new JLabel("Años de Experiencia:"));
        añosExpField = new JTextField(String.valueOf(añosExp));
        add(añosExpField);

        JButton guardarBtn = new JButton("Guardar Cambios");
        guardarBtn.addActionListener(e -> guardarCambios()); add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose()); add(cancelarBtn);
    }

    private void guardarCambios() {
        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE Candidato SET nombre=?, apellido=?, ciudad=?, telefono=?, fechaNacimiento=?, fotografia=?, sexo=?, altura=?, colorPelo=?, colorOjos=?, especialidad=?, añosExp=? WHERE id=?")) {

            ps.setString(1, nombreField.getText().trim());
            ps.setString(2, apellidoField.getText().trim());
            ps.setString(3, ciudadField.getText().trim());
            ps.setString(4, telefonoField.getText().trim());
            ps.setString(5, fechaNacimientoField.getText().trim());
            ps.setString(6, fotografiaField.getText().trim());
            ps.setString(7, sexoField.getText().trim());
            ps.setDouble(8, Double.parseDouble(alturaField.getText().trim()));
            ps.setString(9, colorPeloField.getText().trim());
            ps.setString(10, colorOjosField.getText().trim());
            ps.setString(11, especialidadField.getText().trim());
            ps.setInt(12, Integer.parseInt(añosExpField.getText().trim()));
            ps.setInt(13, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Candidato actualizado correctamente.");
            padre.cargarCandidatos();
            dispose();

        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }
}

