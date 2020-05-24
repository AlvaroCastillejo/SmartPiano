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
    /**
     * Adds a user to the database.
     * @param name Name of the user.
     * @param pass Password of the user.
     * @param mail Mail of the user.
     * @throws SQLException When connection couldn't be done.
     */
    public static void ImportaUsuari(String name, String pass, String mail) throws SQLException {
        System.out.println("entro DB??");


        System.out.println("conecto ?");

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();


        conn.insertQuery("INSERT INTO User(username,password,email) VALUES ('" + name + "','" + pass + "', '" + mail + "')");
        System.out.println("intento inserir");

    }

    /**
     * Checks if the user given already exists in the database.
     * @param user The user given.
     * @return If exists.
     */
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

    /**
     * Find a user in the database.
     * @param user The user to search.
     * @return The status of the search.
     * @throws SQLException If the connection couldn't be done.
     */
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

    /**
     * Adds a song to the database.
     * @param s Song to add.
     * @return Status.
     * @throws SQLException If the connection couldn't be done.
     */
    public static int addSong(SavedSong s) throws SQLException{
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "INSERT INTO Song(song_name,author_name ,album_name,num_reproductions,song_url,privacy)"+
                       "VALUES("+"'"+s.getSongName()+"'"+","+"'"+s.getSongAuthor()+"'"+","+"'"+s.getSongAlbum()+"'"+",0,"+"'"+s.getSongName()+"'"+","+"'"+s.getSongPrivacy()+"'"+");";
        conn.insertQuery(query);
        return 0;
    }

    /**
     * Checks if a song name is stored in our database. If the return value is true, no song in our database has that name.
     * @param songName Name of the song that we want to evaluate.
     * @return A boolean that indicates the availability of the given song name. If the returned value is false, that song name can be used.
     */
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
        return false;
    }

    /**
     * Gets the friends from a given user.
     * @param user The given user.
     * @return The list of friends found.
     */
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

    /**
     * Checks if two given users are friends.
     * @param user One user.
     * @param friendCode The name of the other user.
     * @return If they are friends or not.
     */
    public static boolean alreadyFriends(User user, String friendCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "SELECT * FROM friend WHERE usernameUser like '" + user.getUsername() +  "' and usernameFriend like '" + friendCode + "'";
        ResultSet rs = conn.selectQuery(query);
        try {
            if (rs.isBeforeFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a friend to a user.
     * @param user The user to add the friend to.
     * @param friendCode The name of the friend to add.
     */
    public static void addFriend(User user, String friendCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "insert into Friend(usernameFriend, emailFriend, usernameUser, emailUser)" +
                       "values ('"+ friendCode +"', '" + SQLOperations.getUserEmail(friendCode) + "', '" + user.getUsername() + "', '" + getUserEmail(user.getUsername()) + "')";
        conn.insertQuery(query);
    }

    /**
     * Gets the email given a username.
     * @param friendCode Username.
     * @return The email.
     */
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

    /**
     * Deletes the account from the database.
     * @param subAction The name of the user to delete the account.
     */
    public static void deleteAccount(String subAction) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "delete from User where username like '"+ subAction +"';";
        conn.insertQuery(query);
    }

    /**
     * Does a query in order to get the list of songs shown in the client's SongListView.
     * @param user contains all the user information.
     * @return userSongList List containing the song id, song name, author name, number of reproductions, the url and the privacy of all the public songs created by the given user.
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

    /**
     * Deletes a friend from a user.
     * @param friendCode The friend to delete.
     * @param userCode From who.
     */
    public static void removeFriendFrom(String friendCode, String userCode) {
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "delete from Friend where usernameUser like '"+ userCode +"' and usernameFriend like '"+ friendCode +"'; ";
        conn.deleteQuery(query);
    }

    /**
     * Add minutes played to the database.
     * @param user The user that added them.
     */
    public static void addMinutesPlayed(User user) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();

        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "INSERT INTO ReproductionsGraph(username,reproduction_date,minutes) VALUES('"+user.getUsername()+"','"+df.format(calobj.getTime())+"',"+(user.getMinutes())+");";
        conn.insertQuery(query);
    }

    /**
     * Given the mail, this function returns the username linked to that mail.
     * @param username String containing the email of the evaluated user.
     * @return the username linked to that mail.
     */
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

    /**
     * Updates the reproduction count of a song in our database.
     * @param nameSong name of the song the user just played.
     */
    public static void updateReproduction(String nameSong){
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");

        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        conn.connect();

        String query = " UPDATE Song SET num_reproductions=num_reproductions+1 WHERE song_name LIKE '" + nameSong + "';";
        conn.updateQuery(query);
    }

    /**
     * Deletes song from the database.
     * @param songID The ID of the song to delete.
     */
    public static void deleteSong(String songID) {
        if(getUserFromSongID(songID).equals("server")){
            return;
        }
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "delete from Song where song_id like '"+ songID +"';";
        conn.deleteQuery(query);
    }

    /**
     * Gets the author name of a song.
     * @param songID The song ID.
     * @return The username of the author.
     */
    public static String getUserFromSongID(String songID){
        ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
        ConectorDB conn = new ConectorDB(sc.getDatabaseUser(), sc.getDatabasePassword(), sc.getDatabaseName(), sc.getDatabasePort(), "jdbc:mysql://localhost");
        String query = "select * from song where song_id = " + songID + "";
        ResultSet rs = conn.selectQuery(query);

        try{
            while (rs.next()){
                int song_id = rs.getInt(1);
                String song_name = rs.getString(2);
                String author_name = rs.getString(3);
                return author_name;
            }
        } catch (SQLException ignore){}
        return "null";
    }
}
