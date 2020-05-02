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
    }

    public void registerManager(MainMenuManager m) {
        this.m = m;
    }

    public void registerView(MainMenu v) {
        this.v = v;
    }

    public void refreshUI() {
        v.refresh();
    }
}
