package Model;

import Controller.MainMenuController;

public class MainMenuManager {
    private final MainMenuController c;

    public MainMenuManager(MainMenuController c) {
        this.c = c;
    }

    public void refreshUI() {
        c.refreshUI();
    }
}
