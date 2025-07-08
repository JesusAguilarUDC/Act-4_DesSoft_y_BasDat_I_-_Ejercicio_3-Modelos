package com.jesus.gui.btn.editar;

import com.jesus.connection.MySQLConexion;
import com.jesus.gui.btn.vista.VistaCasting;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditarCasting extends JFrame {
    private JTextField nombreField, descripcionField, idClienteField, idAgenteField, idCandidatoField;
    private final VistaCasting padre;
    private final int id;

    public EditarCasting(VistaCasting padre, int id, String nombre, String descripcion, int idCliente, int idAgente, int idCandidato) {
        this.padre = padre;
        this.id = id;

        setTitle("Editar Casting");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Nombre:"));
        nombreField = new JTextField(nombre);
        add(nombreField);

        add(new JLabel("Descripción:"));
        descripcionField = new JTextField(descripcion);
        add(descripcionField);

        add(new JLabel("ID Cliente:"));
        idClienteField = new JTextField(String.valueOf(idCliente));
        add(idClienteField);

        add(new JLabel("ID Agente:"));
        idAgenteField = new JTextField(String.valueOf(idAgente));
        add(idAgenteField);

        add(new JLabel("ID Candidato:"));
        idCandidatoField = new JTextField(String.valueOf(idCandidato));
        add(idCandidatoField);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.addActionListener(e -> guardarCambios()); add(guardarBtn);

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.addActionListener(e -> dispose()); add(cancelarBtn);
    }

    private void guardarCambios() {
        try (Connection conn = MySQLConexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE Casting SET nombre=?, descripcion=?, id_cliente=?, id_agente=?, id_candidato=? WHERE id=?")) {
            ps.setString(1, nombreField.getText().trim());
            ps.setString(2, descripcionField.getText().trim());
            ps.setInt(3, Integer.parseInt(idClienteField.getText().trim()));
            ps.setInt(4, Integer.parseInt(idAgenteField.getText().trim()));
            ps.setInt(5, Integer.parseInt(idCandidatoField.getText().trim()));
            ps.setInt(6, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Casting actualizado correctamente.");
            padre.cargarCastings();
            dispose();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }
}
