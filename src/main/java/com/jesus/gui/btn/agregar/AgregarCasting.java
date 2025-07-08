package com.jesus.gui.btn.agregar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarCasting extends JFrame {
    private JTextField idField, nombreField, descripcionField, idClienteField, idAgenteField, idCandidatoField;
    private VistaCasting padre;

    public AgregarCasting(VistaCasting padre) {
        this.padre = padre;

        setTitle("Agregar Casting");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        idField = crearCampo("ID:");
        nombreField = crearCampo("Nombre:");
        descripcionField = crearCampo("Descripción:");
        idClienteField = crearCampo("ID Cliente:");
        idAgenteField = crearCampo("ID Agente:");
        idCandidatoField = crearCampo("ID Candidato:");

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarCasting());
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

    private void guardarCasting() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            int idCliente = Integer.parseInt(idClienteField.getText().trim());
            int idAgente = Integer.parseInt(idAgenteField.getText().trim());
            int idCandidato = Integer.parseInt(idCandidatoField.getText().trim());

            try (Connection conn = MySQLConexion.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO Casting VALUES (?, ?, ?, ?, ?, ?)")) {

                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.setString(3, descripcion);
                ps.setInt(4, idCliente);
                ps.setInt(5, idAgente);
                ps.setInt(6, idCandidato);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Casting guardado correctamente.");
                padre.cargarCastings();
                dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verifica los campos. Error: " + e.getMessage());
        }
    }
}
