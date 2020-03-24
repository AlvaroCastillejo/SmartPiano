package Controller;

import Model.AudioPlayer;
import Model.LoginManager;
import Network.Client;
import View.LoginView;
import View.MainMenuView;
import View.Piano;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Controller for the LoginView.
public class LoginController implements ActionListener {
    private LoginView v;
    private Client client;
    private LoginManager loginManager;

    /**
     * Constructor for the class. Initializes the client.
     * @param v The view to control.
     */
    public LoginController(LoginView v){
        this.v = v;

    }

    /**
     *Registers all the actions performed in the LoginView.
     *@param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch(command){
            //Wants to register.
            case "LOGIN/register":
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    RegisterView v = new RegisterView();
                    RegisterController c = new RegisterController(v, client);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            //Wants to login.
            case "LOGIN/login":
                client.sendAction("LOGIN/tryLogin");
                break;
        }
    }

    /**
     * Gets the introduced credentials from the view.
     * @return A string that contains the credentials separated by "/".
     */
    public String getUserCredentials() {
        return v.getUsername().concat("/").concat(v.getPassword());
    }

    /**
     * Called from the client to inform the controller if the login was successful or not.
     * @param logged The login success state.
     */
    public void logged(boolean logged) {
        if(logged){
            v.setVisible(false);
            //Show main menu.
            SwingUtilities.invokeLater(() -> {
                MainMenuView v = new MainMenuView();
                MenuController c = new MenuController(v, new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav"));
                v.registerController(c);
                v.setVisible(true);
            });
        } else {
            //Show register view.
            SwingUtilities.invokeLater(() -> {
                v.setVisible(false);
                RegisterView v = new RegisterView();
                RegisterController c = new RegisterController(v, client);
                v.registerController(c);
                v.setVisible(true);
            });
        }
    }

    public void registerManager (LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public void startClient () {
        try {
            client = new Client(loginManager);
            client.startServerConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
