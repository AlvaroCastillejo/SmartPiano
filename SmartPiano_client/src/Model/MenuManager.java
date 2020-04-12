package Model;

import Controller.MenuController;
import Model.Network.Client;

public class MenuManager {
    private MenuController c;
    private Client client;

    public MenuManager(MenuController c, Client client) {
        this.c = c;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
