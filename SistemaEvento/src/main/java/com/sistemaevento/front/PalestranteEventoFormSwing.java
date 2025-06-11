package com.sistemaevento.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.text.ParseException;
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

        JLabel tituloParticipante = new JLabel("Dados do Palestrante");
        tituloParticipante.setFont(new Font("Arial", Font.BOLD, 16));
        tituloParticipante.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel participantePanel = new JPanel();
        participantePanel.setLayout(new BoxLayout(participantePanel, BoxLayout.Y_AXIS));
        participantePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomePalestranteField = new JTextField();
        JTextField curriculoField = new JTextField();
        JTextField areaField = new JTextField();

        JTextField emailField = new JTextField();
        emailField.setText("exemplo@gmail.com");
        emailField.setForeground(Color.GRAY);

        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (emailField.getText().equals("exemplo@gmail.com")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("exemplo@gmail.com");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });

        participantePanel.add(criarLinhaAlinhada("Nome do Palestrante:", nomePalestranteField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("Currículo:", curriculoField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("Área de Atuação:", areaField));
        participantePanel.add(Box.createVerticalStrut(10));
        participantePanel.add(criarLinhaAlinhada("E-mail:", emailField));

        JLabel tituloEvento = new JLabel("Dados do Evento");
        tituloEvento.setFont(new Font("Arial", Font.BOLD, 16));
        tituloEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloEvento.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel eventoPanel = new JPanel();
        eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.Y_AXIS));
        eventoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomeEventoField = new JTextField();

        JTextField descricaoField = new JTextField();
        descricaoField.setText("Opcional");
        descricaoField.setForeground(Color.GRAY);

        descricaoField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (descricaoField.getText().equals("Opcional")) {
                    descricaoField.setText("");
                    descricaoField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (descricaoField.getText().isEmpty()) {
                    descricaoField.setText("Opcional");
                    descricaoField.setForeground(Color.GRAY);
                }
            }
        });

        final JFormattedTextField[] dataField = new JFormattedTextField[1];
        dataField[0] = new JFormattedTextField();

        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            dataField[0] = new JFormattedTextField(dataMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JTextField localField = new JTextField();
        JTextField capacidadeField = new JTextField();

        ((AbstractDocument) capacidadeField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        eventoPanel.add(criarLinhaAlinhada("Nome do Evento:", nomeEventoField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Descrição:", descricaoField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Data (DD-MM-AAAA):", dataField[0]));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Local:", localField));
        eventoPanel.add(Box.createVerticalStrut(10));
        eventoPanel.add(criarLinhaAlinhada("Capacidade:", capacidadeField));
        eventoPanel.add(Box.createVerticalStrut(10));

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setPreferredSize(new Dimension(200, 50));
        cadastrarButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cadastrarButton.addActionListener(e -> {
            try {
                String nomePalestrante = nomePalestranteField.getText().trim();
                String curriculo = curriculoField.getText().trim();
                String area = areaField.getText().trim();
                String email = emailField.getText().trim();
                String nomeEvento = nomeEventoField.getText().trim();
                String data = dataField[0].getText().trim();
                String local = localField.getText().trim();
                String capacidadeTexto = capacidadeField.getText().trim();
                String descricao = descricaoField.getText().trim(); // opcional

                if (nomePalestrante.isEmpty() || curriculo.isEmpty() || area.isEmpty()
                        || email.isEmpty() || nomeEvento.isEmpty() || data.isEmpty()
                        || local.isEmpty() || capacidadeTexto.isEmpty()) {

                    JOptionPane.showMessageDialog(panel,
                            "Todos os campos devem ser preenchidos.",
                            "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!(email.contains("@") && email.contains(".com"))) {
                    JOptionPane.showMessageDialog(panel, "Email inválido! Deve conter '@' e '.com'.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Palestrante palestrante = new Palestrante();
                palestrante.setNome(nomePalestrante);
                palestrante.setCurriculo(curriculo);
                palestrante.setArea_atuacao(area);
                palestrante.setEmail(email);

                int idPalestrante = palestranteService.obterOuCadastrarPalestrante(palestrante);
                if (idPalestrante <= 0) {
                    JOptionPane.showMessageDialog(panel, "Erro ao registrar palestrante.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Evento evento = new Evento();
                evento.setNome(nomeEvento);
                evento.setDescricao(descricao);
                evento.setData(data);
                evento.setLocal(local);
                evento.setCapacidade(Integer.parseInt(capacidadeTexto));
                evento.setPalestrantesIds(Arrays.asList(idPalestrante));

                int idEvento = eventoService.salvarRetornandoId(evento);
                if (idEvento > 0) {
                    eventoService.vincularPalestrante(idEvento, idPalestrante);
                    JOptionPane.showMessageDialog(panel, "Evento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(panel, "Palestrante cadastrado com ID: " + idPalestrante, "Identificador", JOptionPane.WARNING_MESSAGE);

                    nomePalestranteField.setText("");
                    curriculoField.setText("");
                    areaField.setText("");
                    emailField.setText("");
                    nomeEventoField.setText("");
                    descricaoField.setText("");
                    dataField[0].setText("");
                    localField.setText("");
                    capacidadeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Erro ao salvar evento.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(panel, "Capacidade deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
