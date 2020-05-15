package Controller;

import Model.*;
import Model.AudioPlayer;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendController implements ActionListener {
    private AudioPlayer introSong;
    private FriendView v;
    private FriendManager m;

    /**
     * Constructor for the controller. It initializes the controller.
     * @param introSong The background song that was playing in the MainMenu.
     */
    public FriendController(AudioPlayer introSong) {
        this.introSong = introSong;
    }

    /**
     * Registers all the actions performed in the FriendView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        String action = "";
        if(command.contains("/")){
            String[] elements = command.split("/");
            command = elements[0];
            action = elements[1];
        }
        System.out.println(command);
        switch (command) {
            case "Back":
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    MainMenuView v = new MainMenuView();
                    MenuController c = new MenuController(v, introSong);
                    MenuManager m = new MenuManager(c, this.m.getClient());
                    m.getClient().assignMenuManager(m);
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "AddFriend":
                //add friend, update friendView and show the new friend in the table
                m.sendRequest("UPLOAD/friendRequest/" + v.getJtFriendCode());
                break;
            case "Friend":
                //if friend is selected, show ShowSongs and DeleteFriend buttons above Friend button

                break;
            case "ShowSongList":
                //show the <user_login> SongListView
                boolean backToMenu = false;
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    SongListController c = new SongListController(backToMenu, introSong);
                    ArrayList<Song_database> songList = fillSongList();
                    SongListView v = new SongListView(songList, c);
                    c.registerView(v);
                    SongListManager m = new SongListManager(c, this.m.getClient());
                    this.m.getClient().assignSongListManager(m);
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "DeleteFriend":
                //delete the selected friend, update friendView and don't show that friend in the table
                break;
            case "SELECT":
                backToMenu = false;
                v.setVisible(false);
                if(introSong != null) introSong.setVolume(-10f);
                //Shows the Configuration view.
                /*SwingUtilities.invokeLater(() -> {
                    SongListController c = new SongListController(backToMenu, introSong);
                    ArrayList<Song_database> songList = fillSongList();
                    SongListView v = new SongListView(songList, c);
                    c.registerView(v);
                    SongListManager m = new SongListManager(c, this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });*/
                ArrayList<Song_database> songList = fillSongList();
                m.sendAction("DOWNLOAD/userSongList=" + action);
                break;
        }
    }

    private ArrayList<Song_database> fillSongList() {
        ArrayList<Song_database> toReturn = new ArrayList<>();
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        toReturn.add(new Song_database(0,"Tusa", "JUAN", "4", 23, "", "public"));
        return toReturn;
    }

    public void registerManager(FriendManager m) {
        this.m = m;
    }

    public void registerView(FriendView v) {
        this.v = v;
    }

    public void sendResultFriendAdded(boolean added, String s) {
        if(added){
            v.addedStatus(s, "green");
        } else {
            v.addedStatus(s, "red");
        }
    }

    public void updateUI(ArrayList<Friend> friends) {
        v.updateUI(friends);
    }
}