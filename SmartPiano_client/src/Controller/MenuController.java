package Controller;


import Model.*;
import View.*;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

//Controller for the MainMenu.
public class MenuController implements ActionListener {
    private MainMenuView v;
    private AudioPlayer introSong;
    private MenuManager m;

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
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay);
                    PianoManager m = new PianoManager();
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    c.setView(v);
                    v.setVisible(true);
                });
                break;
            case "Configuration":
                v.setVisible(false);
                introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    ConfigurationView v = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    ConfigurationManager m = new ConfigurationManager();
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "RecordPiano":
                v.setVisible(false);
                //Stop the background song.
                introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    c.isRecordingPiano();
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay);
                    PianoManager m = new PianoManager();
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    v.isRecordingPiano();
                    c.setView(v);
                    v.setVisible(true);
                });
                break;

            case "ShowSongList":
                boolean backToMenu = true;
                v.setVisible(false);
                introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    SongListView v = new SongListView();
                    SongListController c = new SongListController(backToMenu, v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;

            case "Friends":
                v.setVisible(false);
                //Shows the Friends view.
                SwingUtilities.invokeLater(() -> {
                    FriendView v = new FriendView();
                    FriendController c = new FriendController(v, introSong);
                    FriendManager m = new FriendManager(c);
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }

    public void registerManager(MenuManager m) {
        this.m = m;
    }
}
