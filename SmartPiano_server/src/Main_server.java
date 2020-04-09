import Model.Database.SQLOperations;
import Model.Network.Server;

import java.sql.SQLException;

public class Main_server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        //Database
        SQLOperations sqlOperations = new SQLOperations();
        try{
            sqlOperations.ImportaUsuari("admin", "admin", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
