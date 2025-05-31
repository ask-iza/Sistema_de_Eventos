package com.sistemaevento.front;

import com.sistemaevento.service.ParticipanteService;
import com.sistemaevento.service.EventoService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Participante;
import com.sistemaevento.util.CertificadoUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaInscricoesFormSwing {

    private final ParticipanteService participanteService = new ParticipanteService();
    private final EventoService eventoService = new EventoService();

    public void mostrarJanela(int idParticipante, String email) {
        JFrame frame = new JFrame("Minhas Inscrições");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        Participante participante = participanteService.buscarPorId(idParticipante);
        List<Evento> eventos = eventoService.buscarEventosPorParticipante(idParticipante);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton editarPerfilBtn = new JButton("Editar Perfil");
        JButton emitirCertificadoBtn = new JButton("Emitir Certificado");
        JButton excluirInscricaoBtn = new JButton("Excluir Inscrição");

        editarPerfilBtn.addActionListener(e -> {
            JTextField nomeField = new JTextField(participante.getNome());
            JTextField emailField = new JTextField(participante.getEmail());

            JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            formPanel.add(new JLabel("Nome:"));
            formPanel.add(nomeField);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(
                frame, formPanel, "Editar Perfil", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText().trim();
                String novoEmail = emailField.getText().trim();

                if (novoNome.isEmpty() || novoEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos.");
                    return;
                }

                if (!novoEmail.contains("@") || !novoEmail.contains(".")) {
                    JOptionPane.showMessageDialog(frame, "Email inválido! Deve conter '@' e '.com'.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                participante.setNome(novoNome);
                participante.setEmail(novoEmail);
                participanteService.atualizar(participante);
                JOptionPane.showMessageDialog(frame, "Perfil atualizado com sucesso!");
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        if (eventos.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma inscrição encontrada.", SwingConstants.CENTER);
            vazio.setFont(new Font("Arial", Font.ITALIC, 14));
            mainPanel.add(vazio, BorderLayout.CENTER);
        } else {
            DefaultListModel<String> listaModel = new DefaultListModel<>();
            for (Evento evento : eventos) {
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
                listaModel.addElement(bloco);
            }

            JList<String> listaInscricoes = new JList<>(listaModel);
            listaInscricoes.setVisibleRowCount(8);
            listaInscricoes.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
                JTextArea renderer = new JTextArea(value.toString());
                renderer.setWrapStyleWord(true);
                renderer.setLineWrap(true);
                renderer.setOpaque(true);
                renderer.setFont(UIManager.getFont("Label.font"));
                renderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                renderer.setBackground(isSelected ? new Color(200, 220, 255) : Color.WHITE);
                renderer.setForeground(Color.BLACK);
                return renderer;
            });

            JScrollPane scrollPane = new JScrollPane(listaInscricoes);
            scrollPane.setPreferredSize(new Dimension(550, 250));
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Botão emitir certificado (com nome do palestrante)
            emitirCertificadoBtn.addActionListener(e -> {
                int index = listaInscricoes.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(frame, "Selecione um evento para emitir o certificado.");
                    return;
                }

                Evento evento = eventos.get(index);
                String nomePalestrante = evento.getPalestranteNome() != null ? evento.getPalestranteNome() : "Convidado Especial";

                CertificadoUtil.gerarCertificadoPDF(participante.getNome(), evento.getNome(), evento.getData(), nomePalestrante);
            });

            excluirInscricaoBtn.addActionListener(e -> {
                int index = listaInscricoes.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(frame, "Selecione uma inscrição para excluir.");
                    return;
                }

                Evento eventoSelecionado = eventos.get(index);
                boolean aindaInscrito = participanteService.jaInscritoNoEvento(idParticipante, eventoSelecionado.getId());

                if (!aindaInscrito) {
                    JOptionPane.showMessageDialog(frame, "Você já não está mais inscrito neste evento.");
                    return;
                }

                participanteService.removerInscricao(idParticipante, eventoSelecionado.getId());
                eventos.remove(index);
                listaModel.remove(index);
                JOptionPane.showMessageDialog(frame, "Inscrição removida com sucesso.");
            });
        }

        botoesPanel.add(editarPerfilBtn);
        botoesPanel.add(emitirCertificadoBtn);
        botoesPanel.add(excluirInscricaoBtn);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
