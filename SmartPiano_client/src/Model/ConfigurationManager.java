package Model;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    private Map<String, String> teclado;
    private boolean introMusic;
    private int volume;

    public ConfigurationManager() {
        this.teclado = new HashMap<>();
    }

    public boolean isIntroMusic() { return introMusic; }
    public void setIntroMusic(boolean introMusic) { this.introMusic = introMusic; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }

    public void volumeUp () { this.volume += 1; }
    public void volumeDown ()  { this.volume =- 1; }

    public void turnOffIntro () { this.introMusic = false; }
    public void trunOnIntr0 () { this.introMusic = true; }



}
