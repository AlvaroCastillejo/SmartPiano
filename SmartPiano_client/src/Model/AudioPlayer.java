package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer extends Thread {
    private String fileName;
    private FloatControl volume;

    public AudioPlayer(String fileName){
        this.fileName = fileName;
    }

    public void stopTheCurrent(){
        this.interrupt();
    }

    @Override
    public void run(){
        Clip play = null;
        try {
            String f = new File("").getAbsolutePath().concat("\\SmartPiano_client\\src\\Model\\Assets\\IntroMusic\\" + fileName);

            File in = new File(f);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
            play = AudioSystem.getClip();
            play.open(audioInputStream);
            volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(1.0f); // Reduce volume by 10 decibels.
            play.start();

            do {
                Thread.sleep(15);
            } while (play.isRunning());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException | NullPointerException ex) {
        } finally {
            try {
                play.close();
            } catch (Exception exp) {
            }
        }

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(int db){
        volume.setValue(db);
    }
}
