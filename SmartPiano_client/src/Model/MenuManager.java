package Model;

import Controller.MenuController;
import Model.Network.Client;

import java.util.ArrayList;

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

    public void showSongList(ArrayList<Song_database> songList){
        c.showSongList(songList);
    }
}
