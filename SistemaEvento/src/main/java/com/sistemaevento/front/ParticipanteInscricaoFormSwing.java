package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Participante;

public class ParticipanteInscricaoFormSwing {

    private final ParticipanteService participanteService = new ParticipanteService();
    private final EventoService eventoService = new EventoService();

    private List<Evento> eventosCarregados = new ArrayList<>();

    public JPanel criarPainel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

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
            String nomePalestrante = evento.getPalestranteNome() != null ? evento.getPalestranteNome() : "Convidado Especial";

            String bloco = String.format(
                "%s\nDescrição: %s\nData: %s\nLocal: %s\nCapacidade: %d\nPalestrante: %s",
                evento.getNome(),
                evento.getDescricao(),
                evento.getData(),
                evento.getLocal(),
                evento.getCapacidade(),
                nomePalestrante
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

        JButton cadastrarButton = new JButton("Cadastrar e Inscrever");
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

            if (!(email.contains("@") && email.contains(".com"))) {
                JOptionPane.showMessageDialog(panel, "Email inválido! Deve conter '@' e '.com'.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Participante participante = new Participante();
            participante.setNome(nome);
            participante.setEmail(email);
            int idGerado = participanteService.adicionarRetornandoId(participante);

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

        panel.add(criarLinhaAlinhada("Nome:", nomeField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Email:", emailField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(eventoLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(cadastrarButton);

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
