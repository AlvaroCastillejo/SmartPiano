package Controller;

import Network.Client;
import View.LoginView;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {
    private RegisterView v;
    private Client client;

    public RegisterController(RegisterView v, Client client){
        this.v = v;
        this.client = client;
        client.assignRegisterController(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch(command){
            case "REGISTER/register":
                client.sendAction("REGISTER/tryRegister");
                System.out.println("REGISTER");
                break;
            case "REGISTER/login":
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    LoginView v = new LoginView();
                    LoginController c = new LoginController(v);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }



    }

    public void registered(boolean b) {
        if (b){
            v.setVisible(false);
        }
    }

    public String getRegisterCredentials() {
        return v.getUsername().concat("/" + (v.getMail().concat("/" + v.getPassword())));
    }

    public void showError(String error) {
        v.showError(error);
    }
}
