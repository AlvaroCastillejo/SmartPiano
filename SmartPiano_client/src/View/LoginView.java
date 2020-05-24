package View;

import Controller.LoginController;
import View.CustomComponents.JPanelBackground;

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
    private JLabel jlError = new JLabel();

    private int offset;

    public LoginView(Point locationOnScreen){
        setTitle("Login");
        setSize(500, 500);
        if(locationOnScreen == null){
            setLocationRelativeTo(null);
        } else {
            setLocation(locationOnScreen);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }


        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\loginForm.png"));

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(100, 275+offset, 285, 45);
        jpBackground.add(jbLogin);

        jbRegister = new JButton();
        setButtonInvisible(jbRegister);
        jbRegister.setBounds(266, 396+offset, 77, 16);
        jpBackground.add(jbRegister);

        jtUsername = new JTextField();
        jtUsername.setBounds(154, 156+offset, 233, 45);
        jtUsername.setOpaque(false);
        jtUsername.setBorder(null);
        jpBackground.add(jtUsername);

        jpPassword = new JPasswordField();
        jpPassword.setBounds(154, 216+offset, 233, 45);
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

    public void registerController(LoginController a){
        jbLogin.addActionListener(a);
        jbLogin.addKeyListener(a);
        jbLogin.setActionCommand("LOGIN/login");

        jpPassword.addKeyListener(a);

        jbRegister.addActionListener(a);
        jbRegister.setActionCommand("LOGIN/register");
    }

    public String getUsername() {
        return jtUsername.getText();
    }

    public String getPassword() {
        return String.valueOf(jpPassword.getPassword());
    }

    /**
     * Shows the error if any.
     * @param error The error message.
     */
    public void showErrorUser(String error) {
        jlError.setText(error);
    }
}
