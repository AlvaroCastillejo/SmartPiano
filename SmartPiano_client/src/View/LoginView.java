package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class LoginView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbLogin;
    private JButton jbRegister;

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
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\loginForm.png"));

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(100, 275, 285, 45);
        jpBackground.add(jbLogin);

        jbRegister = new JButton();
        setButtonInvisible(jbRegister);
        jbRegister.setBounds(266, 396, 77, 16);
        jpBackground.add(jbRegister);

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

        jbRegister.addActionListener(a);
        jbRegister.setActionCommand("LOGIN/register");
    }
}
