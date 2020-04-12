package Controller;

import Model.LoginManager;
import Model.RegisterManager;
import Model.Network.Client;
import View.LoginView;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//The Register view controller.
public class RegisterController implements ActionListener {
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
                System.out.println("REGISTER");
                break;
            //Wants to login.
            case "REGISTER/login":
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    LoginView v = new LoginView();
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
            v.setVisible(false);
        }
    }

    /**
     * Gets the introduced credentials from the view.
     * @return A string that contains the credentials separated by "/".
     */
    public String getRegisterCredentials() {
        return v.getUsername().concat("/" + (v.getMail().concat("/" + v.getPassword())));
    }

    /**
     * Updates the view to show the corresponding error.
     * @param error The error occurred.
     */
    public void showError(String error) {
        v.showError(error);
    }
}
