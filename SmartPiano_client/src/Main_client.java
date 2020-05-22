import Controller.LoginController;
import Model.Configuration;
import Model.KeyNotes;
import Model.LoginManager;
import Model.Utils.JsonUtils;
import View.LoginView;

import javax.swing.*;
import java.awt.*;

public class Main_client {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        SwingUtilities.invokeLater(() -> {
            Point locationOnScreen = null;
            LoginView v = new LoginView(locationOnScreen);
            LoginController c = new LoginController(v);
            LoginManager m = new LoginManager(c);
            c.registerManager(m);
            c.startClient();
            v.registerController(c);
            v.setVisible(true);
        });
    }
}
