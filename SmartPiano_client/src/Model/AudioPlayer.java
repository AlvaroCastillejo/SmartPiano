package Model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//A class to reproduce songs.
public class AudioPlayer extends Thread {
    private String fileName;
    private FloatControl volume;
    private Clip play;
    /**
     * The class constructor.
     * @param fileName The name of the file to reproduce.
     */
    public AudioPlayer(String fileName){
        this.fileName = fileName;
    }

    /**
     * Stop the song.
     */
    public void stopTheCurrent(){
        play.stop();
        //this.interrupt();
    }

    /**
     * The method that will reproduce the song.
     */
    @Override
    public void run(){
        play = null;
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
            play.close();
            play.stop();
            volume.setValue(-40f);
            stopTheCurrent();
            e.printStackTrace();
        }
    }

    public void setVolume(float db){
        try{
            volume.setValue(db);
        } catch (NullPointerException ignore){}
    }
}
