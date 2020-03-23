package Controller;

import Model.PianoManager;
import View.Piano;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//Represents a piano key that has been pressed. Reproduces the sound.
public class KeyPressed extends Thread{
    private Piano v;
    private String key;
    private boolean sustaining;

    /**
     * Constructor for the class. Automatically makes the note to be sustaining.
     * @param v Piano view where the key will be pressed.
     * @param key The keyCode of the key that has been pressed. (w/0)
     */
    public KeyPressed(Piano v, String key) {
        this.v = v;
        this.key = key;
        this.sustaining = true;
    }

    /**
     * Method that plays the sound and only ends when the sound is ended.
     */
    @Override
    public void run() {

        Clip play = null;
        try {
            //Get the location of the sound using the keyCode.
            String fileName = "Piano".concat(PianoManager.getKeyName(key)).concat(".wav");
            String f = new File("").getAbsolutePath().concat("\\SmartPiano_client\\src\\Model\\Assets\\Sounds\\" + fileName);

            File in = new File(f);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
            play = AudioSystem.getClip();
            play.open(audioInputStream);
            FloatControl volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(1.0f); // Reduce volume by 10 decibels.
            play.start();

            //Keep the thread running until the clip is playing or until the key is no more sustaining.
            do {
                Thread.sleep(250);
                if(!sustaining){
                    break;
                }
            } while (play.isRunning());

            //Shift the volume down to recreate a real piano sound.
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

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the sustaining state.
     * @param sustaining sustaining state to be updated to.
     */
    public void setSustaining(boolean sustaining) {
        this.sustaining = sustaining;
    }

    /**
     * Shifts the volume from one value to another.
     * @param thread The thread controlling the clip.
     * @param volume The FloatControl that controls the clip volume.
     * @param from Original volume.
     * @param to Final volume.
     * @param milliseconds Transition time.
     */
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
