package Model;

import Controller.FriendController;
import Model.Network.Client;

public class FriendManager {
    private FriendController c;
    private Client client;

    public FriendManager(FriendController c,Client client) {
        this.c = c;
        this.client = client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void sendRequest(String friendCode) {
        client.sendAction(friendCode);
    }

    public Client getClient() {
        return client;
    }
}
