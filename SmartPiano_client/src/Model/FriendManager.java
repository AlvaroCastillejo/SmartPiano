package Model;

import Controller.FriendController;
import Model.Network.Client;

import java.util.ArrayList;
import java.util.LinkedList;

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

    /**
     * Tells the friendListController what was the response on the petition.
     * @param s The response.
     */
    public void sendResultFriendAdded(String s) {
        String[] result = s.split("/");
        switch (result[1]){
            case "ignore":
                c.sendResultFriendAdded(true, "Friend added successfully!");
                client.sendAction("DOWNLOAD/friendListForUpdate");
                break;
            case "useruser":
                c.sendResultFriendAdded(false, "You cannot add yourself!");
                break;
            case "alreadyFriends":
                c.sendResultFriendAdded(false, "You are already friends!");
                break;
            case "notExist":
                c.sendResultFriendAdded(false, "This user doesn't exist!");
                break;
        }
    }

    public void sendAction(String s) {
        client.sendAction(s);
    }

    /**
     * Tells the controller to update the view with the new friendList.
     * @param friends The new friendList.
     */
    public void updateUI(ArrayList<Friend> friends) {
        c.updateUI(friends);
    }

    /**
     * Tells the controller if the deletion was successful or not. If it was asks the client to download the updated friendList.
     * @param s The status of the deletion.
     */
    public void sendResultFriendDeleted(String s) {
        String[] result = s.split("/");
        if(result[0].equals("true")){
            c.sendResultFriendDeleted(true, "Friend deleted successfully!");
            client.sendAction("DOWNLOAD/friendListForUpdate");
        }
    }
}
