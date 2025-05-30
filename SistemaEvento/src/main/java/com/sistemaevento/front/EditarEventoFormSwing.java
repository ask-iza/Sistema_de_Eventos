package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.tabelas.Evento;

public class EditarEventoFormSwing {

    private final EventoService eventoService = new EventoService();

    public void abrirFormulario(Evento evento) {
        JFrame frame = new JFrame("Editar Evento");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Campos preenchidos com os dados do evento
        JTextField nomeField = new JTextField(evento.getNome());
        JTextField descricaoField = new JTextField(evento.getDescricao());
        JTextField dataField = new JTextField(evento.getData());
        JTextField localField = new JTextField(evento.getLocal());
        JTextField capacidadeField = new JTextField(String.valueOf(evento.getCapacidade()));

        panel.add(criarLinhaAlinhada("Nome:", nomeField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Descrição:", descricaoField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Data:", dataField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Local:", localField));
        panel.add(Box.createVerticalStrut(10));
        panel.add(criarLinhaAlinhada("Capacidade:", capacidadeField));
        panel.add(Box.createVerticalStrut(20));

        JButton salvarButton = new JButton("Salvar Alterações");
        salvarButton.setPreferredSize(new Dimension(200, 50));
        salvarButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        salvarButton.addActionListener(e -> {
            try {
                evento.setNome(nomeField.getText().trim());
                evento.setDescricao(descricaoField.getText().trim());
                evento.setData(dataField.getText().trim());
                evento.setLocal(localField.getText().trim());
                evento.setCapacidade(Integer.parseInt(capacidadeField.getText().trim()));

                eventoService.atualizar(evento); // método esperado no serviço
                JOptionPane.showMessageDialog(frame, "Evento atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(salvarButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel criarLinhaAlinhada(String label, JComponent campo) {
        JPanel linha = new JPanel(new BorderLayout(10, 0));
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(100, 25));
        linha.add(jLabel, BorderLayout.WEST);
        linha.add(campo, BorderLayout.CENTER);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return linha;
    }
}
