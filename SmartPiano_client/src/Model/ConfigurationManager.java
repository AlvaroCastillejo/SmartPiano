package Model;

import Model.Network.Client;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    private Map<String, String> teclado;
    private boolean introMusic;
    private int volume;
    private Client client;

    public ConfigurationManager() {
        this.teclado = new HashMap<>();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public boolean isIntroMusic() { return introMusic; }
    public void setIntroMusic(boolean introMusic) { this.introMusic = introMusic; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }

    public void volumeUp () { this.volume += 1; }
    public void volumeDown ()  { this.volume =- 1; }

    public void turnOffIntro () { this.introMusic = false; }
    public void trunOnIntr0 () { this.introMusic = true; }


    public void sendAction(String s) {
        client.sendAction(s);
    }
}
