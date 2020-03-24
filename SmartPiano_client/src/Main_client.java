import Controller.LoginController;
import Model.AudioPlayer;
import Model.Configuration;
import Model.LoginManager;
import View.LoginView;

import javax.swing.*;

public class Main_client {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        SwingUtilities.invokeLater(() -> {
            LoginView v = new LoginView();
            LoginController c = new LoginController(v);
            LoginManager m = new LoginManager(c);
            c.registerManager(m);
            c.startClient();
            v.registerController(c);
            v.setVisible(true);
        });
    }
}
