package Controller;

import View.ConfigurationView;
import View.KeyboardConfigurationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationController implements ActionListener {

    private ConfigurationView v;

    public ConfigurationController (ConfigurationView v) {
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "KeyboardConfiguration":
                v.setVisible(false);
                SwingUtilities.invokeLater( () -> {
                    KeyboardConfigurationController c = new KeyboardConfigurationController();
                    KeyboardConfigurationView m = new KeyboardConfigurationView(c);
                    c.setView(m);
                    m.setVisible(true);
                });
        }
    }
}
