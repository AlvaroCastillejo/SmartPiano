package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SongListToSend implements Serializable {
    private ArrayList<Song_database> songs;

    public SongListToSend(ArrayList<Song_database> songs) {
        this.songs = songs;
    }

    public ArrayList<Song_database> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song_database> songs) {
        this.songs = songs;
    }
}
