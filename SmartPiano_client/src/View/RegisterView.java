package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbLogin;
    private JButton jbRegister;
    private JTextField jtUsername;
    private JPasswordField jpPassword;


    public RegisterView(){
        setTitle("Register");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        jpBackground.setBackground("F:\\Documentos\\auniversidad\\3\\DPOO\\2nSemestre\\Proyecto2\\SmartPiano\\SmartPiano_client\\images\\registerForm.png");

        jbRegister = new JButton();
        setButtonInvisible(jbRegister);
        jbRegister.setBounds(100, 275, 285, 45);
        jpBackground.add(jbRegister);

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(266, 396, 77, 16);
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
        jbLogin.setActionCommand("REGISTER/login");

        jbRegister.addActionListener(a);
        jbRegister.setActionCommand("REGISTER/register");
    }
}
