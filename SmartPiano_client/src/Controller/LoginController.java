package Controller;

import View.LoginView;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private LoginView v;

    public LoginController(LoginView v){
        this.v = v;
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
                System.out.println("LOGIN");
                break;
        }



    }
}
