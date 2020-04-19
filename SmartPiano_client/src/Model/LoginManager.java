package Model;

import Controller.LoginController;

public class LoginManager {

    private LoginController loginController;

    public LoginManager(LoginController loginController) {
        this.loginController = loginController;
    }

    public String getUserCredentials() {
        return loginController.getUserCredentials();
    }

    public void logged(boolean b) {
        loginController.logged(b);
    }

    public  void showErrorUser(String user_incorrect) {
        loginController.showErrorUser(user_incorrect);
    }
}
