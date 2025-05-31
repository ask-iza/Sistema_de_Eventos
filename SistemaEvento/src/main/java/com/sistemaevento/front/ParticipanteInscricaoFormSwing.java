package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Participante;

public class ParticipanteInscricaoFormSwing {

    private final ParticipanteService participanteService = new ParticipanteService();
    private final EventoService eventoService = new EventoService();

    public JPanel criarPainel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField nomeField = new JTextField();
        nomeField.setPreferredSize(new Dimension(200, 30));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));

        JLabel eventoLabel = new JLabel("Selecione eventos:");
        DefaultListModel<String> listaEventosModel = new DefaultListModel<>();
        List<Evento> eventos = eventoService.listarComPalestrante();
        for (Evento evento : eventos) {
            listaEventosModel.addElement(evento.getNome());
        }

        JList<String> listaEventos = new JList<>(listaEventosModel);
        listaEventos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaEventos);
        scrollPane.setPreferredSize(new Dimension(300, 80));

        JButton cadastrarButton = new JButton("Cadastrar e Inscrever");
        cadastrarButton.setPreferredSize(new Dimension(200, 50));
        cadastrarButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            List<String> eventosSelecionados = listaEventos.getSelectedValuesList();

            if (nome.isEmpty() || email.isEmpty() || eventosSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Preencha todos os campos e selecione pelo menos um evento.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validação de email mais rigorosa
            if (!(email.contains("@") && email.contains(".com"))) {
                JOptionPane.showMessageDialog(panel, "Email inválido! Deve conter '@' e '.com'.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Participante participante = new Participante();
            participante.setNome(nome);
            participante.setEmail(email);
            int idGerado = participanteService.adicionarRetornandoId(participante);

            if (idGerado > 0) {
                for (String nomeEvento : eventosSelecionados) {
                    Evento eventoSelecionado = eventoService.buscarPorNome(nomeEvento);
                    if (eventoSelecionado != null) {
                        participanteService.inscrever(idGerado, eventoSelecionado.getId());
                    }
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
