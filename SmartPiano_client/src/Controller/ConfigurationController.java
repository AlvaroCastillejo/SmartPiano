package Controller;

import Model.AudioPlayer;
import Model.ConfigurationManager;
import Model.LoginManager;
import Model.MenuManager;
import View.ConfigurationView;
import View.KeyboardConfigurationView;
import View.LoginView;
import View.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//Controller for the ConfigurationView.
public class ConfigurationController implements ActionListener {

    private AudioPlayer introSong;
    private ConfigurationView v;
    private ConfigurationManager m;

    /**
     * Constructor for the controller. It initializes the controller.
     * @param v The view to control.
     * @param introSong The background song that was playing in the MainMenu.
     */
    public ConfigurationController(ConfigurationView v, AudioPlayer introSong) {
        this.v = v;
        this.introSong = introSong;
    }

    /**
     * Registers all the actions performed in the configurationView.
     * @param actionEvent The event occurred.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Back":
                v.setVisible(false);
                introSong.setVolume(1.0f);
                SwingUtilities.invokeLater( () -> {
                    MainMenuView v = new MainMenuView();
                    MenuController c = new MenuController(v, introSong);
                    MenuManager m = new MenuManager(c, this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;

            case "LogOff":
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    introSong.interrupt();
                    LoginView v = new LoginView();
                    LoginController c = new LoginController(v);
                    LoginManager m = new LoginManager(c);
                    c.registerManager(m);
                    c.startClient();
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;

            case "KeyboardConfiguration":
                introSong.setVolume(-40);
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    KeyboardConfigurationController c = new KeyboardConfigurationController();
                    KeyboardConfigurationView m = new KeyboardConfigurationView(c);
                    c.setView(m,introSong);
                    m.setVisible(true);
                });
                break;

            case "DeleteAccount":
                //delete current user account and data
                SwingUtilities.invokeLater(() -> {
                    v.setVisible(false);
                    introSong.interrupt();
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

    public void registerManager(ConfigurationManager m) {
        this.m = m;
    }
}
