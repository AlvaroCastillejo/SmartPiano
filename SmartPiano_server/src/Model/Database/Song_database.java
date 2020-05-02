package Model.Database;

import java.io.File;

public class Song_database {

    private int song_id ;
    private int song_number;
    private String song_name;
    private String author_name;
    private String album_id;
    private int num_reproductions;
    private String song_url;
    private File song;

    public Song_database(int song_id, int song_number, String song_name,String author_name, String album_id, int num_reproductions, String song_url) {
        this.song_id = song_id;
        this.song_number = song_number;
        this.song_name = song_name;
        this.author_name = author_name;
        this.album_id = album_id;
        this.num_reproductions = num_reproductions;
        this.song_url = song_url;

        String f = new File("").getAbsolutePath();

        this.song = new File(f.concat("\\SmartPiano_server\\src\\Model\\Assets\\Songs\\" + song_name));
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getSong_number() {
        return song_number;
    }

    public void setSong_number(int song_number) {
        this.song_number = song_number;
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