package Controller;


import Model.AudioPlayer;
import Model.Song;
import View.ConfigurationView;
import View.MainMenuView;
import View.Piano;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Controller for the MainMenu.
public class MenuController implements ActionListener {
    private MainMenuView v;
    private AudioPlayer introSong;

    /**
     * Constructor for the class. Plays a background song.
     * @param v The view to control.
     * @param audioPlayer The song to play.
     */
    public MenuController(MainMenuView v, AudioPlayer audioPlayer){
        this.v=v;
        introSong = audioPlayer;
        try {
            introSong.start();
        } catch (IllegalThreadStateException ignore){}
    }

    /**
     * Registers all the actions performed in the MainMenu.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        String command = actionEvent.getActionCommand();
        switch (command){
            case "PlayPiano":
                v.setVisible(false);
                //Stop the background song.
                introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Song toPlay = new Song("Prueba1.txt", c);
                    Piano m = new Piano(c, toPlay);
                    c.setView(m);
                    m.setVisible(true);
                });
                break;
            case "Configuration":
                v.setVisible(false);
                //introSong.stopTheCurrent();
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    ConfigurationView v = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }
}
