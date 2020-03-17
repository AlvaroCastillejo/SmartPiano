package View;

import Controller.KeyboardConfigurationController;

import javax.swing.*;
import java.awt.*;

public class KeyboardConfigurationView extends JFrame {
    private KeyboardConfigurationController keyboardController;
    private JButton[] keys;

    public KeyboardConfigurationView(KeyboardConfigurationController k) {
        this.keyboardController = k;

        setSize(856, 400);
        setLocationRelativeTo (null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        JLayeredPane keyBoard = new JLayeredPane();

        // total de tecles
        keys = new JButton[36];
        int j = 0;
        //las teclas normals
        for (int i = 0; i < 21; i++) {
            keys[j] = generateKeyKeyboard(i);
            keyBoard.add(keys[j], 0, -1);
            j += 1;
            if (i % 7 != 2 && i % 7 != 6) {
                keys[j] = generateSustKeyKeyboard(i);
                keyBoard.add(keys[j], 1, -1);
                j += 1;
            }
        }

        keyBoard.setSize(840,300);
        keyBoard.setLocation(0,550);

        JPanel horizontalDivider = new JPanel();
        horizontalDivider.setSize(840, 7);
        horizontalDivider.setLocation(0, 541);
        horizontalDivider.setBackground(new Color(39, 39, 39));

        JPanel bottomWood = new JPanel();
        bottomWood.setSize(840, 11);
        bottomWood.setLocation(0, 750);
        bottomWood.setBackground(new Color(39, 39, 39));

        JPanel upperWood = new JPanel();
        upperWood.setSize(840, 20);
        upperWood.setLocation(0, 0);
        upperWood.setBackground(new Color(39, 39, 39));

        JPanel redPad = new JPanel();
        redPad.setSize(840,3);
        redPad.setLocation(0,548);
        redPad.setBackground(new Color(140, 0, 25));

    }

    private JButton generateSustKeyKeyboard(int i) {
        JButton key = new JButton();
        key.setBackground(Color.WHITE);
        //key.setBorderPainted(true);
        key.setOpaque(true);
        key.setLocation(i*40,0);
        key.setSize(40, 200);

        /*try{
            key.addActionListener(pianoController.getActionListener(i));
            key.setActionCommand("w/".concat(String.valueOf(i)));
        } catch (NullPointerException e){
            System.out.println("ignoring key: " + i);
        }*/

        key.addActionListener(keyboardController);
        key.setActionCommand("w/".concat(String.valueOf(i)));

        return key;
    }

    private JButton generateKeyKeyboard(int i) {
        JButton sustKey = new JButton();
        sustKey.setBackground(Color.BLACK);
        //sustKey.setBorderPainted(false);
        sustKey.setOpaque(true);
        sustKey.setLocation(25 + i*40,0);
        sustKey.setSize(30, 125);

        /*try{
            sustKey.addActionListener(pianoController.getActionListener(i));
            sustKey.setActionCommand("b/".concat(String.valueOf(i)));
        } catch (NullPointerException e){
            System.out.println("ignoring sust key: " + i);
        }*/

        sustKey.addActionListener(keyboardController);
        sustKey.setActionCommand("b/".concat(String.valueOf(i)));

        return sustKey;
    }


}
