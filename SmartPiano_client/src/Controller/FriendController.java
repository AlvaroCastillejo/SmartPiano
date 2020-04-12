package Controller;

import Model.*;
import Model.AudioPlayer;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendController implements ActionListener {
    private AudioPlayer introSong;
    private FriendView v;
    private FriendManager m;

    /**
     * Constructor for the controller. It initializes the controller.
     * @param v The view to control.
     * @param introSong The background song that was playing in the MainMenu.
     */
    public FriendController(FriendView v, AudioPlayer introSong) {
        this.v = v;
        this.introSong = introSong;
    }

    /**
     * Registers all the actions performed in the FriendView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Back":
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    MainMenuView v = new MainMenuView();
                    MenuController c = new MenuController(v, introSong);
                    MenuManager m = new MenuManager(c, this.m.getClient());
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
                introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    SongListView v = new SongListView();
                    SongListController c = new SongListController(backToMenu, v, introSong);
                    SongListManager m = new SongListManager(c, this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "DeleteFriend":
                //delete the selected friend, update friendView and don't show that friend in the table
                break;
        }
    }

    public void registerManager(FriendManager m) {
        this.m = m;
    }
}
