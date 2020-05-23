package Model.Database;

import Model.SavedSong;
import Model.ServerConfiguration;
import Model.Song_database;
import Model.Utils.JsonServerUtils;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongVisualization extends JFrame {

    JPanel jpListSongs = new JPanel();
    private Song_database song;
    private ArrayList<Song_database> songList;
    private ArrayList<Song_database> songTopList;

    public SongVisualization (){
        this.song = song;
        songList = new ArrayList<Song_database>();
        songTopList = new ArrayList<Song_database>();

    }
    public void ImportSongList () throws SQLException {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();
        ResultSet rs = conn.selectQuery("SELECT * FROM Song");
        ConectorDB conn2;
        conn2 = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn2.connect();

        while (rs.next()) {

            try {

                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                String album_id = rs.getString(4);
                int num_reproductions = rs.getInt(5);
                String song_url = rs.getString(6);
                String privacy = rs.getString(7);
                Song_database song = new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy);
                songList.add(song);

            } catch (NullPointerException e) {

                e.printStackTrace();

            }

        }
    }

    public void ShowListSongs (){

        for (Song_database s: songList){
            System.out.println("------");
            System.out.println(s.getSong_name());
            System.out.println(s.getAuthor_name());
        }
    }
    public void DeleteSong (String nameSong){

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " DELETE FROM Song WHERE song_name like '" + nameSong + "' ";
        conn.deleteQuery(query);

    }
    public ArrayList<Song_database> ShowTop5List () throws SQLException {

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " SELECT * FROM Song ORDER BY num_reproductions desc LIMIT 5; ";
        ResultSet rs = conn.selectQuery(query);
        while (rs.next()) {
            try {
                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                String album_id = rs.getString(4);
                int num_reproductions = rs.getInt(5);
                String song_url = rs.getString(6);
                String privacy = rs.getString(7);
                Song_database song = new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy);
                songTopList.add(song);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return songTopList;
    }
    public void updateReproduction(String nameSong){

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " UPDATE Song SET num_reproductions=num_reproductions + 1 WHERE song_name like '" + nameSong + "' ";
        conn.updateQuery(query);

    }

    /**
     * Shows the whole song list .
     * @return List containg all songs from our database.
     * @throws SQLException
     */
    public ArrayList<Song_database> ShowSongList () throws SQLException {

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " SELECT * FROM Song;";
        ResultSet rs = conn.selectQuery(query);
        while (rs.next()) {
            try {
                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                String album_id = rs.getString(4);
                int num_reproductions = rs.getInt(5);
                String song_url = rs.getString(6);
                String privacy = rs.getString(7);
                Song_database song = new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy);
                songTopList.add(song);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return songTopList;
    }

    /**
     * Returns a lists of songs created by a specific author.
     * @param subAction Username of the song list creator.
     * @return list of songs created by the subAction user.
     */
    public ArrayList<Song_database> ShowSongListFrom(String subAction) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " SELECT * FROM Song where author_name like '" + subAction + "'";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()) {
                try {
                    int song_id = rs.getInt(1);
                    String song_name = rs.getString(2);
                    String author_name = rs.getString(3);
                    String album_id = rs.getString(4);
                    int num_reproductions = rs.getInt(5);
                    String song_url = rs.getString(6);
                    String privacy = rs.getString(7);
                    Song_database song = new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy);
                    songTopList.add(song);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songTopList;
    }
    public static SavedSong getSongByName(String name){
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " SELECT * FROM Song where song_name like '" + name + "'";
        ResultSet rs = conn.selectQuery(query);

        SavedSong song = null;
        try{
            while (rs.next()) {
                try {
                    int song_id = rs.getInt(1);
                    String song_name = rs.getString(2);
                    String author_name = rs.getString(3);
                    String album_name = rs.getString(4);
                    int num_reproductions = rs.getInt(5);
                    String song_url = rs.getString(6);
                    String privacy = rs.getString(7);
                    song = new SavedSong(song_name, album_name, privacy,author_name);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }
}
