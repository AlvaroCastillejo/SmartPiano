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

    public void sendResultFriendAdded(String s) {
        String[] result = s.split("/");
        switch (result[1]){
            case "ignore":
                c.sendResultFriendAdded(true, "Friend added successfully!");
                break;
            case "useruser":
                c.sendResultFriendAdded(false, "You cannot add yourself!");
                break;
            case "alreadyFriends":
                c.sendResultFriendAdded(false, "You are already friends!");
                break;
        }
    }

    public void sendAction(String s) {
        client.sendAction(s);
    }
}
