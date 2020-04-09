package Controller;

import Model.AudioPlayer;
import Model.LoginManager;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendController implements ActionListener {
    private AudioPlayer introSong;
    private FriendView v;

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
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "AddFriend":
                //add friend, update friendView and show the new friend in the table
                break;
            case "Friend":
                //if friend is selected, show ShowSongs and DeleteFriend buttons above Friend button
                break;
            case "ShowSongs":
                //show the <user_login> songsView (not created yet)
                v.setVisible(false);
                introSong.setVolume(-10f);
                //Shows the Configuration view.
                SwingUtilities.invokeLater(() -> {
                    SongListView v = new SongListView();
                    SongListController c = new SongListController(v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "DeleteFriend":
                //delete the selected friend, update friendView and don't show that friend in the table
                break;
        }
    }
}
