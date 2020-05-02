import Controller.MainMenuController;
import Model.Database.SQLOperations;
import Model.MainMenuManager;
import Model.Network.Server;
import View.MainMenu;

import javax.swing.*;
import java.sql.SQLException;

public class Main_server {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
