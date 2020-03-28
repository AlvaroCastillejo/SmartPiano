package Controller;

import Model.AudioPlayer;
import Model.Configuration;
import Model.Note;
import Model.PianoManager;
import View.MainMenuView;
import View.Piano;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

//The controller for the Piano view.
public class PianoController implements ActionListener, KeyListener {
    private Piano v;
    //A map with the keys that are concurrently sustaining.
    private Map<String, KeyPressed> sustainingKeys;
    private ActionListener[] actionListeners;
    private boolean isRecordingPiano;

    Track t;
    Sequence s;
    long initialTime;

    /**
     * An empty constructor.
     */
    public PianoController(){
        sustainingKeys = new HashMap<>();
        isRecordingPiano = false;
    }

    public void setView(Piano v) {
        this.v = v;
    }

    /**
     * Detects if a key was pressed with the mouse.
     * @param actionEvent The command assigned.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //String command = actionEvent.getActionCommand();
        //Play the corresponding sound.
        //new KeyPressed(v, command).start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * Detects if a key (from the keyboard) was pressed.
     * @param keyEvent The information about the key pressed.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //Obtain the keyCode (w/0) from the keybinding configuration.
        String keyCode = Configuration.getKeyBinding(keyEvent.getKeyCode());

        if(keyCode == null){
            return;
        }

        switch (keyCode){
            case "rec":
                if(v.isRecording() == null || !v.isRecording()){
                    v.startRecording();
                    startRecording();
                } else {
                    v.stopRecording();
                    stopRecording();
                }
                break;
            case "goBack":
                break;
            default:

                if(isRecordingPiano){
                    if((sustainingKeys.get(keyCode) == null)){
                        v.ascend(keyCode);
                    }
                }

                //If it wasn't sustaining...
                if(sustainingKeys.get(keyCode) == null){
                    //Add the note to the current sustaining notes.
                    sustainingKeys.put(keyCode, new KeyPressed(v, keyCode));
                    sustainingKeys.get(keyCode).start();
                    v.pressButton(keyCode);
                    notePressed(keyCode);
                }
        }
    }

    private void notePressed(String keyCode) {
        int hexacode = PianoManager.getHexaCode(PianoManager.getKeyName(keyCode));
        try {
            //****  note on - middle C  ****
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0x90,hexacode,0x60);
            MidiEvent me = new MidiEvent(mm,(long)System.currentTimeMillis()-initialTime);
            t.add(me);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void noteReleased(String keyCode){
        int hexacode = PianoManager.getHexaCode(PianoManager.getKeyName(keyCode));
        try {
            //****  note off - middle C - 120 ticks later  ****
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0x80,hexacode,0x40);
            MidiEvent me = new MidiEvent(mm,(long) (System.currentTimeMillis()-initialTime)+120);
            t.add(me);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void startRecording() {
        try {
            initialTime = System.currentTimeMillis();
            s = new Sequence(Sequence.PPQ,120);
            this.t = s.createTrack();

            //General MIDI sysex -- turn on General MIDI sound set.
            byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
            SysexMessage sm = new SysexMessage();
            sm.setMessage(b, 6);
            MidiEvent me = new MidiEvent(sm,(long)0);
            t.add(me);

            //set tempo (meta event)
            MetaMessage mt = new MetaMessage();
            byte[] bt = {0x02, (byte)0x00, 0x00};
            mt.setMessage(0x51 ,bt, 3);
            me = new MidiEvent(mt,(long)0);
            t.add(me);

            //set track name (meta event)
            mt = new MetaMessage();
            String TrackName = new String("midifile track");
            mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
            me = new MidiEvent(mt,(long)0);
            t.add(me);

            //set omni on
            ShortMessage mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7D,0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

            //set poly on
            mm = new ShortMessage();
            mm.setMessage(0xB0, 0x7F,0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

            //set instrument to Piano
            mm = new ShortMessage();
            mm.setMessage(0xC0, 0x00, 0x00);
            me = new MidiEvent(mm,(long)0);
            t.add(me);

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    /**
     * Detects if a key (from the keyboard) was released.
     * @param keyEvent The information about the key released.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //Obtain the keyCode (w/0) from the keybinding configuration.
        String keyCode = Configuration.getKeyBinding(keyEvent.getKeyCode());

        if(keyCode == null){
            return;
        }

        switch (keyCode) {
            case "rec":
                break;
            case "goBack":
                v.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    MainMenuView v = new MainMenuView();
                    MenuController c = new MenuController(v, new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav"));
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            default:

                v.cropAscendingNote(keyCode);

                //Cut off the sustaining.
                sustainingKeys.get(keyCode).setSustaining(false);
                //Remove it from the current sustaining notes.
                sustainingKeys.remove(keyCode);
                v.releaseButton(keyCode);
                noteReleased(keyCode);
        }
    }

    private void stopRecording() {
        try {

            //set end of track (meta event) 19 ticks later
            MetaMessage mt = new MetaMessage();
            byte[] bet = {}; // empty array
            mt.setMessage(0x2F,bet,0);
            MidiEvent me = new MidiEvent(mt, (long)140);
            t.add(me);

            //write the MIDI sequence to a MIDI file
            File f = new File("midifile.mid");
            MidiSystem.write(s,1,f);
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }


    public void drop(Note note) {
        //v.drop(note);
    }

    public String getRecordingKey() {
        return Configuration.getRecKeyName();
    }

    public String getReturnKey() {
        return  Configuration.getGoBackKeyName();
    }

    public void isRecordingPiano() {
        this.isRecordingPiano = true;
    }
}
