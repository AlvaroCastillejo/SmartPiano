package Model.Database;

import Model.ServerConfiguration;
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
                int song_number = rs.getInt(2);
                String song_name = rs.getString(3);
                String author_name = rs.getString(4);
                String album_id = rs.getString(5);
                int num_reproductions = rs.getInt(6);
                String song_url = rs.getString(7);
                Song_database song = new Song_database(song_id,song_number,song_name,author_name,album_id,num_reproductions,song_url);
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
                int song_number = rs.getInt(2);
                String song_name = rs.getString(3);
                String author_name = rs.getString(4);
                String album_id = rs.getString(5);
                int num_reproductions = rs.getInt(6);
                String song_url = rs.getString(7);
                Song_database song = new Song_database(song_id,song_number,song_name,author_name,album_id,num_reproductions,song_url);
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
    public ArrayList<Song_database> ShowSongList () throws SQLException {

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " SELECT * FROM Song;";
        ResultSet rs = conn.selectQuery(query);
        while (rs.next()) {
            try {
                int song_id = rs.getInt(1);
                int song_number = rs.getInt(2);
                String song_name = rs.getString(3);
                String author_name = rs.getString(4);
                String album_id = rs.getString(5);
                int num_reproductions = rs.getInt(6);
                String song_url = rs.getString(7);
                Song_database song = new Song_database(song_id,song_number,song_name,author_name,album_id,num_reproductions,song_url);
                songTopList.add(song);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return songTopList;
    }
}
