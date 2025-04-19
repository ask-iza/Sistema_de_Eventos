
package com.sistemaevento.front;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sistemaevento.service.EventoService;
import com.sistemaevento.service.PalestranteService;
import com.sistemaevento.tabelas.Evento;
import com.sistemaevento.tabelas.Palestrante;

public class PalestranteEventoFormSwing {

    private final PalestranteService palestranteService = new PalestranteService();
    private final EventoService eventoService = new EventoService();

    public JPanel criarPainel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Campos do palestrante
        JTextField nomePalestranteField = new JTextField();
        JTextField curriculoField = new JTextField();
        JTextField areaField = new JTextField();

        // Campos do evento
        JTextField nomeEventoField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField localField = new JTextField();
        JTextField capacidadeField = new JTextField();

        JButton cadastrarButton = new JButton("Cadastrar");
        JLabel resultadoLabel = new JLabel();

        cadastrarButton.addActionListener(e -> {
            try {
                // Criar palestrante
                Palestrante palestrante = new Palestrante();
                palestrante.setNome(nomePalestranteField.getText());
                palestrante.setCurriculo(curriculoField.getText());
                palestrante.setArea_atuacao(areaField.getText());
                int idPalestrante = palestranteService.cadastrarRetornandoId(palestrante); // novo método

                if (idPalestrante <= 0) {
                    resultadoLabel.setText("Erro ao cadastrar palestrante.");
                    return;
                }

                // Criar evento
                Evento evento = new Evento();
                evento.setNome(nomeEventoField.getText());
                evento.setDescricao(descricaoField.getText());
                evento.setData(dataField.getText());
                evento.setLocal(localField.getText());
                evento.setCapacidade(Integer.parseInt(capacidadeField.getText()));
                evento.setPalestrantesIds(java.util.Arrays.asList(idPalestrante));

                int idEvento = eventoService.salvarRetornandoId(evento);

                if (idEvento > 0) {
                    eventoService.vincularPalestrante(idEvento, idPalestrante);
                    resultadoLabel.setText("Evento e Palestrante cadastrados com sucesso! Evento ID: " + idEvento);
                    nomePalestranteField.setText("");
                    curriculoField.setText("");
                    areaField.setText("");
                    nomeEventoField.setText("");
                    descricaoField.setText("");
                    dataField.setText("");
                    localField.setText("");
                    capacidadeField.setText("");

                } else {
                    resultadoLabel.setText("Erro ao salvar evento.");
                }

            } catch (Exception ex) {
                resultadoLabel.setText("Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        panel.add(new JLabel("Nome do Palestrante:")); panel.add(nomePalestranteField);
        panel.add(new JLabel("Currículo:")); panel.add(curriculoField);
        panel.add(new JLabel("Área de Atuação:")); panel.add(areaField);
        panel.add(new JLabel("Nome do Evento:")); panel.add(nomeEventoField);
        panel.add(new JLabel("Descrição:")); panel.add(descricaoField);
        panel.add(new JLabel("Data (YYYY-MM-DD):")); panel.add(dataField);
        panel.add(new JLabel("Local:")); panel.add(localField);
        panel.add(new JLabel("Capacidade:")); panel.add(capacidadeField);
        panel.add(cadastrarButton);
        panel.add(resultadoLabel);

        return panel;
    }
}