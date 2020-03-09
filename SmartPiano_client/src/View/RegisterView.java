package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;

public class RegisterView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbLogin;
    private JButton jbRegister;
    private JTextField jtUsername;
    private JTextField jtMail;
    private JPasswordField jpPassword;
    private JLabel jlError;


    public RegisterView(){
        setTitle("Register");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\registerForm2.png"));

        jbRegister = new JButton();
        setButtonInvisible(jbRegister);
        jbRegister.setBounds(100, 310, 285, 45);
        jpBackground.add(jbRegister);

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(266, 396, 77, 16);
        jpBackground.add(jbLogin);

        jtUsername = new JTextField();
        jtUsername.setBounds(154, 130, 233, 45);
        jtUsername.setOpaque(false);
        jtUsername.setBorder(null);
        jpBackground.add(jtUsername);

        jtMail = new JTextField();
        jtMail.setBounds(154, 190, 233, 45);
        jtMail.setOpaque(false);
        jtMail.setBorder(null);
        jpBackground.add(jtMail);

        jpPassword = new JPasswordField();
        jpPassword.setBounds(154, 250, 233, 45);
        jpPassword.setOpaque(false);
        jpPassword.setBorder(null);
        jpBackground.add(jpPassword);

        jlError = new JLabel();
        //JButton jbTest = new JButton();
        jlError.setBounds(120, 420, 233, 40);
        jpBackground.add(jlError);

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

    public String getUsername(){
        return jtUsername.getText();
    }

    public String getMail(){
        return jtMail.getText();
    }

    public String getPassword(){
        return String.valueOf(jpPassword.getPassword());
    }

    public void showError(String error) {
        jlError.setText(error);
    }
}
