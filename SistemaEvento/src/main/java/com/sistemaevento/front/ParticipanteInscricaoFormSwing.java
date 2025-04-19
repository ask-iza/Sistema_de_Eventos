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
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();

        JLabel eventoLabel = new JLabel("Selecione eventos:");
        DefaultListModel<String> listaEventosModel = new DefaultListModel<>();
        List<Evento> eventos = eventoService.listarTodos();
        for (Evento evento : eventos) {
            listaEventosModel.addElement(evento.getNome());
        }

        JList<String> listaEventos = new JList<>(listaEventosModel);
        listaEventos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaEventos);

        JButton cadastrarButton = new JButton("Cadastrar e Inscrever");
        JLabel resultadoLabel = new JLabel();

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            List<String> eventosSelecionados = listaEventos.getSelectedValuesList();

            if (nome.isEmpty() || email.isEmpty() || eventosSelecionados.isEmpty()) {
                resultadoLabel.setText("Preencha tudo e selecione pelo menos um evento.");
                return;
            }

            Participante participante = new Participante();
            participante.setNome(nome);
            participante.setEmail(email);
            int idGerado = participanteService.adicionarRetornandoId(participante); // novo método

            if (idGerado > 0) {
                for (String nomeEvento : eventosSelecionados) {
                    Evento eventoSelecionado = eventoService.buscarPorNome(nomeEvento);
                    if (eventoSelecionado != null) {
                        participanteService.inscrever(idGerado, eventoSelecionado.getId());
                    }
                }
                resultadoLabel.setText("Cadastrado com ID: " + idGerado + " e inscrito! 🎉");
                nomeField.setText("");
                emailField.setText("");
                listaEventos.clearSelection();
            } else {
                resultadoLabel.setText("Erro ao cadastrar participante.");
            }
        });

        panel.add(new JLabel("Nome:")); panel.add(nomeField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(eventoLabel); panel.add(scrollPane);
        panel.add(cadastrarButton); panel.add(resultadoLabel);

        return panel;
    }
}