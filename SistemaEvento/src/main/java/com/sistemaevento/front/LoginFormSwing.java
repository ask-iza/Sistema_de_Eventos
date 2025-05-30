package com.sistemaevento.front;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sistemaevento.service.PalestranteService;

public class LoginFormSwing {
    private final PalestranteService palestranteService = new PalestranteService();

    public void exibirLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        panel.add(new JLabel("E-mail:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        JButton loginButton = new JButton("Entrar");
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());

            // Validação fictícia para exemplo
            boolean autenticado = palestranteService.autenticar(email, senha);
            if (autenticado) {
                SessaoUsuario.setPerfil("palestrante");
                JOptionPane.showMessageDialog(frame, "Login realizado com sucesso!");
                frame.dispose();
                // Abrir menu principal ou painel com permissão
                new MenuPrincipalSwing().exibirMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Credenciais inválidas.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

