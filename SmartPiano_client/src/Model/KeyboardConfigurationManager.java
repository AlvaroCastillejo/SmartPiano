package Model;

import Controller.KeyboardConfigurationController;
import Model.Network.Client;

public class KeyboardConfigurationManager {
    private KeyboardConfigurationController c;
    private Client client;

    public KeyboardConfigurationManager (KeyboardConfigurationController c) {
        this.c = c;
    }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
}
