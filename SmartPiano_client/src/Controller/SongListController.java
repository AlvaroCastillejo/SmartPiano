package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

public class SongListController implements ActionListener {
    private AudioPlayer introSong;
    private SongListView v;
    private boolean backToMenu;

    private SongListManager m;
    /**
     * Constructor for the controller. It initializes the controller.
     * @param v The view to control.
     * @param introSong The background song that was playing in the MainMenu.
     */
    public SongListController(boolean backToMenu, SongListView v, AudioPlayer introSong) {
        this.backToMenu = backToMenu;
        this.v = v;
        this.introSong = introSong;
    }

    /**
     * Registers all the actions performed in the SongListView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Back":
                //if I was in the menu go back to menu, else, go back to friendView
                if (backToMenu) {
                    v.setVisible(false);
                    introSong.setVolume(1.0f);
                    SwingUtilities.invokeLater(() -> {
                        MainMenuView v = new MainMenuView();
                        MenuController c = new MenuController(v, introSong);
                        v.registerController(c);
                        v.setVisible(true);
                    });
                } else {
                    v.setVisible(false);
                    introSong.setVolume(1.0f);
                    SwingUtilities.invokeLater(() -> {
                        FriendView v = new FriendView();
                        FriendController c = new FriendController(v, introSong);
                        v.registerController(c);
                        v.setVisible(true);
                    });
                }
                break;
            case "SelectedSong":
                //play the selected song when play song button is pressed
                break;
            case "PlaySong":
                File midiFile = new FileChooser().FileChooser();

                LinkedList<Note> notes = null;
                try {
                    notes = MidiFileSequencer.getNotes(midiFile.getPath());

                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                v.setVisible(false);
                //Stop the background song.
                introSong.stopTheCurrent();
                //Shows the Piano view.
                LinkedList<Note> finalNotes = notes;
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Song toPlay = new Song(finalNotes, c);
                    Piano v = new Piano(c, toPlay);
                    v.isSongPiano();
                    PianoManager m = new PianoManager();
                    //m.setClient(this.m.getClient());
                    c.registerManager(m);
                    c.setView(v);
                    v.setVisible(true);
                    toPlay.start();
                });
                break;
        }
    }

    public void registerManager(SongListManager m) {
        this.m = m;
    }
}
