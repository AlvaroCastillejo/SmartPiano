package View;

import Controller.KeyboardConfigurationController;
import Model.Configuration;
import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class KeyboardConfigurationView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private KeyboardConfigurationController keyboardController;
    private JButton[] keys;
    private int offset;
    private Configuration configKeys;
    private Map<String, JButton> keyboardMap;

    private JLabel goBackInstruction;
    private JLabel tmpInstruction;

    public KeyboardConfigurationView(KeyboardConfigurationController k, Point locationOnScreen) {
        this.keyboardController = k;

        keyboardMap = new HashMap<>();

        setTitle("Edit Keys");
        setSize(856, 500);
        setLocationRelativeTo(null);
        setLocation(locationOnScreen);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }

        JLayeredPane keyBoard = new JLayeredPane();

        //Total amount of keys.
        keys = new JButton[36];
        int j = 0;
        //The normal keys.
        for (int i = 0; i < 21; i++) {
            keys[j] = generateKey(i);
            keyBoard.add(keys[j], 0, -1);
            j += 1;
            if (i % 7 != 2 && i % 7 != 6) {
                keys[j] = generateSustKey(i);
                keyBoard.add(keys[j], 1, -1);
                j += 1;
            }
        }

        goBackInstruction = new JLabel("Press return to go back!");
        goBackInstruction.setBounds(50, 60, 1000, 100);
        goBackInstruction.setFont(new Font("Tahoma", Font.BOLD, 30));
        getContentPane().add(goBackInstruction);

        tmpInstruction = new JLabel("");
        tmpInstruction.setBounds(50, 100, 1000, 100);
        tmpInstruction.setFont(new Font("Tahoma", Font.BOLD, 30));
        getContentPane().add(tmpInstruction);

        keyBoard.setSize(840,300);
        keyBoard.setLocation(0,250);

        //Beautifiers.
        JPanel horizontalDivider = new JPanel();
        horizontalDivider.setSize(840, 7);
        horizontalDivider.setLocation(0, 341);
        horizontalDivider.setBackground(new Color(39, 39, 39));

        JPanel bottomWood = new JPanel();
        bottomWood.setSize(840, 11);
        bottomWood.setLocation(0, 450);
        bottomWood.setBackground(new Color(39, 39, 39));

        JPanel upperWood = new JPanel();
        upperWood.setSize(840, 20);
        upperWood.setLocation(0, 0);
        upperWood.setBackground(new Color(39, 39, 39));

        JPanel redPad = new JPanel();
        redPad.setSize(840,3);
        redPad.setLocation(0,248);
        redPad.setBackground(new Color(140, 0, 25));

        JPanel verticalDivider1 = new JPanel();
        verticalDivider1.setSize(2, 541);
        verticalDivider1.setLocation(120, 0);
        verticalDivider1.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider2 = new JPanel();
        verticalDivider2.setSize(2, 541);
        verticalDivider2.setLocation(280, 0);
        verticalDivider2.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider3 = new JPanel();
        verticalDivider3.setSize(2, 541);
        verticalDivider3.setLocation(400, 0);
        verticalDivider3.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider4 = new JPanel();
        verticalDivider4.setSize(2, 541);
        verticalDivider4.setLocation(560, 0);
        verticalDivider4.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider5 = new JPanel();
        verticalDivider5.setSize(2, 541);
        verticalDivider5.setLocation(680, 0);
        verticalDivider5.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider6 = new JPanel();
        verticalDivider6.setSize(2, 541);
        verticalDivider6.setLocation(838, 0);
        verticalDivider6.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider7 = new JPanel();
        verticalDivider7.setSize(2, 541);
        verticalDivider7.setLocation(0, 0);
        verticalDivider7.setBackground(new Color(149, 149, 149));

        getContentPane().add(keyBoard);
        getContentPane().add(redPad);
        getContentPane().add(horizontalDivider);
        getContentPane().add(bottomWood);
        getContentPane().add(upperWood);
        getContentPane().add(verticalDivider1);
        getContentPane().add(verticalDivider2);
        getContentPane().add(verticalDivider3);
        getContentPane().add(verticalDivider4);
        getContentPane().add(verticalDivider5);
        getContentPane().add(verticalDivider6);
        getContentPane().add(verticalDivider7);
        getContentPane().setBackground(Color.LIGHT_GRAY);
    }

    private JButton generateKey(int i){
        JButton key = new JButton();
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        key.setLocation(i*40,0);
        key.setSize(40, 200);

        //ActionListener
        //Disable space bar triggering click for JButton.
        key.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        key.addActionListener(keyboardController);
        key.setActionCommand("w/".concat(String.valueOf(i)));
        key.addKeyListener(keyboardController);

        keyboardMap.put("w/".concat(String.valueOf(i)), key);

        return key;

    }


    private JButton generateSustKey(int i){
        JButton sustKey = new JButton();
        sustKey.setBackground(Color.BLACK);
        sustKey.setOpaque(true);
        sustKey.setLocation(25 + i*40,0);
        sustKey.setSize(30, 125);

        //ActionListener
        //Disable space bar triggering click for JButton.
        sustKey.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        sustKey.addActionListener(keyboardController);
        sustKey.setActionCommand("b/".concat(String.valueOf(i)));
        sustKey.addKeyListener(keyboardController);

        keyboardMap.put("b/".concat(String.valueOf(i)), sustKey);

        return sustKey;

    }

    public void registerController (ActionListener al) {
        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");
    }

    /**
     * Turns the JButton invisible.
     * @param button
     */
    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void pressButton(String a){
        try{
            if(a.startsWith("b")) ((JButton) keyboardMap.get(a)).setBackground(Color.DARK_GRAY);
            else ((JButton) keyboardMap.get(a)).setBackground(Color.LIGHT_GRAY);
        } catch (NullPointerException ignore){}
    }


    public void releaseButton(String a){
        try{
            if(a.startsWith("b")) ((JButton) keyboardMap.get(a)).setBackground(Color.BLACK);
            else ((JButton) keyboardMap.get(a)).setBackground(Color.WHITE);
        } catch (NullPointerException ignore){}
    }

    public void showInfoOn(String key, String action){
        switch (action){
            case "actionPerfomed":
                tmpInstruction.setText("Key " + key + " is selected. Press any unbind key.");
                repaint();
                break;
            case "status":
                tmpInstruction.setText("The pressed key is already in use for " + key);
                repaint();
                break;
            case "ok":
                tmpInstruction.setText("Key " + key + " was bind successfully");
                repaint();
                break;
        }
        int delay = 3000;
        ActionListener timedAction  = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tmpInstruction.setText("");
                repaint();
            }
        };

        Timer timer = new Timer(delay, timedAction);
        timer.setRepeats(false);
        timer.start();
    }
}
