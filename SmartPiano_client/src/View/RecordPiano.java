package View;

import Controller.LoginController;
import Controller.PianoController;
import Model.Song;
import View.CustomComponents.JPiano;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RecordPiano extends JFrame{

    public RecordPiano() {
        getContentPane().setLayout(new GridLayout(1, 1));
        setTitle("RecordingPiano");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(856, 800);
        setLocationRelativeTo(null);

        JPiano piano = new JPiano();
        getContentPane().add(piano);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PianoController c = new PianoController();
            Song toPlay = new Song("Prueba1.txt", c);
            Piano m = new Piano(c, toPlay);
            c.setView(m);
            m.setVisible(true);
        });
    }
}




