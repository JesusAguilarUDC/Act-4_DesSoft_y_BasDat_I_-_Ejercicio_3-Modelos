package com.jesus.gui;

import com.jesus.gui.btn.vista.VistaAgenteCasting;
import com.jesus.gui.btn.vista.VistaCandidato;
import com.jesus.gui.btn.vista.VistaCasting;
import com.jesus.gui.btn.vista.VistaCliente;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI() {
        setTitle("Sistema de Gestión");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton clienteBtn = new JButton("Clientes");
        JButton agenteBtn = new JButton("Agentes de Casting");
        JButton candidatoBtn = new JButton("Candidato");
        JButton castingBtn = new JButton("Casting");
        JButton salirBtn = new JButton("Salir");

        clienteBtn.addActionListener(e -> {
            new VistaCliente().setVisible(true);
            dispose();
        });

        agenteBtn.addActionListener(e -> {
            new VistaAgenteCasting().setVisible(true);
            dispose();
        });

        candidatoBtn.addActionListener(e -> {
            new VistaCandidato().setVisible(true);
            dispose();
        });

        castingBtn.addActionListener(e -> {
            new VistaCasting().setVisible(true);
            dispose();
        });

        salirBtn.addActionListener(e -> System.exit(0));

        add(clienteBtn);
        add(agenteBtn);
        add(candidatoBtn);
        add(castingBtn);
        add(salirBtn);
    }
}
