package View;

import Controller.RegisterController;
import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class RegisterView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbLogin;
    private JButton jbRegister;
    private JTextField jtUsername;
    private JTextField jtMail;
    private JPasswordField jpPassword;
    private JPasswordField jpRepeatPassword;
    private JLabel jlError;

    private int offset;

    public RegisterView(Point locationOnScreen){
        setTitle("Register");
        setLocation(locationOnScreen);
        setSize(500, 500);
        //setLocationRelativeTo(null);
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
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\registerForm3.png"));

        jbRegister = new JButton();
        setButtonInvisible(jbRegister);
        jbRegister.setBounds(100, 314+offset, 285, 45);
        jpBackground.add(jbRegister);

        jbLogin = new JButton();
        setButtonInvisible(jbLogin);
        jbLogin.setBounds(266, 396+offset, 77, 16);
        jpBackground.add(jbLogin);

        jtUsername = new JTextField();
        jtUsername.setBounds(154, 98+offset, 233, 45);
        jtUsername.setOpaque(false);
        jtUsername.setBorder(null);
        jpBackground.add(jtUsername);

        jtMail = new JTextField();
        jtMail.setBounds(154, 148+offset, 233, 45);
        jtMail.setOpaque(false);
        jtMail.setBorder(null);
        jpBackground.add(jtMail);

        jpPassword = new JPasswordField();
        jpPassword.setBounds(154, 198+offset, 233, 45);
        jpPassword.setOpaque(false);
        jpPassword.setBorder(null);
        jpBackground.add(jpPassword);

        jpRepeatPassword = new JPasswordField();
        jpRepeatPassword.setBounds(154, 248+offset, 233, 45);
        jpRepeatPassword.setOpaque(false);
        jpRepeatPassword.setBorder(null);
        jpBackground.add(jpRepeatPassword);

        jlError = new JLabel();
        //JButton jbTest = new JButton();
        jlError.setBounds(120, 420+offset, 233, 40);
        jpBackground.add(jlError);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    /**
     * Sets a button invisible.
     * @param button The button to set invisible.
     */
    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void registerController(RegisterController a){
        jbLogin.addActionListener(a);
        jbLogin.setActionCommand("REGISTER/login");

        jpPassword.addKeyListener(a);

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
        jpBackground.repaint();
        repaint();
    }

    public String getRepeatPassword() {
        return String.valueOf(jpRepeatPassword.getPassword());
    }
}
