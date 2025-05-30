package com.sistemaevento.front;

import javax.swing.*;

import com.sistemaevento.service.PalestranteService;

import java.awt.*;

public class AutenticacaoDialog {

    public static boolean pedirCredenciais(Component parent, PalestranteService service) {
        JTextField idField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] message = {
            "ID do Palestrante:", idField,
            "Email do Palestrante:", emailField
        };

        int option = JOptionPane.showConfirmDialog(parent, message, "Autenticação do Palestrante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String email = emailField.getText().trim();

                if (service.validarCredenciais(id, email)) {
                    return true;
                    
                } else {
                    JOptionPane.showMessageDialog(parent, "Credenciais inválidas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
}

