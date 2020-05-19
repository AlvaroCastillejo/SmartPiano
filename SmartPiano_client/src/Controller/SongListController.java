package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class SongListController implements ActionListener {
    private AudioPlayer introSong;
    private SongListView v;
    private boolean backToMenu;

    private SongListManager m;
    /**
     * Constructor for the controller. It initializes the controller.
     * @param introSong The background song that was playing in the MainMenu.
     */
    public SongListController(boolean backToMenu, AudioPlayer introSong) {
        this.backToMenu = backToMenu;

        this.introSong = introSong;
    }

    /**
     * Registers all the actions performed in the SongListView.
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
        switch (command) {
            case "Back":
                //if I was in the menu go back to menu, else, go back to friendView

                if (backToMenu) {
                    v.setVisible(false);
                    if(introSong != null) introSong.setVolume(1.0f);
                    SwingUtilities.invokeLater(() -> {
                        MainMenuView v = new MainMenuView();
                        MenuController c = new MenuController(v, introSong);
                        MenuManager m = new MenuManager(c,this.m.getClient());
                        c.registerManager(m);
                        v.registerController(c);
                        v.setVisible(true);
                    });
                } else {
                    /*v.setVisible(false);
                    introSong.setVolume(1.0f);
                    SwingUtilities.invokeLater(() -> {
                        ArrayList<Friend> list = fillFriendList();
                        FriendController c = new FriendController(introSong);
                        FriendView v = new FriendView(list, c);
                        FriendManager m = new FriendManager(c,this.m.getClient());
                        m.getClient().assignFriendManager(m);
                        c.registerManager(m);
                        c.registerView(v);
                        v.registerController(c);
                        v.setVisible(true);
                    });*/
                    v.setVisible(false);
                    m.sendAction("ASKFOR/friendList");
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
                playSong(notes, "noll");
                /*v.setVisible(false);
                //Stop the background song.
                introSong.stopTheCurrent();
                //Shows the Piano view.
                LinkedList<Note> finalNotes = notes;
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Song toPlay = new Song(finalNotes, c);
                    c.setSong(toPlay);
                    Piano v = new Piano(c, toPlay);
                    v.isSongPiano();
                    PianoManager m = new PianoManager();
                    m.registerController(c);
                    m.setClient(this.m.getClient());
                    c.registerManager(m);
                    c.setView(v);
                    v.setVisible(true);
                    toPlay.start();
                });*/
                break;
            case "PLAY":
                v.setVisible(false);
                //introSong.stopTheCurrent();
                m.sendAction("DOWNLOAD/requestFileByName="+action);
                break;
        }
    }

    public void playSong(LinkedList<Note> notes, String songName){
        v.setVisible(false);
        //Stop the background song.
        if(introSong != null) introSong.stopTheCurrent();
        //Shows the Piano view.
        LinkedList<Note> finalNotes = notes;
        SwingUtilities.invokeLater(() -> {
            PianoController c = new PianoController();
            Song toPlay = new Song(finalNotes, c);
            c.setSong(toPlay);
            Piano v = new Piano(c, toPlay, songName);
            v.isSongPiano();
            PianoManager m = new PianoManager();
            m.registerController(c);
            m.setClient(this.m.getClient());
            c.registerManager(m);
            c.setView(v);
            v.setVisible(true);
            toPlay.start();
        });
    }

    public void registerManager(SongListManager m) {
        this.m = m;
    }

    public void registerView(SongListView v) {
        this.v = v;
    }
}
