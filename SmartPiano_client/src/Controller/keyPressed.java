package Controller;

import View.Piano;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class keyPressed extends Thread{
    private Piano v;
    private String key;

    public keyPressed(Piano v, String key) {
        this.v = v;
        this.key = key;
    }

    @Override
    public void run(){

        Clip play = null;
        try {
            String fileName = getKeyFilename(key);
            String f = new File("").getAbsolutePath().concat("\\SmartPiano_client\\src\\Model\\Assets\\Sounds\\" + fileName);

            File in = new File(f);
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

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getKeyFilename(String key) {
        String k = "";
        switch (key){
            case "w/0":
                k = "C2";
                break;
            case "b/0":
                k = "Db2";
                break;
            case "w/1":
                k = "D2";
                break;
            case "b/1":
                k = "Eb2";
                break;
            case "w/2":
                k = "E2";
                break;
            case "w/3":
                k = "F2";
                break;
            case "b/3":
                k = "Gb2";
                break;
            case "w/4":
                k = "G2";
                break;
            case "b/4":
                k = "Ab2";
                break;
            case "w/5":
                k = "A2";
                break;
            case "b/5":
                k = "Bb2";
                break;
            case "w/6":
                k = "B2";
                break;
            case "w/7":
                k = "C3";
                break;
            case "b/7":
                k = "Db3";
                break;
            case "w/8":
                k = "D3";
                break;
            case "b/8":
                k = "Eb3";
                break;
            case "w/9":
                k = "E3";
                break;
            case "w/10":
                k = "F3";
                break;
            case "b/10":
                k = "Gb3";
                break;
            case "w/11":
                k = "G3";
                break;
            case "b/11":
                k = "Ab3";
                break;
            case "w/12":
                k = "A3";
                break;
            case "b/12":
                k = "Bb3";
                break;
            case "w/13":
                k = "B3";
                break;
            case "w/14":
                k = "C4";
                break;
            case "b/14":
                k = "Db4";
                break;
            case "w/15":
                k = "D4";
                break;
            case "b/15":
                k = "Eb4";
                break;
            case "w/16":
                k = "E4";
                break;
            case "w/17":
                k = "F4";
                break;
            case "b/17":
                k = "Gb4";
                break;
            case "w/18":
                k = "G4";
                break;
            case "b/18":
                k = "Ab4";
                break;
            case "w/19":
                k = "A4";
                break;
            case "b/19":
                k = "Bb4";
                break;
            case "w/20":
                k = "B4";
                break;
        }
        return "Piano".concat(k).concat(".wav");
    }
}
