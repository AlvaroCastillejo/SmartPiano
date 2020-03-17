package Controller;


import Model.AudioPlayer;
import View.ConfigurationView;
import View.MainMenuView;
import View.Piano;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {
    private MainMenuView v;
    private AudioPlayer introSong;

    public MenuController(MainMenuView v){
        this.v=v;
        introSong = new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav");
        introSong.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        String command = actionEvent.getActionCommand();
        System.out.printf("%s",command);
        switch (command){
            case "PlayPiano":
                v.setVisible(false);
                introSong.stopTheCurrent();
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Piano m = new Piano(c);
                    c.setView(m);
                    m.setVisible(true);
                });
                break;
            case "Configuration":
                v.setVisible(false);
                introSong.stopTheCurrent();
                SwingUtilities.invokeLater(() -> {
                    ConfigurationView m = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(m);




                });
                break;


        }
    }

    private void setVisible(boolean b) {
    }



}
