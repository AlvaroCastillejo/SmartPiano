package View;

import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class ConfigurationView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JButton jbLogOff;
    private JButton jbeditKeys;
    private JButton jbDeleteAccount;
    private int offset;

    public ConfigurationView () {
        setTitle("User Configuration");
        setSize(500, 500);
        setLocationRelativeTo(null);
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
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\Configuration.png"));

        jbBack = new JButton();
        setButtonInvisible(jbBack);
        jbBack.setBounds(40, 45+offset, 75, 45);
        jpBackground.add(jbBack);

        jbLogOff = new JButton(); //LogOff button
        setButtonInvisible(jbLogOff);
        jbLogOff.setBounds(100, 160+offset, 285, 45);
        jpBackground.add(jbLogOff);

        jbeditKeys = new JButton("Edit Keys"); //Edit Keys button
        jbeditKeys.setBounds(100, 240+offset, 285, 45);
        jpBackground.add(jbeditKeys);

        jbDeleteAccount = new JButton("Delete account"); //Delete account button
        jbDeleteAccount.setBounds(100, 320+offset, 285, 45);
        jpBackground.add(jbDeleteAccount);


        getContentPane().add(jpBackground, BorderLayout.CENTER);


    }

    public void registerController (ActionListener al) {
        jbeditKeys.addActionListener(al);
        jbeditKeys.setActionCommand("KeyboardConfiguration");

        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");

        jbLogOff.addActionListener(al);
        jbLogOff.setActionCommand("LogOff");
    }
    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}
