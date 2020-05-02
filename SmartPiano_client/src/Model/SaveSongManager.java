package Model;

import Controller.SaveSongController;

public class SaveSongManager {
    private SaveSongController saveSongController;

    public SaveSongManager (SaveSongController saveSongController){this.saveSongController = saveSongController; }

    public String getSongName(){ return saveSongController.getSongName(); }

    public void sendResultCheckSongName(boolean b) {
        saveSongController.sendResultCheckSongName(b);
    }
}
