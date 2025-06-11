package com.sistemaevento.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Participante;

public class ParticipanteInscricaoFormSwing {

    private final ParticipanteService participanteService = new ParticipanteService();
    private final EventoService eventoService = new EventoService();
    private List<Evento> eventosCarregados;

    public JPanel criarPainel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JTextField nomeField = new JTextField();
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

        JLabel eventoLabel = new JLabel("Selecione eventos:");
        eventosCarregados = eventoService.listarComPalestrante();
        DefaultListModel<String> listaEventosModel = new DefaultListModel<>();

        for (Evento evento : eventosCarregados) {
            String bloco = String.format(
                "%s\nDescrição: %s\nData: %s\nLocal: %s\nCapacidade: %d\nPalestrante: %s",
                evento.getNome(),
                evento.getDescricao(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidade(),
                evento.getPalestranteNome() == null ? "Convidado Especial" : evento.getPalestranteNome()
            );
            listaEventosModel.addElement(bloco);
        }

        JList<String> listaEventos = new JList<>(listaEventosModel);
        listaEventos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaEventos.setVisibleRowCount(8);
        listaEventos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JTextArea renderer = new JTextArea(value);
            renderer.setWrapStyleWord(true);
            renderer.setLineWrap(true);
            renderer.setOpaque(true);
            renderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            renderer.setForeground(Color.BLACK);
            renderer.setBackground(isSelected ? new Color(200, 220, 255) : Color.WHITE);
            renderer.setFont(UIManager.getFont("Label.font"));
            return renderer;
        });

        JScrollPane scrollPane = new JScrollPane(listaEventos);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        JButton cadastrarButton = new JButton("Fazer Inscrição");
        cadastrarButton.setPreferredSize(new Dimension(200, 50));
        cadastrarButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            int[] indicesSelecionados = listaEventos.getSelectedIndices();

            if (nome.isEmpty() || email.isEmpty() || indicesSelecionados.length == 0) {
                JOptionPane.showMessageDialog(panel, "Preencha todos os campos e selecione pelo menos um evento.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!(email.contains("@"))) {
                JOptionPane.showMessageDialog(panel, "Email inválido! Deve conter '@'", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Participante participante = new Participante();
            participante.setNome(nome);
            participante.setEmail(email);
            int idGerado = participanteService.obterOuCadastrarParticipante(participante);

            if (idGerado > 0) {
                for (int idx : indicesSelecionados) {
                    Evento eventoSelecionado = eventosCarregados.get(idx);
                    participanteService.inscrever(idGerado, eventoSelecionado.getId());
                }
                JOptionPane.showMessageDialog(panel, "Cadastrado com sucesso! ID: " + idGerado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                nomeField.setText("");
                emailField.setText("");
                listaEventos.clearSelection();
            } else {
                JOptionPane.showMessageDialog(panel, "Erro ao cadastrar participante.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton minhasInscricoesButton = new JButton("Minhas Inscrições");
        minhasInscricoesButton.setPreferredSize(new Dimension(200, 50));
        minhasInscricoesButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        minhasInscricoesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        minhasInscricoesButton.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField emailLoginField = new JTextField();

            JPanel loginPanel = new JPanel(new GridLayout(2, 2));
            loginPanel.add(new JLabel("ID do Participante:"));
            loginPanel.add(idField);
            loginPanel.add(new JLabel("E-mail:"));
            loginPanel.add(emailLoginField);

            int result = JOptionPane.showConfirmDialog(null, loginPanel, "Acessar Minhas Inscrições", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String email = emailLoginField.getText().trim();
                    boolean credenciaisValidas = participanteService.verificarCredenciais(id, email);
                    if (!credenciaisValidas) {
                        JOptionPane.showMessageDialog(null, "Credenciais inválidas. Verifique ID e E-mail.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    new ConsultaInscricoesFormSwing().mostrarJanela(id, email);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(criarLinhaAlinhada("Nome:", nomeField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Email:", emailField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(eventoLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(cadastrarButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(minhasInscricoesButton);

        return panel;
    }

    private JPanel criarLinhaAlinhada(String labelText, JComponent campo) {
        JPanel linha = new JPanel(new BorderLayout(10, 0));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        linha.add(label, BorderLayout.WEST);
        linha.add(campo, BorderLayout.CENTER);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return linha;
    }
}
