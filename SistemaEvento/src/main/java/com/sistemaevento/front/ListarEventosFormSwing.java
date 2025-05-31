package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.service.PalestranteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Participante;
import com.sistemaevento.tabelas.Palestrante;

public class ListarEventosFormSwing {

    private final EventoService eventoService = new EventoService();
    private final PalestranteService palestranteService = new PalestranteService();
    private final ParticipanteService participanteService = new ParticipanteService();
    private List<Evento> eventosCarregados = new ArrayList<>();

    public JPanel criarPainel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel tituloLabel = new JLabel("Eventos Disponíveis:");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        DefaultListModel<String> listaEventosModel = new DefaultListModel<>();
        JList<String> listaEventos = new JList<>(listaEventosModel);
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventos.setVisibleRowCount(10);

        listaEventos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JTextArea renderer = new JTextArea(value.toString());
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
        scrollPane.setPreferredSize(new Dimension(500, 250));

        // Estilo padrão para os botões
        Dimension tamanhoBotao = new Dimension(200, 50);
        Border paddingBorda = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(1, 1, 1, 1)
        );

        // Botão de Atualizar
        JButton atualizarButton = new JButton("Atualizar Evento");
        atualizarButton.setPreferredSize(tamanhoBotao);
        atualizarButton.setBorder(paddingBorda);
        atualizarButton.addActionListener(e -> {
            boolean autorizado = AutenticacaoDialog.pedirCredenciais(frame, palestranteService);
            if (autorizado) {
                int index = listaEventos.getSelectedIndex();
                if (index == -1 || index >= eventosCarregados.size()) {
                    JOptionPane.showMessageDialog(panel,
                            "Selecione um evento da lista para editar.",
                            "Nenhum evento selecionado", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Evento eventoSelecionado = eventosCarregados.get(index);
                new EditarEventoFormSwing().abrirFormulario(eventoSelecionado);
            }
        });

        // Botão de Excluir
        JButton excluirButton = new JButton("Excluir Evento");
        excluirButton.setPreferredSize(tamanhoBotao);
        excluirButton.setBorder(paddingBorda);
        excluirButton.addActionListener(e -> {
            boolean autorizado = AutenticacaoDialog.pedirCredenciais(frame, palestranteService);
            if (autorizado) {
                int index = listaEventos.getSelectedIndex();
                if (index == -1 || index >= eventosCarregados.size()) {
                    JOptionPane.showMessageDialog(panel,
                            "Selecione um evento da lista para excluir.",
                            "Nenhum evento selecionado", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int confirmacao = JOptionPane.showConfirmDialog(panel,
                        "Esse evento também será apagado do banco de dados permanentemente.\nDeseja continuar?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    Evento eventoSelecionado = eventosCarregados.get(index);
                    eventoService.excluir(eventoSelecionado.getId());
                    JOptionPane.showMessageDialog(panel, "Evento excluído com sucesso.");
                    eventosCarregados = eventoService.listarComPalestrante();
                    atualizarLista(listaEventosModel, eventosCarregados);
                }
            }
        });

        // Botão de Ver Participantes
        JButton verParticipantesButton = new JButton("Ver Participantes");
        verParticipantesButton.setPreferredSize(tamanhoBotao);
        verParticipantesButton.setBorder(paddingBorda);
        verParticipantesButton.addActionListener(e -> {
            int index = listaEventos.getSelectedIndex();
            if (index == -1 || index >= eventosCarregados.size()) {
                JOptionPane.showMessageDialog(panel,
                        "Selecione um evento da lista para ver os participantes.",
                        "Nenhum evento selecionado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Evento eventoSelecionado = eventosCarregados.get(index);
            List<Participante> participantes = participanteService.listarParticipantesPorEvento(eventoSelecionado.getId());

            // Cria nova janela para mostrar os participantes
            JPanel participantesPanel = new JPanel();
            participantesPanel.setLayout(new BoxLayout(participantesPanel, BoxLayout.Y_AXIS));
            participantesPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            if (participantes.isEmpty()) {
                JLabel vazio = new JLabel("Nenhum participante ainda");
                vazio.setFont(new Font("Arial", Font.ITALIC, 14));
                vazio.setAlignmentX(Component.CENTER_ALIGNMENT);

                participantesPanel.add(Box.createVerticalGlue());
                participantesPanel.add(vazio);
                participantesPanel.add(Box.createVerticalGlue());
            } else {
                int count = 1;
                for (Participante participante : participantes) {
                    String emailCensurado = participanteService.obterEmailCensurado(participante.getId());

                    participantesPanel.add(new JLabel(count + "- Nome: " + participante.getNome()));
                    participantesPanel.add(new JLabel("     E-mail: " + emailCensurado));
                    participantesPanel.add(Box.createVerticalStrut(10));
                    count++;
                }
            }

            JFrame participantesFrame = new JFrame("Participantes do Evento");
            participantesFrame.setSize(500, 300);
            participantesFrame.setLocationRelativeTo(frame);
            participantesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            participantesFrame.add(participantesPanel);
            participantesFrame.setVisible(true);

        });

        // Carregar eventos ao abrir
        eventosCarregados = eventoService.listarComPalestrante();
        atualizarLista(listaEventosModel, eventosCarregados);

        // Painel de botões com espaçamento
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Adiciona espaçamento entre os botões
        botoesPanel.add(atualizarButton);
        botoesPanel.add(excluirButton);
        botoesPanel.add(verParticipantesButton);

        panel.add(tituloLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(botoesPanel);

        return panel;
    }

    private void atualizarLista(DefaultListModel<String> model, List<Evento> eventos) {
        model.clear();
        if (eventos.isEmpty()) {
            model.addElement("Nenhum evento encontrado.");
        } else {
            for (Evento evento : eventos) {
                // Verifica se PalestrantesIds é null, caso seja, inicializa como uma lista vazia
                List<Integer> palestrantesIds = evento.getPalestrantesIds() == null ? new ArrayList<>() : evento.getPalestrantesIds();

                String nomePalestrante = evento.getPalestranteNome();
                if (nomePalestrante == null || nomePalestrante.isBlank()) {
                    nomePalestrante = "Convidado Especial";
                }
                // Default caso não haja palestrante
                if (!palestrantesIds.isEmpty()) {
                    int palestranteId = palestrantesIds.get(0);  // Considerando que apenas um palestrante é vinculado
                    System.out.println("Buscando palestrante com ID: " + palestranteId);
                    Palestrante palestrante = palestranteService.buscarPalestrantePorId(palestranteId);
                    
                    if (palestrante != null) {
                        System.out.println("Palestrante encontrado: " + palestrante.getNome());
                        nomePalestrante = palestrante.getNome();  // Atribuindo o nome do palestrante
                    } else {
                        System.out.println("Palestrante não encontrado para o evento: " + evento.getNome());
                        nomePalestrante = "Palestrante não encontrado";  // Caso o palestrante não exista no banco
                    }
                }

                String bloco = String.format(
                        "%s\nDescrição: %s\nData: %s\nLocal: %s\nCapacidade: %d\nPalestrante: %s",
                        evento.getNome(),
                        evento.getDescricao(),
                        evento.getData(),
                        evento.getLocal(),
                        evento.getCapacidade(),
                        nomePalestrante
                );
                model.addElement(bloco);
            }
        }
    }
}