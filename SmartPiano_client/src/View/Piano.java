package View;

import Controller.PianoController;
import Model.Song;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Piano extends JFrame {
    private PianoController pianoController;
    private JButton[] keys;
    private Map<String, JButton> keyboardMap;
    private boolean isRunning;

    public Piano(PianoController a, Song toPlay) {
        this.pianoController = a;
        keyboardMap = new HashMap<>();
        isRunning = false;

        setSize(856, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        JLayeredPane keyBoard = new JLayeredPane();

        // total de tecles
        keys = new JButton[36];
        int j = 0;
        //las teclas normals
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

        toPlay.registerView(this);
        toPlay.start();
    }

    private JButton generateKey(int i){
        JButton key = new JButton();
        key.setBackground(Color.WHITE);
        //key.setBorderPainted(true);
        key.setOpaque(true);
        key.setLocation(i*40,0);
        key.setSize(40, 200);

        key.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        key.addActionListener(pianoController);
        key.setActionCommand("w/".concat(String.valueOf(i)));
        key.addKeyListener(pianoController);

        keyboardMap.put("w/".concat(String.valueOf(i)), key);

        return key;
    }

    private JButton generateSustKey(int i){
        JButton sustKey = new JButton();
        sustKey.setBackground(Color.BLACK);
        //sustKey.setBorderPainted(false);
        sustKey.setOpaque(true);
        sustKey.setLocation(25 + i*40,0);
        sustKey.setSize(30, 125);

        sustKey.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        sustKey.addActionListener(pianoController);
        sustKey.setActionCommand("b/".concat(String.valueOf(i)));
        sustKey.addKeyListener(pianoController);

        keyboardMap.put("b/".concat(String.valueOf(i)), sustKey);

        return sustKey;
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

    public void doAction(String action){    }

    public void drop(String keyCode) {
        Thread playing = new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;

                int i = 7;
                while (isRunning){
                    try {
                        //Thread.sleep(10);
                        JPanel note = new JPanel();
                        note.setSize(840,10);
                        note.setLocation(0,i);
                        note.setBackground(new Color(140, 0, 25));
                        getContentPane().add(note);
                        System.out.println("repainting note at y: " + note.getLocation().getY());
                        getContentPane().repaint();
                        Thread.sleep(10);
                        getContentPane().remove(note);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        playing.start();
    }



    public void stop(){
        isRunning = false;
    }

}




