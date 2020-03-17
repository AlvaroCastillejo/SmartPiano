package View;

import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigurationView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbKeyboardConfiguration;
    private int offset;

    public ConfigurationView () {
        setTitle("Configuration");
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
        jpBackground.setLayout(new GridLayout(1, 1));
        //String f = new File("").getAbsolutePath();
        //jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\menu.png"));

        jbKeyboardConfiguration = new JButton("Keyboard Configuration");
        //jbKeyboardConfiguration.setBounds(100, 160+offset, 285, 45);
        jpBackground.add(jbKeyboardConfiguration);

        getContentPane().add(jpBackground);

    }

    public void registerController (ActionListener al) {
        jbKeyboardConfiguration.addActionListener(al);
        jbKeyboardConfiguration.setActionCommand("KeyboardConfiguration");
    }

}
