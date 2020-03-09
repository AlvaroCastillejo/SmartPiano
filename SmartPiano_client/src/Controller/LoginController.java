package Controller;

import Network.Client;
import View.LoginView;
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
                    RegisterController c = new RegisterController(v);
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
}
