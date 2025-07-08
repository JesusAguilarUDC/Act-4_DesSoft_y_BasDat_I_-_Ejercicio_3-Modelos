package com.jesus;

import com.jesus.gui.GUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
