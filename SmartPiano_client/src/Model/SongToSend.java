package Model;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

public class SongToSend implements Serializable {
    private LinkedList<Note> notes;
    private final String songName;

    public SongToSend(File file, String songName) {
        this.songName = songName;
        notes = new LinkedList<>();

        try {
            notes = MidiFileSequencer.getNotes(file.getPath());

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Note> getNotes() {
        return notes;
    }

    public String getSongName() {
        return songName;
    }
}
