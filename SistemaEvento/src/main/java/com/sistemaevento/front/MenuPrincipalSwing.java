package com.sistemaevento.front;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MenuPrincipalSwing {

    public void exibirMenu() {
        JFrame frame = new JFrame("Sistema de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Participante", new ParticipanteInscricaoFormSwing().criarPainel());
        tabbedPane.addTab("Palestrante", new PalestranteEventoFormSwing().criarPainel());

        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipalSwing().exibirMenu());
    }
}