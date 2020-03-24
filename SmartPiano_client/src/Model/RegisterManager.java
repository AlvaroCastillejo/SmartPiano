package Model;

import Controller.RegisterController;

public class RegisterManager {

    private RegisterController registerController;

    public RegisterManager(RegisterController registerController) {
        this.registerController = registerController;
    }

    public String getRegisterCredentials() {
        return registerController.getRegisterCredentials();
    }

    public void registered(boolean b) {
        registerController.registered(b);
    }

    public void showError(String this_user_already_exists) {
        registerController.showError(this_user_already_exists);
    }

}
