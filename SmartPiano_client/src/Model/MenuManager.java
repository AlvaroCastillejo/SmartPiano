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

    public void sendAction(String s) {
        client.sendAction(s);
    }

    public void sendFriendListToSend(FriendListToSend friendList) {
        c.sentFriendListToSend(friendList);
    }
}
