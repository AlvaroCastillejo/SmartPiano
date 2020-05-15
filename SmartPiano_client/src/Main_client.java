import Controller.LoginController;
import Model.Configuration;
import Model.KeyNotes;
import Model.LoginManager;
import Model.Utils.JsonUtils;
import View.LoginView;

import javax.swing.*;

public class Main_client {
    public static void main(String[] args) {
        KeyNotes keyNotes = JsonUtils.getKeyNotes("keyBoardConfig");
        Configuration config = new Configuration(keyNotes);
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
