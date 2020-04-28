package Model.Database;

import Model.ServerConfiguration;
import Model.User;
import Model.Utils.JsonServerUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        String query = "SELECT * FROM User WHERE username like '" + user.getUsername() + "'"+"AND password LIKE '"+ user.getPassword() + "'";
        ResultSet rs = conn.selectQuery(query);
        if (rs.isBeforeFirst()){
            return 0;
        }else{
            return 1;
        }

    }


}
