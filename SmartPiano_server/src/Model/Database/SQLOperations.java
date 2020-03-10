package Model.Database;

import java.sql.SQLException;

public class SQLOperations {
    public void ImportaUsuari(String name, String pass, String mail) throws SQLException {
        System.out.println("entro DB??");


        System.out.println("conecto ?");

        ConectorDB conn = new ConectorDB("root", "admin", "SmartPiano", 3306, "jdbc:mysql://localhost");
        conn.connect();


        conn.insertQuery("INSERT INTO User(username,password,mail) VALUES ('" + name + "','" + pass + "', '" + mail + "')");
        System.out.println("intento inserir");

    }
}
