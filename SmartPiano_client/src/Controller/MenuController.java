package Controller;


import Model.*;
import Model.Utils.Logic;
import Model.Utils.Quicksort;
import View.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

//Controller for the MainMenu.
public class MenuController implements ActionListener {
    private MainMenuView v;
    private AudioPlayer introSong;
    private MenuManager m;
    private boolean backToMenu;
    private Point locationOnScreen;

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
        } catch (IllegalThreadStateException | NullPointerException ignore){}
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
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                //Stop the background song.
                if(introSong != null) introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay, "Free Play", locationOnScreen);
                    PianoManager m = new PianoManager();
                    m.registerController(c);
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    c.setView(v);
                    v.setVisible(true);
                });
                break;
            case "Configuration":
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    ConfigurationView v = new ConfigurationView(locationOnScreen);
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    ConfigurationManager m = new ConfigurationManager();
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "RecordPiano":
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                //Stop the background song.
                if(introSong != null) introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    c.isRecordingPiano();
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay, "Recording", locationOnScreen);
                    PianoManager m = new PianoManager();
                    m.registerController(c);
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    v.isRecordingPiano();
                    c.setView(v);
                    v.setVisible(true);
                });
                break;

            case "ShowSongList":
                this.backToMenu = true;
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
                //Shows the Configuration view.

                m.sendAction("DOWNLOAD/publicSongList="+ m.getClient().getLogin());
                break;
            case "Friends":
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                m.sendAction("ASKFOR/friendList");
                //Shows the Friends view.
                break;
        }
    }

    /**
     * MEthod that creates the songlist view.
     * @param songList An ArrayList which contains all the songs found.
     */
    public void showSongList(ArrayList<Song_database> songList){
        Song_database[] a = new Song_database[songList.size()];
        for (int i = 0; i < songList.size(); i++) {
            a[i] = songList.get(i);
        }

        a = Quicksort.Quicksort(a, 0, a.length-1);
        a = Logic.reverseSongList(a, a.length);

        songList = new ArrayList<>();
        songList.addAll(Arrays.asList(a));

        ArrayList<Song_database> finalSongList = songList;
        SwingUtilities.invokeLater(() -> {
            SongListController c = new SongListController(backToMenu, introSong);
            SongListView v = new SongListView(finalSongList, c, locationOnScreen);
            c.registerView(v);
            SongListManager m = new SongListManager(c,this.m.getClient());
            this.m.getClient().assignSongListManager(m);
            c.registerManager(m);
            v.registerController(c);
            v.setVisible(true);
        });
    }

    public void registerManager(MenuManager m) {
        this.m = m;
    }

    /**
     * Method that creates the friendlist view.
     * @param friendList An ArrayList which contains all the friends found.
     */
    public void sentFriendListToSend(FriendListToSend friendList) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Friend> list = friendList.getFriendList();
            FriendController c = new FriendController(introSong);
            FriendView v = new FriendView(list, c, locationOnScreen);
            FriendManager m = new FriendManager(c,this.m.getClient());
            m.getClient().assignFriendManager(m);
            c.registerManager(m);
            c.registerView(v);
            v.registerController(c);


            //v.generateFriendPanel(list);
            v.setVisible(true);
        });
    }
}
