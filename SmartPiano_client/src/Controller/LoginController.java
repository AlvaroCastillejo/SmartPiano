package Controller;

import Model.AudioPlayer;
import Model.LoginManager;
import Model.MenuManager;
import Model.Network.Client;
import Model.RegisterManager;
import View.LoginView;
import View.MainMenuView;
import View.RegisterView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;



//Controller for the LoginView.
public class LoginController implements ActionListener, KeyListener {
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
                    Point locationOnScreen = this.v.getLocationOnScreen();
                    v.setVisible(false);
                    RegisterView v = new RegisterView(locationOnScreen);
                    RegisterController c = new RegisterController(v, client);
                    RegisterManager rm = new RegisterManager(c);
                    c.assignRegisterManager(rm);
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
            Point locationOnScreen = this.v.getLocationOnScreen();
            v.setVisible(false);
            //Show main menu.
            SwingUtilities.invokeLater(() -> {
                MainMenuView v = new MainMenuView(locationOnScreen);
                MenuController c = new MenuController(v, null); //new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav")
                MenuManager m = new MenuManager(c, client);
                client.assignMenuManager(m);
                c.registerManager(m);
                v.registerController(c);
                v.setVisible(true);
            });
        } else {
            showErrorUser("kk");
            System.out.println("ENTRO\n");
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

    /**
     * Updates the view to show the corresponding error.
     * @param error The error occurred.
     */
    public void showErrorUser(String error) {
        v.showErrorUser(error);
    }

    public String getUserLogin() {
        return v.getUsername();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * Registers when a key from the keyboard is pressed.
     * @param keyEvent The information of the key pressed.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==10){
            client.sendAction("LOGIN/tryLogin");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
