package View;

import View.CustomComponents.JPiano;

import javax.swing.*;

public class MainMenuView extends JFrame {
    public MainMenuView(){
        //getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Piano");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPiano jPiano = new JPiano();
        getContentPane().add(jPiano);
    }
}
