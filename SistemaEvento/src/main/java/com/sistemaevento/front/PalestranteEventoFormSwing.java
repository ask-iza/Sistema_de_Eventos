package com.sistemaevento.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.PalestranteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Palestrante;

public class PalestranteEventoFormSwing {

    private final PalestranteService palestranteService = new PalestranteService();
    private final EventoService eventoService = new EventoService();

    public JPanel criarPainel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Título Dados do Participante
        JLabel tituloParticipante = new JLabel("Dados do Participante");
        tituloParticipante.setFont(new Font("Arial", Font.BOLD, 16));
        tituloParticipante.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel Participante
        JPanel participantePanel = new JPanel();
        participantePanel.setLayout(new BoxLayout(participantePanel, BoxLayout.Y_AXIS));
        participantePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomePalestranteField = new JTextField();
        JTextField curriculoField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField emailField = new JTextField();

        participantePanel.add(criarLinhaAlinhada("Nome do Palestrante:", nomePalestranteField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("Currículo:", curriculoField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("Área de Atuação:", areaField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("E-mail:", emailField));

        // Título Dados do Evento
        JLabel tituloEvento = new JLabel("Dados do Evento");
        tituloEvento.setFont(new Font("Arial", Font.BOLD, 16));
        tituloEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloEvento.setBorder(new EmptyBorder(20, 0, 0, 0)); // espaçamento superior

        // Painel Evento
        JPanel eventoPanel = new JPanel();
        eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.Y_AXIS));
        eventoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomeEventoField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField localField = new JTextField();
        JTextField capacidadeField = new JTextField();

        eventoPanel.add(criarLinhaAlinhada("Nome do Evento:", nomeEventoField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Descrição:", descricaoField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Data (YYYY-MM-DD):", dataField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Local:", localField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Capacidade:", capacidadeField));

        JButton cadastrarButton = new JButton("Cadastrar Evento e Palestrante");
        cadastrarButton.setPreferredSize(new Dimension(200, 50));
        cadastrarButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cadastrarButton.addActionListener(e -> {
            try {
                Palestrante palestrante = new Palestrante();
                palestrante.setNome(nomePalestranteField.getText().trim());
                palestrante.setCurriculo(curriculoField.getText().trim());
                palestrante.setArea_atuacao(areaField.getText().trim());
                palestrante.setEmail(emailField.getText().trim());

                int idPalestrante = palestranteService.cadastrarRetornandoId(palestrante);
                if (idPalestrante <= 0) {
                    JOptionPane.showMessageDialog(panel, "Erro ao cadastrar palestrante.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Evento evento = new Evento();
                evento.setNome(nomeEventoField.getText().trim());
                evento.setDescricao(descricaoField.getText().trim());
                evento.setData(dataField.getText().trim());
                evento.setLocal(localField.getText().trim());
                evento.setCapacidade(Integer.parseInt(capacidadeField.getText().trim()));
                evento.setPalestrantesIds(Arrays.asList(idPalestrante));

                int idEvento = eventoService.salvarRetornandoId(evento);
                if (idEvento > 0) {
                    eventoService.vincularPalestrante(idEvento, idPalestrante);
                    JOptionPane.showMessageDialog(panel, "Evento e Palestrante cadastrados com sucesso!\nEvento ID: " + idEvento, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    // Limpar campos
                    nomePalestranteField.setText("");
                    curriculoField.setText("");
                    areaField.setText("");
                    emailField.setText("");
                    nomeEventoField.setText("");
                    descricaoField.setText("");
                    dataField.setText("");
                    localField.setText("");
                    capacidadeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Erro ao salvar evento.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                boolean todosPreenchidos =
                    !nomePalestranteField.getText().trim().isEmpty() &&
                    !curriculoField.getText().trim().isEmpty() &&
                    !areaField.getText().trim().isEmpty() &&
                    !emailField.getText().trim().isEmpty() &&
                    !nomeEventoField.getText().trim().isEmpty() &&
                    !descricaoField.getText().trim().isEmpty() &&
                    !dataField.getText().trim().isEmpty() &&
                    !localField.getText().trim().isEmpty() &&
                    !capacidadeField.getText().trim().isEmpty();

                if (todosPreenchidos) {
                    JOptionPane.showMessageDialog(panel,
                        "Ocorreu um erro mesmo com todos os campos preenchidos.\nVerifique os dados e tente novamente.",
                        "Erro inesperado", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel,
                        "Preencha todos os campos corretamente antes de cadastrar.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                }

                ex.printStackTrace();
            }
        });

        panel.add(tituloParticipante);
        panel.add(Box.createVerticalStrut(10));
        panel.add(participantePanel);
        panel.add(tituloEvento);
        panel.add(Box.createVerticalStrut(10));
        panel.add(eventoPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(cadastrarButton);

        return panel;
    }

    private JPanel criarLinhaAlinhada(String labelText, JComponent inputField) {
        JPanel linha = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        linha.add(label, BorderLayout.WEST);
        linha.add(inputField, BorderLayout.CENTER);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return linha;
    }
}
