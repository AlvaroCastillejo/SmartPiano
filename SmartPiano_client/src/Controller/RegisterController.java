package Controller;

import Model.LoginManager;
import Model.RegisterManager;
import Model.Network.Client;
import View.LoginView;
import View.RegisterView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//The Register view controller.
public class RegisterController implements ActionListener, KeyListener {
    private RegisterView v;
    private Client client;
    private RegisterManager registerManager;

    /**
     * Contructor for the class.
     * @param v The view to control.
     * @param client The client that started the application.
     */
    public RegisterController(RegisterView v, Client client){
        this.v = v;
        this.client = client;
    }
    public void assignRegisterManager(RegisterManager registerManager){
        this.registerManager = registerManager;
        client.assignRegisterController(registerManager);
    }
    /**
     * Registers all the actions performed in the RegisterView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch(command){
            //Wants to register.
            case "REGISTER/register":
                client.sendAction("REGISTER/tryRegister");
                break;
            //Wants to login.
            case "REGISTER/login":
                SwingUtilities.invokeLater(() -> {
                    Point locationOnScreen = this.v.getLocationOnScreen();
                    v.setVisible(false);
                    LoginView v = new LoginView(locationOnScreen);
                    LoginController c = new LoginController(v);
                    LoginManager m = new LoginManager(c);
                    c.registerManager(m);
                    c.startClient();
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }


    /**
     * Closes the view if the registration was successful.
     * @param b The success register status.
     */
    public void registered(boolean b) {
        if (b){
            Point locationOnScreen = this.v.getLocationOnScreen();
            v.setVisible(false);
            SwingUtilities.invokeLater(() -> {
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

    /**
     * Gets the introduced credentials from the view.
     * @return A string that contains the credentials separated by "/".
     */
    public String getRegisterCredentials() {
        return v.getUsername().concat("/" + (v.getMail().concat("/" + v.getPassword().concat("/" + v.getRepeatPassword()))));
    }

    /**
     * Updates the view to show the corresponding error.
     * @param error The error occurred.
     */
    public void showError(String error) {
        v.showError(error);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == 10){
            client.sendAction("REGISTER/tryRegister");
        }
    }
}
