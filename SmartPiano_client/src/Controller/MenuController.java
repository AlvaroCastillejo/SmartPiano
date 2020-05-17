package Controller;


import Model.*;
import View.*;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

//Controller for the MainMenu.
public class MenuController implements ActionListener {
    private MainMenuView v;
    private AudioPlayer introSong;
    private MenuManager m;
    private boolean backToMenu;

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
                v.setVisible(false);
                //Stop the background song.
                if(introSong != null) introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay, "Free Play");
                    PianoManager m = new PianoManager();
                    m.registerController(c);
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    c.setView(v);
                    v.setVisible(true);
                });
                break;
            case "Configuration":
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
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
                if(introSong != null) introSong.stopTheCurrent();
                //Shows the Piano view.
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    c.isRecordingPiano();
                    Song toPlay = new Song(null, c);
                    Piano v = new Piano(c, toPlay, "Recording");
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
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
                //Shows the Configuration view.

                ArrayList<Song_database> songList = fillSongList();
                //m.sendAction("DOWNLOAD/userSongList=" + m.getClient().getLogin());
                m.sendAction("DOWNLOAD/publicSongList="+ m.getClient().getLogin());
                break;
            case "Friends":
                v.setVisible(false);
                m.sendAction("ASKFOR/friendList");
                //Shows the Friends view.
                break;
        }
    }

    public void showSongList(ArrayList<Song_database> songList){
        SwingUtilities.invokeLater(() -> {
            SongListController c = new SongListController(backToMenu, introSong);
            SongListView v = new SongListView(songList, c);
            c.registerView(v);
            SongListManager m = new SongListManager(c,this.m.getClient());
            this.m.getClient().assignSongListManager(m);
            c.registerManager(m);
            v.registerController(c);
            v.setVisible(true);
        });
    }

    private ArrayList<Song_database> fillSongList() {
        ArrayList<Song_database> toReturn = new ArrayList<>();
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "admin", "4", 23, "", "public"));
        return toReturn;
    }

    private ArrayList<Friend> fillFriendList() {
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "PEDRO"));
        friends.add(new Friend("0", "Alvaro"));
        friends.add(new Friend("0", "Alvaro"));
        friends.add(new Friend("0", "Alvaro"));
        friends.add(new Friend("0", "Alvaro"));

        return friends;
    }

    public void registerManager(MenuManager m) {
        this.m = m;
    }

    public void sentFriendListToSend(FriendListToSend friendList) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Friend> list = friendList.getFriendList();
            FriendController c = new FriendController(introSong);
            FriendView v = new FriendView(list, c);
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
