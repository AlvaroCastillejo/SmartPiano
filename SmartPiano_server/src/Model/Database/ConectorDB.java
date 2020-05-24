package Model.Database;

import java.sql.*;

public class ConectorDB {
    static String userName;
    static String password;
    static String db;
    static int port;
    static String url = "";
    static Connection conn = null;
    static Statement s;
    public PreparedStatement statement;

    public ConectorDB(String usr, String pass, String db, int port, String url) {
        ConectorDB.userName = usr;
        ConectorDB.password = pass;
        ConectorDB.db = db;
        ConectorDB.port = port;
        ConectorDB.url = url;
        ConectorDB.url += ":"+port+"/";
        ConectorDB.url += db;
        ConectorDB.url += "?verifyServerCertificate=false&useSSL=true";
    }

    public boolean connect()  {
        try {
            Class.forName("com.mysql.jdbc.Connection");

            conn =  DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Conexió a base de dades "+url+" ... Ok");
            }
            return true;
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            System.out.println(ex);
        }
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public void insertQuery(String query){
        try {
            System.out.println("insert query");
            s = conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println(query);
            System.out.println("Problema al Inserir --> " + ex);
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public void disconnect(){
        try {
            conn.close();
            System.out.println("Desconnectat!");
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }


}

