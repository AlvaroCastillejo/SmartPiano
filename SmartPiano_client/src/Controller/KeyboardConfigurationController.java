package Controller;

import Model.AudioPlayer;
import Model.Configuration;
import Model.ConfigurationManager;
import Model.KeyboardConfigurationManager;
import View.*;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

//Controller for the KeyboardConfigurationView. (Keybinding) Not working
public class KeyboardConfigurationController implements ActionListener, KeyListener {
    private KeyboardConfigurationView v;
    private ActionListener[] actionListeners;
    private AudioPlayer introSong;
    private KeyboardConfigurationManager manager;
    private boolean alreadyPressed;


    private String note;

    public ActionListener getActionListeners(int i) { return actionListeners[i]; }


    /*/**
     * Constructor for the controller. It initializes the controller.
     * @param v The view to control.
     * @param introSong The background song that was playing in the ConfigurationMenu.
     */

    public KeyboardConfigurationController( AudioPlayer introSong) {
        this.introSong = introSong;
        alreadyPressed = false;
    }

    /**
     * Registers all the actions performed in the KeyboardConfigurationView.
     * @param actionEvent The event occurred.
     */

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String note = actionEvent.getActionCommand();
        v.showInfoOn(note, "actionPerfomed");
        if (!alreadyPressed) {
            this.note = note;
            v.pressButton(note);
            alreadyPressed = true;
        } else {
            v.releaseButton(this.note);
            this.note = note;
            v.pressButton(note);
        }
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        String command = "";
        command = Configuration.getKeyBinding(keyEvent.getKeyCode());
        int keyCode = keyEvent.getKeyCode();
        if(command == null){
            command = "";
        }
        switch (command) {
            case "goBack":
                //Save the new KeyboardConfiguration
                Configuration.saveKeyboardConfiguration();

                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    ConfigurationView v = new ConfigurationView();
                    ConfigurationController c = new ConfigurationController(v, introSong);
                    ConfigurationManager m = new ConfigurationManager();
                    m.setClient(manager.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;

            case "rec":
                break;

            default:
                if (!alreadyPressed) break;

                int status = Configuration.bindKey(note, keyCode);
                if(status == 1){
                    v.showInfoOn(Configuration.getKeyBinding(keyCode), "status");
                } else {
                    v.showInfoOn(note, "ok");
                }
                v.releaseButton(note);
                note = null;
                alreadyPressed = false;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void registerManager(KeyboardConfigurationManager m) {
        this.manager = m;
    }

    public void registerView (KeyboardConfigurationView v) {
        this.v = v;
    }

}
