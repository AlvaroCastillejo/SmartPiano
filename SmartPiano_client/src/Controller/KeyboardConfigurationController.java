package Controller;

import View.KeyboardConfigurationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Controller for the KeyboardConfigurationView. (Keybinding) Not working
public class KeyboardConfigurationController implements ActionListener {
    private KeyboardConfigurationView k;
    private ActionListener[] actionListeners;

    public ActionListener getActionListeners(int i) { return actionListeners[i]; }

    public void setView (KeyboardConfigurationView k) { this.k = k; }

    @Override
    public void actionPerformed (ActionEvent actionEvent)  {
        String command = actionEvent.getActionCommand();

    }

}
