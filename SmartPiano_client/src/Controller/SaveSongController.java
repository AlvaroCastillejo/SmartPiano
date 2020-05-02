package Controller;

import Model.PianoManager;
import Model.SaveSongManager;
import Model.Song;
import View.Piano;
import View.SaveSongView;
import Controller.PianoController;

import Model.Network.Client;

import javax.sound.midi.MidiSystem;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SaveSongController implements ActionListener {
    private SaveSongView v;
    private Client client;
    private SaveSongManager saveSongManager;
    private PianoController pc;
    public SaveSongController(SaveSongView v,Client client, PianoController pc) {
        this.v  = v;
        this.client = client;
        this.pc = pc;
        v.calltoAction(this);
    }
    public void assignSaveSongManager(SaveSongManager saveSongManager){
        this.saveSongManager = saveSongManager;
        client.assignSaveSongController(saveSongManager);
    }

    /**
     * We only handle the Login button
     * @param actionEvent Handled event
     */
    @Override

    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();

        switch (action) {
            case "saveSong":
                songNameAlreadyExists(v.getSongName());
        }
    }

    private void songNameAlreadyExists(String songName) {
        client.sendAction("ASKFOR/checkSongName="+songName);

    }

    private void processSongName(boolean b){
        if(b){
            v.songNameAlreadyExists();
        }else{
            pc.saveSong(v.getSongName(),v.getAlbumName(),v.getPrivacy());
            v.setVisible(false);

        }
    }

    //
    public String getSongName(){return v.getSongName();}

    public void sendResultCheckSongName(boolean b) {
        processSongName(b);
    }
}
