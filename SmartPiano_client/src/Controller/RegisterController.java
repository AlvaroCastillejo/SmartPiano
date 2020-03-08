package Controller;

import View.LoginView;
import View.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {
    private RegisterView v;

    public RegisterController(RegisterView v){
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch(command){
            case "LOGIN/register":
                System.out.println("REGISTER");
                break;
            case "LOGIN/login":
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
}
