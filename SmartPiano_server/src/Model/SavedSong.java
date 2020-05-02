package Model;

import java.io.File;
import java.io.Serializable;

public class SavedSong implements Serializable {
    private String songName;
    private String songAlbum;
    private String songPrivacy;
    private String songAuthor;
    private File songFile;

    public SavedSong(String songName, String songAlbum, String songPrivacy, String songAuthor, File songFile) {
        this.songName = songName;
        this.songAlbum = songAlbum;
        this.songPrivacy = songPrivacy;
        this.songAuthor= songAuthor;
        this.songFile = songFile;
    }

    public File getFile() {
        return songFile;
    }

    public void destroySongFile(){
        songFile = null;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongPrivacy() {
        return songPrivacy;
    }

    public void setSongPrivacy(String songPrivacy) {
        this.songPrivacy = songPrivacy;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }
}
