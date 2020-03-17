package Controller;

import Model.PianoManager;
import View.Piano;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class KeyPressed extends Thread{
    private Piano v;
    private String key;
    private boolean sustaining;

    public KeyPressed(Piano v, String key) {
        this.v = v;
        this.key = key;
        this.sustaining = true;
    }

    @Override
    public void run() {

        Clip play = null;
        try {
            String fileName = "Piano".concat(PianoManager.getKeyName(key)).concat(".wav");
            String f = new File("").getAbsolutePath().concat("\\SmartPiano_client\\src\\Model\\Assets\\Sounds\\" + fileName);

            File in = new File(f);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
            play = AudioSystem.getClip();
            play.open(audioInputStream);
            FloatControl volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(1.0f); // Reduce volume by 10 decibels.
            play.start();

            do {
                Thread.sleep(250);
                if(!sustaining){
                    break;
                }
            } while (play.isRunning());

            shiftVolume(this, volume, volume.getValue(), volume.getMinimum(), 2);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ignore){
        } finally {
            try {
                play.close();
            } catch (Exception exp) {
            }
        }
        System.out.println("...");

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSustaining(boolean sustaining) {
        this.sustaining = sustaining;
    }

    public void shiftVolume(Thread thread, FloatControl volume, double from, double to, int milliseconds) {
        for(int i = 0; from > to; from--){
            volume.setValue(volume.getValue()-1);
            try {
                sleep(milliseconds);
            } catch (InterruptedException e) {

            }
        }
    }
}
