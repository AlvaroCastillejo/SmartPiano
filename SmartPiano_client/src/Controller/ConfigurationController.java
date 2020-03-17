package Controller;

import Model.AudioPlayer;
import View.ConfigurationView;
import View.KeyboardConfigurationView;
import View.MainMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationController implements ActionListener {

    private AudioPlayer introSong;
    private ConfigurationView v;

    public ConfigurationController(ConfigurationView v, AudioPlayer introSong) {
        this.v = v;
        this.introSong = introSong;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "KeyboardConfiguration":
                introSong.setVolume(-40);
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    KeyboardConfigurationController c = new KeyboardConfigurationController();
                    KeyboardConfigurationView m = new KeyboardConfigurationView(c);
                    c.setView(m);
                    m.setVisible(true);
                });
                break;
            case "Back":
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    MainMenuView v = new MainMenuView();
                    MenuController c = new MenuController(v, introSong);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
        }
    }
}
