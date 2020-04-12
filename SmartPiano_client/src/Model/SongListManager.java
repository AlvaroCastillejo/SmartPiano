package Model;

import Controller.SongListController;
import Model.Network.Client;

public class SongListManager {
    private SongListController c;
    private Client client;

    public SongListManager(SongListController c, Client client) {
        this.c = c;
        this.client = client;
    }

    public SongListController getC() {
        return c;
    }

    public void setC(SongListController c) {
        this.c = c;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
