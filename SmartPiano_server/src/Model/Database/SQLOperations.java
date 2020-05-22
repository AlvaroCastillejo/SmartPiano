package Model.Database;

import Model.*;
import Model.Utils.JsonServerUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SQLOperations {
    public static void ImportaUsuari(String name, String pass, String mail) throws SQLException {
        System.out.println("entro DB??");


        System.out.println("conecto ?");

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();


        conn.insertQuery("INSERT INTO User(username,password,email) VALUES ('" + name + "','" + pass + "', '" + mail + "')");
        System.out.println("intento inserir");

    }

    public static int userAlreadyExists(User user) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();
        String query = "SELECT * FROM User WHERE username like '" + user.getUsername() + "'";
        ResultSet rs = conn.selectQuery(query);
        try {
            if (!rs.isBeforeFirst()) {
                query = "SELECT * FROM User WHERE email like '" + user.getMail() + "'";
                rs = conn.selectQuery(query);
                if (!rs.isBeforeFirst()) {
                    return 0; //Everything is OK
                } else {
                    return 2; //Mail already exists
                }
            } else {
                return 1; //User already exists
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return -1;
    }

    public static int findUser(User user) throws SQLException {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();
        String query = "SELECT * FROM User WHERE (username like '" + user.getUsername() + "'"+"OR email like '" + user.getUsername() + "'" + ") AND password LIKE '"+ user.getPassword() + "'";
        ResultSet rs = conn.selectQuery(query);
        if (rs.isBeforeFirst()){
            return 0;
        }else{
            return 1;
        }
    }

    public static int addSong(SavedSong s) throws SQLException{
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "INSERT INTO Song(song_name,author_name ,album_name,num_reproductions,song_url,privacy)"+
                       "VALUES("+"'"+s.getSongName()+"'"+","+"'"+s.getSongAuthor()+"'"+","+"'"+s.getSongAlbum()+"'"+",0,"+"'"+s.getSongName()+"'"+","+"'"+s.getSongPrivacy()+"'"+");";
        conn.insertQuery(query);
        return 0;
    }

    public static boolean checkSongName(String songName) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT * FROM Song WHERE song_name like '" + songName +  "'";
        ResultSet rs = conn.selectQuery(query);
        try {
            if (rs.isBeforeFirst()) {
                return true; // No other song has that name
            } else {
                return false; //Song name already exists
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        System.out.println("-----> final funcion\n");
        return false;
    }

    public static ArrayList<Friend> getFriendsFrom(User user) {
        ArrayList<Friend> friendList = new ArrayList<>();

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT * FROM friend WHERE usernameUser like '" + user.getUsername() +  "'";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){
                String usernameFriend = rs.getString(1);
                String emailFriend = rs.getString(2);
                String usernameUser = rs.getString(3);
                String emailUser = rs.getString(4);
                friendList.add(new Friend(emailFriend, usernameFriend));
            }
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendList;
    }

    public static boolean alreadyFriends(User user, String friendCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT * FROM friend WHERE usernameUser like '" + user.getUsername() +  "' and usernameFriend like '" + friendCode + "'";
        ResultSet rs = conn.selectQuery(query);
        try {
            if (rs.isBeforeFirst()) {
                return true; //No other song has that name
            } else {
                return false; //Song name already exists
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static void addFriend(User user, String friendCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "insert into Friend(usernameFriend, emailFriend, usernameUser, emailUser)" +
                       "values ('"+ friendCode +"', '" + SQLOperations.getUserEmail(friendCode) + "', '" + user.getUsername() + "', '" + getUserEmail(user.getUsername()) + "')";
        conn.insertQuery(query);
    }

    private static String getUserEmail(String friendCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "select user.email from user where username like '" + friendCode + "'";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){
                String toReturn = rs.getString("email");
                return toReturn;
            }
        } catch (SQLException ignore){}

        return "null";
    }

    public static void deleteAccount(String subAction) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "delete from User where username like '"+ subAction +"';";
        conn.insertQuery(query);
    }

    /**
     * Does a query in order to get the list of songs shown in the client's SongListView.
     * @param user
     * @return userSongList
     */
    public static ArrayList<Song_database> getSongsFromUser(String user) {
        ArrayList<Song_database> userSongList = new ArrayList<>();

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT song_id, song_name, author_name, album_name, num_reproductions, song_url, privacy FROM Song WHERE author_name LIKE '" + user + "' or privacy LIKE 'Public';";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){
                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                String album_id = rs.getString(4);
                int num_reproductions = rs.getInt(5);
                String song_url = rs.getString(6);
                String privacy = rs.getString(7);
                userSongList.add(new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy));
            }
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSongList;
    }

    /**
     * Does a query in order to get the list of songs shown in the client friend's SongListView.
     * @param friendUsername
     * @return friendSongList
     */
    public static ArrayList<Song_database> getSongsFromFriend(String friendUsername) {
        ArrayList<Song_database> friendSongList = new ArrayList<>();

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT song_id, song_name, author_name, album_name, num_reproductions, song_url, privacy FROM Song WHERE author_name LIKE '" + friendUsername + "';";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){

                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                String album_id = rs.getString(4);
                int num_reproductions = rs.getInt(5);
                String song_url = rs.getString(6);
                String privacy = rs.getString(7);
                friendSongList.add(new Song_database(song_id,song_name,author_name,album_id,num_reproductions,song_url,privacy));
            }
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendSongList;
    }

    public static void removeFriendFrom(String friendCode, String userCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "delete from Friend where usernameUser like '"+ userCode +"' and usernameFriend like '"+ friendCode +"'; ";
        conn.deleteQuery(query);
    }

    public static void addMinutesPlayed(User user) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "INSERT INTO ReproductionsGraph(username,reproduction_date,minutes) VALUES('"+user.getUsername()+"','"+df.format(calobj.getTime())+"',"+(user.getMinutes())+");";
        conn.insertQuery(query);
    }

    public static String getUsernameFromEmail(String username) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "select user.username from user where email like '" + username + "'";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){
                String toReturn = rs.getString("username");
                return toReturn;
            }
        } catch (SQLException ignore){}
        return "null";
    }
}
