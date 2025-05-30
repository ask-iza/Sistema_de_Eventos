package com.sistemaevento.front;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.tabelas.Evento;

public class ListarEventosFormSwing {

    private final EventoService eventoService = new EventoService();
    private final boolean isPalestrante;
    private List<Evento> eventosCarregados = new ArrayList<>();

    public ListarEventosFormSwing(boolean isPalestrante) {
        this.isPalestrante = isPalestrante;
    }

    public JPanel criarPainel() {
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

        // Botão de Atualizar
        JButton atualizarButton = new JButton("Atualizar Evento");
        atualizarButton.setPreferredSize(new Dimension(200, 50));

        atualizarButton.addActionListener(e -> {
            if (!SessaoUsuario.isPalestrante()) {
                JOptionPane.showMessageDialog(panel, "Acesso negado. Apenas palestrantes podem editar eventos.", "Acesso negado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int index = listaEventos.getSelectedIndex();
            if (index == -1 || index >= eventosCarregados.size()) {
                JOptionPane.showMessageDialog(panel,
                        "Selecione um evento da lista para editar.",
                        "Nenhum evento selecionado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Evento eventoSelecionado = eventosCarregados.get(index);
            new EditarEventoFormSwing().abrirFormulario(eventoSelecionado);
        });

        // Botão de Excluir
        JButton excluirButton = new JButton("Excluir");
        excluirButton.setPreferredSize(new Dimension(200, 50));

        excluirButton.addActionListener(e -> {
            if (!SessaoUsuario.isPalestrante()) {
                JOptionPane.showMessageDialog(panel, "Acesso negado. Apenas palestrantes podem excluir eventos.", "Acesso negado", JOptionPane.WARNING_MESSAGE);
                return;
            }

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
                eventoService.excluir(eventoSelecionado.getId()); // Método esperado
                JOptionPane.showMessageDialog(panel, "Evento excluído com sucesso.");
                // Atualizar lista após exclusão
                eventosCarregados = eventoService.listarTodos();
                atualizarLista(listaEventosModel, eventosCarregados);
            }
        });

        // Carregar eventos ao abrir
        eventosCarregados = eventoService.listarTodos();
        atualizarLista(listaEventosModel, eventosCarregados);

        // Botões lado a lado
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botoesPanel.add(atualizarButton);
        botoesPanel.add(excluirButton);

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
                String bloco = String.format(
                        "%s\nDescrição: %s\nData: %s\nLocal: %s\nCapacidade: %d",
                        evento.getNome(),
                        evento.getDescricao(),
                        evento.getData(),
                        evento.getLocal(),
                        evento.getCapacidade()
                );
                model.addElement(bloco);
            }
        }
    }
}
