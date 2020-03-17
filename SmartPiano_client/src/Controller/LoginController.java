package Controller;

import Model.AudioPlayer;
import Network.Client;
import View.LoginView;
import View.MainMenuView;
import View.Piano;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginController implements ActionListener {
    private LoginView v;
    private Client client;


    public LoginController(LoginView v){
        this.v = v;
        try {
            client = new Client(this);
            client.startServerConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch(command){
            case "LOGIN/register":
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    RegisterView v = new RegisterView();
                    RegisterController c = new RegisterController(v, client);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            case "LOGIN/login":
                client.sendAction("LOGIN/tryLogin");
                System.out.println("LOGIN");
                break;
        }
    }

    public String getUserCredentials() {
        return v.getUsername().concat("/").concat(v.getPassword());
    }

    public void logged(boolean logged) {
        if(logged){
            v.setVisible(false);
            SwingUtilities.invokeLater(() -> {
                MainMenuView v = new MainMenuView();
                MenuController c = new MenuController(v, new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav"));
                v.registerController(c);
                v.setVisible(true);
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                v.setVisible(false);
                RegisterView v = new RegisterView();
                RegisterController c = new RegisterController(v, client);
                v.registerController(c);
                v.setVisible(true);
            });
        }
    }
}
