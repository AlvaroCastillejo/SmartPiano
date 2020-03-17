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

    public MenuController(MainMenuView v, AudioPlayer audioPlayer){
        this.v=v;
        introSong = audioPlayer;                                                   //new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav");
        try {
            introSong.start();
        } catch (IllegalThreadStateException ignore){}
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
                //introSong.stopTheCurrent();
                SwingUtilities.invokeLater(() -> {
                    ConfigurationView v = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }

    private void setVisible(boolean b) {
    }



}
