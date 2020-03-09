import Controller.LoginController;
import View.LoginView;

import javax.swing.*;

public class Main_client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView v = new LoginView();
            LoginController c = new LoginController(v);
            v.registerController(c);
            v.setVisible(true);
        }
        );
    }
}
