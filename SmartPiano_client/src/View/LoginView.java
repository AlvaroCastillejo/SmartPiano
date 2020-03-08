package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbLogin;
    private JTextField jtUsername;
    private JPasswordField jpPassword;

    public LoginView(){
        setTitle("Login");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        jpBackground.setBackground("F:\\Documentos\\auniversidad\\3\\DPOO\\2nSemestre\\Proyecto2\\SmartPiano\\SmartPiano_client\\images\\loginForm.png");

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(100, 275, 285, 45);
        jpBackground.add(jbLogin);

        jtUsername = new JTextField();
        jtUsername.setBounds(154, 156, 233, 45);
        jtUsername.setOpaque(false);
        jtUsername.setBorder(null);
        jpBackground.add(jtUsername);

        jpPassword = new JPasswordField();
        jpPassword.setBounds(154, 216, 233, 45);
        jpPassword.setOpaque(false);
        jpPassword.setBorder(null);
        jpBackground.add(jpPassword);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void registerController(ActionListener a){
        jbLogin.addActionListener(a);
        jbLogin.setActionCommand("LOGIN/login");
    }
}
