package Model;

import java.io.File;
import java.io.Serializable;

public class Song_database implements Serializable {

    private int song_id ;
    private String song_name;
    private String author_name;
    private String album_id;
    private int num_reproductions;
    private String song_url;
    private String privacy;
    private File song;

    public Song_database(int song_id, String song_name, String author_name, String album_id, int num_reproductions, String song_url, String privacy) {
        this.song_id = song_id;
        this.song_name = song_name;
        this.author_name = author_name;
        this.album_id = album_id;
        this.num_reproductions = num_reproductions;
        this.song_url = song_url;
        this.privacy = privacy;

        String f = new File("").getAbsolutePath();

        this.song = new File(f.concat("\\SmartPiano_server\\src\\Model\\Assets\\Songs\\" + song_name));
    }

    public Song_database() {
        this.song_id = -1;
        this.song_name = "";
        this.author_name = "";
        this.album_id = "";
        this.num_reproductions = -1;
        this.song_url = "";
        this.privacy = "";
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }
    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }
    public int getNum_reproductions() {
        return num_reproductions;
    }

    public void setNum_reproductions(int num_reproductions) {
        this.num_reproductions = num_reproductions;
    }

}
