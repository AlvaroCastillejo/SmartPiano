package Controller;

import Model.AudioPlayer;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Controller for the KeyboardConfigurationView. (Keybinding) Not working
public class KeyboardConfigurationController implements ActionListener {
    private KeyboardConfigurationView k;
    private ActionListener[] actionListeners;
    private AudioPlayer introSong;

    public ActionListener getActionListeners(int i) { return actionListeners[i]; }

    public void setView (KeyboardConfigurationView k, AudioPlayer introSong) {
        this.k = k;
        this.introSong = introSong;
    }

    /**
     * Constructor for the controller. It initializes the controller.
     * @param v The view to control.
     * @param introSong The background song that was playing in the ConfigurationMenu.
     */
    /*
    public KeyboardConfigurationController(KeyboardConfigurationView v, AudioPlayer introSong) {
        this.v = v;
        this.introSong = introSong;
    }*/
    /**
     * Registers all the actions performed in the KeyboardConfigurationView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Back":
                k.setVisible(false);
                if(introSong != null) introSong.setVolume(40);
                SwingUtilities.invokeLater( () -> {
                    ConfigurationView v = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }


}
