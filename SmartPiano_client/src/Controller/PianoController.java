package Controller;

import Model.*;
import Model.Network.Client;
import View.MainMenuView;
import View.Piano;
import View.SaveSongView;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//The controller for the Piano view.
public class PianoController implements ActionListener, KeyListener {
    private Piano v;
    private PianoManager m;
    private SaveSongView ssv;
    //A map with the keys that are concurrently sustaining.
    private Map<String, KeyPressed> sustainingKeys;
    private ActionListener[] actionListeners;
    private boolean isRecordingPiano;
    private boolean startedRecording;
    private Song song;

    Track t;
    Sequence s;
    long initialTime;
    private boolean autoplay;
    private SavedSong savedSong;
    private Point locationOnScreen;

    /**
     * An empty constructor.
     */
    public PianoController(){
        sustainingKeys = new HashMap<>();
        isRecordingPiano = false;
        startedRecording = false;
        autoplay = true;
    }

    /**
     * Assigns the view of the controller.
     * @param v The piano to control.
     */
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

    /**
     * Detects if a key (from the keyboard) has been typed.
     * @param keyEvent The information about the key typed.
     */
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
                if(v.getIsRecordingPianoType()){
                    if(v.isRecording() == null || !v.isRecording()){
                        v.startRecording();
                        startRecording();
                    } else {
                        v.stopRecording();
                        stopRecording();
                    }
                    break;
                }
                if(v.getIsSongPlayer()){
                    if(autoplay){
                        v.setAutoPlay(false);
                        autoplay = false;
                    } else {
                        v.setAutoPlay(true);
                        autoplay = true;
                    }
                }
                break;
            case "goBack":
                m.sendAction("UPLOAD/endReproduction");
                if (v.getIsSongPlayer()){
                    v.setAutoPlay(false);
                }
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
                    if(isRecordingPiano && startedRecording){
                        notePressed(keyCode);
                    }
                }
        }
    }

    /**
     * Saves the note pressed in the MIDI file. This only stores the beginning of the note.
     * @param keyCode The code of the note pressed.
     */
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

    /**
     * Creates the MIDI file that will store the notes played.
     */
    private void startRecording() {

        startedRecording = true;
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

    public static MidiEvent createSetTempoEvent(long tick, long tempo) {
        // microseconds per quarternote
        long mpqn = 60000000 / tempo;

        MetaMessage metaMessage = new MetaMessage();

        // create the tempo byte array
        byte[] array = new byte[] { 0, 0, 0 };

        for (int i = 0; i < 3; i++) {
            int shift = (3 - 1 - i) * 8;
            array[i] = (byte) (mpqn >> shift);
        }

        // now set the message
        try {
            metaMessage.setMessage(81, array, 3);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new MidiEvent(metaMessage, tick);
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
                this.locationOnScreen = this.v.getLocationOnScreen();
                v.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    MainMenuView v = new MainMenuView(locationOnScreen);
                    MenuController c = new MenuController(v, null); //new AudioPlayer("Ludovico-Einaudi-Nuvole-Bianche.wav")
                    MenuManager m = new MenuManager(c, this.m.getClient());
                    c.registerManager(m);
                    v.registerController(c);
                    v.setVisible(true);
                });
                break;
            default:
                v.cropAscendingNote(keyCode);
                //Cut off the sustaining.
                try{
                    sustainingKeys.get(keyCode).setSustaining(false);
                } catch (NullPointerException e){
                    System.out.println();
                }
                //Remove it from the current sustaining notes.
                sustainingKeys.remove(keyCode);
                v.releaseButton(keyCode);
                if(isRecordingPiano && startedRecording){
                    noteReleased(keyCode);
                }
        }
    }

    /**
     * Saves the note pressed in the MIDI file. This stores the end of the note.
     * @param keyCode The code of the note released.
     */
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

    /**
     * Finishes the MIDI file, closes it and saves it.
     */
    private void stopRecording() {

        startedRecording = false;
        try {

            //set end of track (meta event) 19 ticks later
            MetaMessage mt = new MetaMessage();
            byte[] bet = {}; // empty array
            mt.setMessage(0x2F,bet,0);
            MidiEvent me = new MidiEvent(mt, (long)140);
            t.add(me);

            //We open the Saving Song View


            SwingUtilities.invokeLater(()->{
                SaveSongView v = new SaveSongView();
                SaveSongController c = new SaveSongController(v,m.getClient(),this);
                SaveSongManager ssm = new SaveSongManager(c);
                m.getClient().setSaveSongManager(ssm);
                c.assignSaveSongManager(ssm);
                v.setVisible(true);
            });

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    public void saveSong(String songName, String albumName, String songPrivacy ){
        File f = new File(songName+".mid");
        try {
            MidiSystem.write(s,1,f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.savedSong= new SavedSong(songName,albumName,songPrivacy,m.getClient().getLogin(),f);
        this.m.sendAction("SAVESONG/saveSong");

        //write the MIDI sequence to a MIDI file
        //FileSaver fileSaver = new FileSaver(f);
    }

    public void setSong(Song song) { this.song = song;}

    /**
     * Drops one note on the Piano.
     * @param note The note to drop.
     */
    public void drop(Note note) {
        v.drop(note);
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

    public void startCount(int countdown) {
        v.startCountDown(countdown);
    }

    public void registerManager(PianoManager m) {
        this.m = m;
    }

    public SavedSong getSavedSong() {
        return this.savedSong;
    }

    public void startTimeLine(long duration) {
        v.startTimeLine(duration);
    }
}
