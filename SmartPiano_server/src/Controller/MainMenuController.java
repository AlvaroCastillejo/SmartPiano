package Controller;

import Model.MainMenuManager;
import View.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private MainMenuManager m;
    private MainMenu v;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
        String[] elements = actionEvent.getActionCommand().split("/");
        String command = elements[0];
        String action = elements[1];
        switch (command){
            case "DELETE":
                m.deleteSong(action);
                break;
        }
    }

    public void registerManager(MainMenuManager m) {
        this.m = m;
    }

    public void registerView(MainMenu v) {
        this.v = v;
    }

    public void refreshUI(double[] hoursReproductions) {
        v.refresh(hoursReproductions);
    }
}
