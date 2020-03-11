package Controller.KeyControllers.Octave4;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class E4 implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("E4 pressed");

        Clip play = null;
        try {
            File in = new File("prueba2.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
            play = AudioSystem.getClip();
            play.open(audioInputStream);
            FloatControl volume = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(1.0f); // Reduce volume by 10 decibels.
            play.start();
            // Loop until the Clip is not longer running.
            // We loop this way to allow the line to fill, otherwise isRunning will
            // return false
            do {
                Thread.sleep(15);
            } while (play.isRunning());
            play.drain();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            try {
                play.close();
            } catch (Exception exp) {
            }
        }
        System.out.println("...");
    }
}
