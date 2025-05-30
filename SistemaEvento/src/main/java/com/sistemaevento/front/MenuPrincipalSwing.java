package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrincipalSwing extends JFrame {

    public MenuPrincipalSwing() {
        setTitle("Sistema de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        // Painel principal com layout em coluna
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Tamanho padrão dos botões
        Dimension tamanhoBotao = new Dimension(200, 40);

        // Botões com ações
        painel.add(criarBotao("Participante", tamanhoBotao, e -> abrirJanela(new ParticipanteInscricaoFormSwing().criarPainel(), "Participante")));
        painel.add(Box.createVerticalStrut(15));
        painel.add(criarBotao("Palestrante", tamanhoBotao, e -> abrirJanela(new PalestranteEventoFormSwing().criarPainel(), "Palestrante")));
        painel.add(Box.createVerticalStrut(15));
        painel.add(criarBotao("Eventos disponíveis", tamanhoBotao, e -> abrirJanela(new ListarEventosFormSwing(true).criarPainel(), "Eventos disponíveis")));
        painel.add(Box.createVerticalStrut(15));

        add(painel);
    }

    private JButton criarBotao(String texto, Dimension tamanho, ActionListener listener) {
        JButton botao = new JButton(texto);
        botao.setMaximumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.addActionListener(listener);
        return botao;
    }

    private void abrirJanela(JPanel painel, String titulo) {
        JFrame novaJanela = new JFrame(titulo);
        novaJanela.setSize(600, 600);
        novaJanela.setLocationRelativeTo(null);
        novaJanela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        novaJanela.add(painel);
        novaJanela.setVisible(true);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipalSwing().setVisible(true));
    }

}
