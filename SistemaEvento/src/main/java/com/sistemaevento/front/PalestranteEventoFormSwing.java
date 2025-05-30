package com.sistemaevento.front;

import javax.swing.*;
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
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField nomePalestranteField = new JTextField();
        JTextField curriculoField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField nomeEventoField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField localField = new JTextField();
        JTextField capacidadeField = new JTextField();

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

        // Adiciona campos com alinhamento
        panel.add(criarLinhaAlinhada("Nome do Palestrante:", nomePalestranteField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Currículo:", curriculoField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Área de Atuação:", areaField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("E-mail:", emailField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Nome do Evento:", nomeEventoField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Descrição:", descricaoField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Data (YYYY-MM-DD):", dataField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Local:", localField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Capacidade:", capacidadeField));
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
