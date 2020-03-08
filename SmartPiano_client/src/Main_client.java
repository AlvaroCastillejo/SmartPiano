import View.LoginView;

import javax.swing.*;

public class Main_client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView v = new LoginView();
            v.setVisible(true);
        });
    }
}
